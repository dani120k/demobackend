package demobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class FileInfo {
    @org.springframework.data.annotation.Id
    @JsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String idMongoDb;

    @JsonProperty(value = "title")
    @Field(value = "title", targetType = FieldType.STRING)
    private String title;

    @JsonProperty(value = "isvrt")
    @Field(value = "isvrt", targetType = FieldType.BOOLEAN)
    private Boolean isVrt;

    @JsonProperty(value = "parentdirectoryid")
    @Field(value = "parentdirectoryid", targetType = FieldType.STRING)
    private String parentDirectoryId;

    @JsonProperty(value = "filename")
    @Field(value = "filename", targetType = FieldType.STRING)
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(String parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }

    public String getIdMongoDb() {
        return idMongoDb;
    }

    public void setIdMongoDb(String idMongoDb) {
        this.idMongoDb = idMongoDb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVrt() {
        return isVrt;
    }

    public void setVrt(Boolean vrt) {
        isVrt = vrt;
    }
}
