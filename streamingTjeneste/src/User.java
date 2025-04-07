
import util.FileIO;
import util.TextUI;

import java.io.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.util.Arrays;
import java.util.ArrayList;

public class User {

    public String name;

    static TextUI UI = new TextUI();
    static FileIO IO = new FileIO();

    public User(String name) {
        this.name = name;
    }

    public static User login(String name, String passwd) {

        String userLogin = name + "," + passwd;
        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            if (user.trim().equals(userLogin)) {

                return new User(name);
            }
        }

        return null;
    }

    public static boolean register(String name, String passwd) {

        IO.saveUserData("data/userData.csv", name, passwd);
        return true;
    }


/*
    public static boolean userLoggingIn() {

        String userLogin = username + "," + password;
        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            if (user.trim().equals(userLogin)) {

                return true;
            }
        }

        return false;
    }

 */
}






