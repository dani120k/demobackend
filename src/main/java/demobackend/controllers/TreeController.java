package demobackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demobackend.model.DirectoryInfo;
import demobackend.model.FileInfo;
import demobackend.model.TreeNode;
import demobackend.service.DirectoryInfoMongoService;
import demobackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tree")
public class TreeController {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${directory}")
    private String directory;

    @Autowired
    private FileService fileService;

    @Autowired
    private DirectoryInfoMongoService directoryInfoMongoService;

    @GetMapping(value = "/loadtree")
    public ResponseEntity<String> loadTree() throws JsonProcessingException {
        List<DirectoryInfo> directoryInfos = directoryInfoMongoService.loadHighLevelDirectory();

        List<TreeNode> treeNodeList = new ArrayList<>();

        for (DirectoryInfo directoryInfo : directoryInfos) {
            treeNodeList.add(initTreeNode(directoryInfo));
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(treeNodeList));
    }

    private TreeNode initTreeNode(DirectoryInfo directoryInfo){
        TreeNode treeNode = new TreeNode();
        treeNode.setName(directoryInfo.getTitle());
        treeNode.setId(directoryInfo.getIdMongoDb());
        treeNode.setVrt(false);
        treeNode.setType("directory");


        List<DirectoryInfo> directoryInfoList = directoryInfoMongoService.loadChildrenDirectories(directoryInfo.getIdMongoDb());

        List<TreeNode> childrenList = new ArrayList<>();

        if (directoryInfoList != null){
            for (DirectoryInfo info : directoryInfoList) {
                childrenList.add(initTreeNode(info));
            }
        }

        List<FileInfo> fileInfoList = directoryInfoMongoService.loadChildrenFiles(directoryInfo.getIdMongoDb());

        if (fileInfoList != null){
            for (FileInfo info : fileInfoList) {
                childrenList.add(initTreeNodeFile(info));
            }
        }

        treeNode.setChildren(childrenList);

        return treeNode;
    }

    private TreeNode initTreeNodeFile(FileInfo fileInfo){
        TreeNode treeNode = new TreeNode();
        treeNode.setName(fileInfo.getFileName());
        treeNode.setId(fileInfo.getIdMongoDb());
        treeNode.setChildren(new ArrayList<>());
        //TOODO

        String[] split = fileInfo.getFileName().split("\\.");
        if (split[split.length - 1].equals("vrt")){
            treeNode.setVrt(true);
        } else {
            treeNode.setVrt(false);
        }

        treeNode.setType("file");

        return treeNode;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveDirectory(@RequestBody DirectoryInfo directoryInfo) throws IOException {
        if (directoryInfo.getParentDirectoryId() == null) {
            fileService.createDirectory(Paths.get(directory, directoryInfo.getTitle()));
            directoryInfo.setDirectoryPath(Paths.get(directory, directoryInfo.getTitle()).toString());
        } else {
            DirectoryInfo byId = directoryInfoMongoService.getById(directoryInfo.getParentDirectoryId());

            fileService.createDirectory(Paths.get(byId.getDirectoryPath(), directoryInfo.getTitle()));
            directoryInfo.setDirectoryPath(Paths.get(byId.getDirectoryPath(), directoryInfo.getTitle()).toString());
        }

        Boolean result = directoryInfoMongoService.save(directoryInfo);

        if (result)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Возникла ошибка при создании новой директории");
    }

    @PostMapping(value = "/savefile", consumes = "multipart/form-data")
    public ResponseEntity<String> saveFile(@RequestParam(value = "fileinfo") String fileInfoJson,
                                           @RequestParam(value = "file", required = false) MultipartFile file) throws JsonProcessingException {
        FileInfo fileInfo = objectMapper.readValue(fileInfoJson, FileInfo.class);

        String[] split = fileInfo.getFileName().split("\\.");
        if (split[split.length - 1].equals("vrt")){
            fileInfo.setVrt(true);
        } else
            fileInfo.setVrt(false);

        if (fileInfo.getParentDirectoryId() != null) {
            DirectoryInfo byId = directoryInfoMongoService.getById(fileInfo.getParentDirectoryId());

            fileService.saveFileInDirectory(byId.getDirectoryPath(), file, fileInfo);
        }
        Boolean result = directoryInfoMongoService.saveFile(fileInfo);

        if (result)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Возникла ошибка при добавлении нового файла");
    }

    @GetMapping(value = "/deletedirectory")
    public ResponseEntity<String> deleteDirectory(@RequestParam(value = "id") String id){
        DirectoryInfo byId = directoryInfoMongoService.getById(id);

        Boolean res = directoryInfoMongoService.deleteDirectoryById(id);

        fileService.deleteDirectory(byId.getDirectoryPath());


        if (res)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/deletefile")
    public ResponseEntity<String> deleteFile(@RequestParam(value = "id") String id){
        FileInfo fileInfo = directoryInfoMongoService.getFileById(id);

        DirectoryInfo byId = directoryInfoMongoService.getById(fileInfo.getParentDirectoryId());

        Boolean res = directoryInfoMongoService.deleteFileById(id);

        fileService.deleteDirectory(Paths.get(byId.getDirectoryPath(), fileInfo.getFileName()).toString());

        if (res)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
