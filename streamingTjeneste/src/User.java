import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    public String name;
    public ArrayList<Content> watchLater = new ArrayList<>();
    public ArrayList<Content> watchAgain = new ArrayList<>();

    static TextUI UI = new TextUI();
    static FileIO IO = new FileIO();

    public User(String name) {
        this.name = name;
    }



    public static User login(String name, String passwd) {

        String userLogin = name + "," + passwd;
        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            if (userLoggingIn(name, passwd)) {
                return new User(name);
            }
        }

        return null;
    }

    public static boolean register(String name, String passwd) {

        if(!userLoggingIn(name)) {
            if (name.length() >= 1 && passwd.length() >= 1) {

                IO.saveUserData("data/userData.csv", name, passwd);
                return true;
            }


        }

        return false;
    }

    public static boolean userLoggingIn(String name) {

        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length >= 1 && parts[0].trim().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean userLoggingIn(String name, String passwd) {

        String userLogin = name + "," + passwd;
        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            if (user.trim().equals(userLogin)) {

                return true;
            }
        }

        return false;
    }


    public boolean loadWatchLater(Movies[] allMovies, Series[] allSeries) {

        watchLater.clear();
        ArrayList<String> lines = IO.loadUserData("data/watchLaterData.csv");

        for(String line : lines) {

            String[] parts = line.split(",");

            String username = parts[0].trim();
            String title = parts[1].trim();

            if(!username.equals(this.name)) {

                return false;
            }


            for(Movies m : allMovies) {

                if(m.title.equalsIgnoreCase(title)) {

                    watchLater.add(m);
                }
            }


            for(Series s : allSeries) {

                if(s.title.equalsIgnoreCase(title)) {

                    watchLater.add(s);
                }
            }


        }

        return true;
    }


    public boolean loadContentWatched(Movies[] allMovies, Series[] allSeries) {

        watchAgain.clear();
        ArrayList<String> lines = IO.loadUserData("data/userHasWatchedData.csv");

        for(String line : lines) {

            String[] parts = line.split(",");

            String username = parts[0].trim();
            String title = parts[1].trim();

            if (!username.equals(this.name)) {

                return false;
            }

            for (Movies m : allMovies) {

                if (m.title.equalsIgnoreCase(title)) {

                    watchAgain.add(m);
                }
            }


            for (Series s : allSeries) {

                if (s.title.equalsIgnoreCase(title)) {

                    watchAgain.add(s);
                }
            }
        }

        return true;
    }

    public static boolean addContentWatched(String username, String title) {

        String filePath = "data/userHasWatchedData.csv";
        ArrayList<String> lines = IO.loadUserData(filePath);

        for(String line : lines) {

            if(line.equalsIgnoreCase(username + "," + title)) {

                return false; //This content has already been watched and saved.
            }
        }

        IO.saveUserData(filePath, username, title);
        return true;
    }

    public void addWatchLater(String title) {

        String filePath = "data/watchLaterData.csv";

        IO.saveUserData(filePath, this.name, title);
    }

    public void removeWatchLater(String title) {
        IO.deleteUserData("data/watchLaterData.csv", this.name, title);
    }
}





