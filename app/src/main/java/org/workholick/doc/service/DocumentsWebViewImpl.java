package org.workholick.doc.service;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.webkit.JavascriptInterface;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.workholick.doc.contracts.FileService;
import org.workholick.doc.contracts.LoginService;
import org.workholick.doc.model.Docs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentsWebViewImpl  {
    Context context;

    private final String externalFileDir="org.workholic.doc";
    private final String externalDataFileDir="org.workholic.doc.data";

    ObjectMapper mapper=new ObjectMapper();

    public  DocumentsWebViewImpl(Context context) {
         this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public String fetchAllDoc() throws Exception{
        System.out.println("Executing fetch..");
        try {
            File file = new File(this.context.getFilesDir(), "DocManager");
            if (!file.exists()) {
                file.mkdir();
            }
            List<Docs> docList=new ArrayList<>();

            for(Docs doc:FileService.getServiceInstance().readAllDoc(file)) {
                docList.add(doc);
            }
            System.out.print("reading all files:"+docList);
            if(docList.isEmpty()){
                return "EMPTY";
            }
            return new JSONArray(mapper.writeValueAsString(docList)).toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  "EMPTY";
    }

    @JavascriptInterface
    public void executed(String message){
        System.out.println("Executed..."+message);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public String login(String password){
        try {
            File file=new File(this.context.getCacheDir(),externalFileDir);
            if(!file.exists()){
                file.mkdir();
            }
            return LoginService.getLoginService().login(password, file) ? "SUCCESS" : "FAILURE";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public String signUp(String password, String confrimPassword){

        if(!password.equals(confrimPassword)){
            return "PASSWORD_NOT_MATCHED";
        }
        try {

            File file=new File(this.context.getCacheDir(),externalFileDir);
            if(!file.exists()){
                file.mkdir();
            }
            boolean result=LoginService.getLoginService().signUp(password, file);

            return  result? "SUCCESS" : "FAILURE";
        }catch (Exception ex){
            ex.printStackTrace();
            return "FAILURE";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public boolean save(String fileName,String content){

        try {
            File file = new File(this.context.getFilesDir(), "DocManager");
            if (!file.exists()) {
                file.mkdir();
            }
            System.out.println("loc:"+ file.getAbsolutePath());
            return FileService.getServiceInstance().saveFile(fileName,content,file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public boolean update(String fileName, String oldFileName, String content){
        try {
            File file = new File(this.context.getFilesDir(), "DocManager");
            if (!file.exists()) {
                file.mkdir();
            }
            return FileService.getServiceInstance().updateFile(fileName,oldFileName,content,file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public boolean delete(String fileName){
        System.out.print("Document name :"+fileName);
        try {
            File file = new File(this.context.getFilesDir(), "DocManager");
            if (!file.exists()) {
                file.mkdir();
            }
            return FileService.getServiceInstance().deleteFile(fileName,file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
