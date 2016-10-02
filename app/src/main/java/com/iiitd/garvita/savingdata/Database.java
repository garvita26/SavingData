package com.iiitd.garvita.savingdata;

/**
 * Created by Garvita on 01-10-2016.
 */
public class Database {


    private String _username;
    private String _password;

    public Database(){}

    public Database(String username,String password)
    {
        this._username=username;
        this._password=password;
    }




    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_password(String _password) {
        this._password = _password;
    }




    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }
}

