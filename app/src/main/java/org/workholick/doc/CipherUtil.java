package org.workholick.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CipherUtil {

    private static Cipher cipher;

    private static SecretKey secureKey;

    private static final String keyFile="securekey.key";

    public static SecretKey getkey(){
        return secureKey;
    }

    public static Cipher  init(File directory) throws  Exception {
        if(cipher!=null){
            return cipher;
        }
        File file=new File(directory,keyFile);
        boolean isNewFile=false;
        if(!file.exists()){
            file.createNewFile();
            isNewFile=true;
        }else{
            ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(file));
            secureKey=(SecretKey)inputStream.readObject();
            inputStream.close();
            cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
            return cipher;
        }
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            secureKey = keygenerator.generateKey();
            if(isNewFile){
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(secureKey);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
            return cipher;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
