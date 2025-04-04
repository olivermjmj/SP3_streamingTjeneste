import java.util.ArrayList;

public class User {
    public static User login(String name, String passwd) {
        return null;
    }
    public static boolean register(String name, String passwd) {
        return false;
    }

    String name;
    ArrayList<Integer> watchhistory;
    ArrayList<Integer> watchlater;

    public User(String name) {
        this.name = name;
    }

    public void save() {
    }
}
