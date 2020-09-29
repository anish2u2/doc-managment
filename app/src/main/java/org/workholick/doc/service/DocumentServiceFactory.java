package org.workholick.doc.service;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.workholick.cipher.contracts.CipherSuit;
import org.workholick.cipher.factory.CipherFactory;
import org.workholick.doc.contracts.FileService;
import org.workholick.doc.model.Docs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class DocumentServiceFactory implements FileService {

    private static FileService fileService;

    private final static Set<Docs> docs=new HashSet<>();

    private final String extension=".cod";

    private DocumentServiceFactory(){

    }

    public  static FileService getInstance(){
        if(fileService==null){
            fileService=new DocumentServiceFactory();
        }
        return fileService;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Set<Docs> readAllDoc(File directory)throws Exception {
        if(directory==null){
            return null;
        }
        File[] filesDir=directory.listFiles();
        if(filesDir!=null && filesDir.length>0) {
            docs.clear();
            List<File> listOfFiles=Arrays.asList(directory.listFiles());
            listOfFiles=listOfFiles.stream().sorted((file1,file2)->{
                if(file1.lastModified()==file2.lastModified()){
                    return 0;
                }else if(file1.lastModified()>file2.lastModified()){
                    return 1;
                }else {
                    return -1;
                }
            }).collect(Collectors.toList());
            for (File doc : listOfFiles) {

                Docs document=new Docs();
                document.setDocTitle(doc.getName());
                document.setDocText(readFile(doc));
                docs.add(document);
            }
        }
        return docs;
    }

    private String readFile(File file) throws  Exception{
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        System.out.println("File name:"+ file.getName());
        CipherInputStream stream=null;
        try{
            stream= CipherFactory.getInstance().getInputStream(CipherSuitFactory.getDecryptor(),file);
            int length=0;
            byte[] data=new byte[1024];
            while((length=stream.read(data))!=-1){
                outputStream.write(data,0,length);
            }
            outputStream.flush();
            return new String(outputStream.toByteArray());
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally {
            outputStream.close();
            if(stream!=null)
                stream.close();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean updateFile(String fileName,final String oldFileName, String content, File directory) throws Exception{
        if(!deleteFile(oldFileName,directory)){
            throw  new Exception("Unable to delete old file.");
        }

        if(fileName!=null){
            Date currentTime=new Date();
            File file=new File(directory,fileName);
            if(file.exists()){
                throw new Exception("File already exists");
            }else {
                file.createNewFile();
            }
            file.setLastModified(new Date().getTime());
            try(CipherOutputStream outputStream =CipherFactory.getInstance().getOutputStream(CipherSuitFactory.getEncryptor(),file)){
                outputStream.write(content.getBytes());
                outputStream.flush();
                docs.forEach((document)->{
                    if(document.getDocTitle().equals(oldFileName)){
                        document.setDocTitle(fileName);
                        document.setDocText(content);
                    }
                });

                return true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean deleteFile(String fileName, File directory)  throws Exception {
        if(fileName!=null){
            File file=new File(directory,fileName);
            if(!file.exists()){
                throw new Exception("File not exists");
            }
            file.delete();
            Optional<Docs> firstDoc=docs.stream().filter((document)->{
                if(document.getDocTitle().equals(fileName)){
                    return true;
                }else{
                    return false;
                }
            }).findFirst();
            if(firstDoc.isPresent()){
                docs.remove(firstDoc.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean saveFile(String fileName, String content, File directory)  throws Exception {
        if(fileName!=null){
            File file=new File(directory,fileName);
            if(file.exists()){
                throw new Exception("File already exists");
            }else{
                file.createNewFile();
            }
            file.setLastModified(new Date().getTime());
            try(CipherOutputStream outputStream =CipherFactory.getInstance().getOutputStream(CipherSuitFactory.getEncryptor(),file)){
                outputStream.write(content.getBytes());
                outputStream.flush();
                Docs document=new Docs();
                document.setDocText(content);
                document.setDocTitle(fileName);
                docs.add(document);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                file.delete();
            }
        }
        return false;
    }
}
