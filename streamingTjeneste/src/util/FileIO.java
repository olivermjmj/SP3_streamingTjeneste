package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    public void saveUserData(String path, String username, String password) {

        try (FileWriter writer = new FileWriter(path, true)) { //True append, makes it so we won't overwrite the existing file's content.

            writer.append(username);
            writer.append(',');
            writer.append(password);
            writer.append('\n');

            System.out.println("Data skrevet til filen!");
        } catch (IOException e) {

            System.out.println("Something went wrong with saveUserData");
        }
    }

    public ArrayList<String> loadUserData(String path) {

        ArrayList<String> data = new ArrayList<>();

        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();//skip header;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();   //"Bjarne, 123password"
                data.add(line);
            }
        } catch (FileNotFoundException e) {

            System.out.println("Something went wrong with loadUserData");
        }
        return data;
    }

}

