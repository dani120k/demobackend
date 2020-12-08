package demobackend.service;

import demobackend.model.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    public void createDirectory(Path path) throws IOException {
        Path directory = Files.createDirectory(path);
    }

    public void saveFileInDirectory(String directoryPath, MultipartFile file, FileInfo fileInfo) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(directoryPath + File.separator + fileInfo.getFileName())))) {
            bufferedOutputStream.write(file.getBytes());
        } catch (Exception ex) {

        }
    }

    public void deleteDirectory(String directoryPath) {
        File[] allContents = Paths.get(directoryPath).toFile().listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file.getPath());
            }
        }

        Paths.get(directoryPath).toFile().delete();
    }
}
