import java.util.ArrayList;

public class User {

    String name;
    ArrayList<Integer> watchHistory;
    ArrayList<Integer> watchLater;

    public static User login(String name, String passwd) {
        return null;
    }
    public static boolean register(String name, String passwd) {
        return false;
    }

    public User(String name) {
        this.name = name;
    }

    public void save() {
    }
}
