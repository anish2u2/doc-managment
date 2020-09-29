package org.workholick.doc.contracts;

import org.workholick.doc.model.Docs;
import org.workholick.doc.service.DocumentServiceFactory;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface FileService {

    public Set<Docs> readAllDoc(File directory) throws Exception;

    public boolean updateFile(String fileName, String oldFileName, String content , File directory) throws Exception;

    public boolean deleteFile(String fileName, File directory)  throws Exception;

    public boolean saveFile(String fileName, String content, File directory)  throws Exception;

    static FileService getServiceInstance(){
        return DocumentServiceFactory.getInstance();
    }

}
