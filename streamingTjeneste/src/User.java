
import java.io.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.util.Arrays;
import java.util.ArrayList;


public class User {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private static final String USERDATA_DIR = "userdata";

    private static byte[] hashPasswd(char[] passwd, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(passwd, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(passwd, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error hashing password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static User login(String name, String passwd) {
        byte[] hashbytes;
        byte[] hash;
        byte[] salt;
        DataInputStream in;

        salt = new byte[16];
        hash = new byte[32];

        File f = new File(USERDATA_DIR+"/"+name+".udf");
        try {
            in = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(f)));

            int read = in.read(hash, 0, hash.length);
            read += in.read(salt, 0, salt.length);
            if (read <= 0)
                throw new IOException("read failed");

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }

        hashbytes = hashPasswd(passwd.toCharArray(), salt);

        if (Arrays.equals(hash, hashbytes)) {
            return new User(name);
        }
        return null;
    }

    public static boolean register(String name, String passwd) {
        byte[] hashbytes;
        byte[] salt;
        DataOutputStream out;

        salt = new byte[16];
        RANDOM.nextBytes(salt);
        hashbytes = hashPasswd(passwd.toCharArray(), salt);

        new File(USERDATA_DIR).mkdir();
        File f = new File(USERDATA_DIR+"/"+name+".udf");
        if (f.exists())
            return false;
        try {
            out = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(f)));

            out.write(hashbytes);
            out.write(salt);
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String name;
    ArrayList<Integer> watchhistory;
    ArrayList<Integer> watchlater;

    public User(String name) {
        this.name = name;
    }

    public void save() {
    }
}
