package demobackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import demobackend.model.DirectoryInfo;
import demobackend.model.FileInfo;
import demobackend.model.XmlResponse;
import demobackend.service.DirectoryInfoMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sokolov.model.alghorithms.FileOpenService;
import sokolov.model.alghorithms.VrtOpener;
import sokolov.model.xmlmodel.VRTDataset;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/image")
public class ImageController {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DirectoryInfoMongoService directoryInfoMongoService;

    @GetMapping(value = "/loadimage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    void loadImage(@RequestParam(value = "id") String id,
                   HttpServletResponse response) throws Exception {
        System.out.println();
        FileInfo fileById = directoryInfoMongoService.getFileById(id);
        DirectoryInfo byId = directoryInfoMongoService.getById(fileById.getParentDirectoryId());

        Path path = Paths.get(byId.getDirectoryPath(), fileById.getFileName());

        String[] split = fileById.getFileName().split("\\.");

        BufferedImage tif;

        if (split[split.length - 1].equals("vrt")){
            tif =  VrtOpener.openVrt(byId.getDirectoryPath(), fileById.getFileName());
        } else {
            tif = ImageIO.read(new File(path.toString()));
        }

        System.out.println("builded");

        ImageIO.write(tif, "png", new File("test.png"));

        try (InputStream in = new FileInputStream("test.png")) {
            response.addHeader("Content-disposition", "file.png");

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            in.transferTo(response.getOutputStream());
            response.flushBuffer();
        } finally {

        }
    }


    @GetMapping(value = "/loadoriginalimage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    void loadOriginalImage(@RequestParam(value = "id") String id,
                   HttpServletResponse response) throws Exception {
        FileInfo fileById = directoryInfoMongoService.getFileById(id);
        DirectoryInfo byId = directoryInfoMongoService.getById(fileById.getParentDirectoryId());

        Path path = Paths.get(byId.getDirectoryPath(), fileById.getFileName());

        String[] split = fileById.getFileName().split("\\.");

        BufferedImage tif;

        if (split[split.length - 1].equals("vrt")){
            tif =  VrtOpener.openVrt(byId.getDirectoryPath(), fileById.getFileName());
        } else {
            tif = ImageIO.read(new File(path.toString()));
        }

        System.out.println("builded");

        ImageIO.write(tif, "tif", new File("test.tif"));

        try (InputStream in = new FileInputStream("test.tif")) {
            response.addHeader("Content-disposition", "test.tif");

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            in.transferTo(response.getOutputStream());
            response.flushBuffer();
        } finally {

        }
    }

    @GetMapping(value = "/loadxml")
    public ResponseEntity loadXml(@RequestParam(value = "id") String id) throws IOException {
        FileInfo fileById = directoryInfoMongoService.getFileById(id);
        DirectoryInfo byId = directoryInfoMongoService.getById(fileById.getParentDirectoryId());

        Path path = Paths.get(byId.getDirectoryPath(), fileById.getFileName());

        String xml = Files.readString(path);
        XmlResponse xmlResponse = new XmlResponse();
        xmlResponse.setText(xml);

        return ResponseEntity.ok(objectMapper.writeValueAsString(xmlResponse));
    }

    @GetMapping(value = "/loadoperations")
    public ResponseEntity loadOperations(@RequestParam(value = "id") String id) throws IOException {
        FileInfo fileById = directoryInfoMongoService.getFileById(id);
        DirectoryInfo byId = directoryInfoMongoService.getById(fileById.getParentDirectoryId());

        List<String> logs = new ArrayList<>();
        if (fileById.getVrt() != null && fileById.getVrt()) {
            Path path = Paths.get(byId.getDirectoryPath(), fileById.getFileName());

            String xml = Files.readString(path);
            XmlMapper xmlMapper = new XmlMapper();

            VRTDataset vrtDataset = xmlMapper.readValue(xml, VRTDataset.class);

            if (fileById.getVrt()) {
                logs.add("logs here\n\t");
                logs.add("added vrt");
            }
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(logs));
    }
}
