package org.workholick.doc.contracts;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.workholick.doc.service.LoginServiceFactory;

import java.io.File;

public interface LoginService {

    public boolean login(String password, File rootDirectory) throws Exception;

    public boolean signUp(String password, File rootDirectory) throws Exception;

    public static LoginService getLoginService() throws Exception{
        return LoginServiceFactory.getInstance();
    }

}
