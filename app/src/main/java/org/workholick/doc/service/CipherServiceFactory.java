package org.workholick.doc.service;

import android.annotation.SuppressLint;

import org.workholick.doc.contracts.CipherService;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class CipherServiceFactory implements CipherService {


    private static CipherServiceFactory instance=new CipherServiceFactory();

    private CipherServiceFactory(){

    }

    public static CipherService getFcatory(){
        return instance;
    }




    @Override
    public CipherOutputStream getCipherOutputStream(Cipher cipher, OutputStream outputStream, SecretKey secretKey) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return  new CipherOutputStream(outputStream,cipher);
    }

    @Override
    public CipherInputStream getCipherInputStream(Cipher cipher, InputStream inputStream, SecretKey secretKey) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return new CipherInputStream(inputStream,cipher);
    }


}
