package org.workholick.doc.service;

import org.workholick.cipher.contracts.CipherSuit;
import org.workholick.cipher.factory.CipherFactory;
import org.workholick.doc.CipherUtil;
import org.workholick.doc.contracts.CipherService;
import org.workholick.doc.contracts.LoginService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class LoginServiceFactory implements LoginService {

    private final String DOC_CACHE="doc_cahce.ch";
    private final String KEY="pwd.key";
    private final String CACHE_DIR="org.workholick.doc";
    private byte[] data;
    private final String crackKey="RG9jTWFuYWdlckAyMDIw-";

    private static LoginService loginService= new LoginServiceFactory();

    {
        try {
            data = new byte[20];
            int length = 0;
            byte[] dataArray = crackKey.getBytes();
            while (length < 20) {
                data[length] = dataArray[length];
                length++;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private LoginServiceFactory(){

    }


    public boolean signUp(String password, File rootDirectory) throws Exception {
        try{
            CipherSuit cipherSuit= CipherFactory.getInstance();
            cipherSuit.initJks(password,rootDirectory);
            CipherSuitFactory.initCipher(password,rootDirectory);
           try(CipherOutputStream outputStream= CipherFactory.getInstance().getOutputStream(CipherSuitFactory.getEncryptor(),new File(rootDirectory,KEY))){
               outputStream.write(password.getBytes());
               outputStream.flush();
           }catch (Exception ex){
               ex.printStackTrace();
           }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean login(final String password, File rootDirectory) throws Exception {
       try{
//           CipherFactory.getInstance().getDesCipher(rootDirectory,password);
           CipherFactory.getInstance().initJks(password,rootDirectory);
           CipherSuitFactory.initCipher(password,rootDirectory);
           try(CipherInputStream inputStream= CipherFactory.getInstance().getInputStream(CipherSuitFactory.getDecryptor(),new File(rootDirectory,KEY))){
               byte[] data=new byte[4096];
               int length=0;
               ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
               while((length=inputStream.read(data))!=-1) {
                   outputStream.write(data,0,length);
               }
               if(password.equals(new String(outputStream.toByteArray()))){
                   return true;
               }
           }catch (Exception ex){
               ex.printStackTrace();
           }
        }catch (Exception ex){
            ex.printStackTrace();

       }
        return false;
    }



    public static LoginService getInstance(){
        return loginService;
    }
}
