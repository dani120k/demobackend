package demobackend.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import demobackend.model.DirectoryInfo;
import demobackend.model.FileInfo;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryInfoMongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DirectoryInfo> loadHighLevelDirectory(){
        Query query = new Query(Criteria.where("parentdirectoryid").is(null));

        List<DirectoryInfo> directoryinfo = mongoTemplate.find(query, DirectoryInfo.class, "directoryinfo");

        return directoryinfo;
    }

    public Boolean save(DirectoryInfo directoryInfo) {
        if (directoryInfo.getIdMongoDb() == null){
            DirectoryInfo spdapikeys = mongoTemplate.insert(directoryInfo, "directoryinfo");

            return true;
        } else {
            Query query = new Query(Criteria.where("_id").is(new ObjectId(directoryInfo.getIdMongoDb())));

            Document doc = new Document();
            mongoTemplate.getConverter().write(directoryInfo, doc);
            Update update = Update.fromDocument(doc);

            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DirectoryInfo.class, "directoryinfo");

            if (updateResult.getMatchedCount() != 0)
                return true;
        }

        return false;
    }

    public List<DirectoryInfo> loadChildrenDirectories(String idMongoDb) {
        Query query = new Query(Criteria.where("parentdirectoryid").is(idMongoDb));

        List<DirectoryInfo> directoryinfo = mongoTemplate.find(query, DirectoryInfo.class, "directoryinfo");

        return directoryinfo;
    }

    public List<FileInfo> loadChildrenFiles(String idMongoDb) {
        Query query = new Query(Criteria.where("parentdirectoryid").is(idMongoDb));

        List<FileInfo> directoryinfo = mongoTemplate.find(query, FileInfo.class, "fileinfo");

        return directoryinfo;
    }

    public Boolean saveFile(FileInfo fileInfo) {
        if (fileInfo.getIdMongoDb() == null){
            FileInfo spdapikeys = mongoTemplate.insert(fileInfo, "fileinfo");

            return true;
        } else {
            Query query = new Query(Criteria.where("_id").is(new ObjectId(fileInfo.getIdMongoDb())));

            Document doc = new Document();
            mongoTemplate.getConverter().write(fileInfo, doc);
            Update update = Update.fromDocument(doc);

            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, FileInfo.class, "fileinfo");

            if (updateResult.getMatchedCount() != 0)
                return true;
        }

        return false;
    }

    public DirectoryInfo getById(String idMongoDb) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(idMongoDb)));

        DirectoryInfo directoryinfo = mongoTemplate.findOne(query, DirectoryInfo.class, "directoryinfo");

        return directoryinfo;
    }

    public Boolean deleteDirectoryById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));

        DeleteResult directoryinfo = mongoTemplate.remove(query, "directoryinfo");

        if (directoryinfo.getDeletedCount() != 0)
            return true;

        return false;
    }

    public FileInfo getFileById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));

        FileInfo directoryinfo = mongoTemplate.findOne(query, FileInfo.class, "fileinfo");

        return directoryinfo;
    }

    public Boolean deleteFileById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));

        DeleteResult directoryinfo = mongoTemplate.remove(query, "fileinfo");

        if (directoryinfo.getDeletedCount() != 0)
            return true;

        return false;
    }
}
