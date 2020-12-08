package demobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

public class DirectoryInfo {
    @org.springframework.data.annotation.Id
    @JsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String idMongoDb;

    @JsonProperty(value = "directorypath")
    @Field(value = "directorypath", targetType = FieldType.STRING)
    private String directoryPath;

    @JsonProperty(value = "title")
    @Field(value = "title", targetType = FieldType.STRING)
    private String title;

    @JsonProperty(value = "parentdirectoryid")
    @Field(value = "parentdirectoryid", targetType = FieldType.STRING)
    private String parentDirectoryId;

    @JsonProperty(value = "files")
    @Field(value = "files")
    private List<FileInfo> fileInfoList;

    @JsonProperty(value = "directories")
    @Field(value = "directories")
    private List<DirectoryInfo> directoryInfoList;

    public List<DirectoryInfo> getDirectoryInfoList() {
        return directoryInfoList;
    }

    public void setDirectoryInfoList(List<DirectoryInfo> directoryInfoList) {
        this.directoryInfoList = directoryInfoList;
    }

    public String getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(String parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }

    public List<FileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public String getIdMongoDb() {
        return idMongoDb;
    }

    public void setIdMongoDb(String idMongoDb) {
        this.idMongoDb = idMongoDb;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
