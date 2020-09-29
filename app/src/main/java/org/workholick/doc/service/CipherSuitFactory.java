package org.workholick.doc.service;

import org.workholick.cipher.contracts.CipherSuit;
import org.workholick.cipher.factory.CipherFactory;

import java.io.File;

import javax.crypto.Cipher;

public class CipherSuitFactory {


    private static Cipher encryptor;
    private static Cipher decryptor;

    public static void initCipher(String password,File rootDirectory){
        try{
            if(encryptor==null)
                 encryptor=CipherFactory.getInstance().getEncryptorCipher(rootDirectory,password);
            if(decryptor==null)
                decryptor=CipherFactory.getInstance().getDesCipher(rootDirectory,password);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static Cipher getEncryptor(){
        return encryptor;
    }
    public static Cipher getDecryptor(){
        return decryptor;
    }
}
