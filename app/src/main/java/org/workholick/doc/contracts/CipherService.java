package org.workholick.doc.contracts;

import org.workholick.doc.service.CipherServiceFactory;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;

public interface CipherService {

    static CipherService getInstance(){
        return CipherServiceFactory.getFcatory();
    }

    public CipherOutputStream getCipherOutputStream(Cipher cipher, OutputStream outputStream, SecretKey secretKey) throws Exception;

    public CipherInputStream getCipherInputStream(Cipher cipher, InputStream inputStream, SecretKey secretKey) throws Exception;


}
