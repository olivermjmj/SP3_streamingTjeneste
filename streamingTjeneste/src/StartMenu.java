import main.MainMenu;
import util.FileIO;
import util.TextUI;

import java.util.ArrayList;

public class StartMenu {

    int choice;
    String username;
    String password;

    TextUI UI = new TextUI();
    FileIO IO = new FileIO();

    public int startMenu() {

        this.choice = UI.promptNumeric("1. Login" + "\n" + "2. Signup" + "\n" + "3. Exit"); //Asks the user if we wanna login, signup or close the program.

        switch (choice) {

            case 1: UI.displayMessage("You have chosen to login");

                this.username = UI.promptText("Please enter your username: ");
                this.password = UI.promptText("Please enter your password: ");

                if(userLoggingIn()) {

                    System.out.println("You have now logged in as " + username + ". Opening Main Menu");
                    //MainMenu();
                }

            break;

            case 2: UI.displayMessage("You have chosen to signup");

                this.username = UI.promptText("Please choose your username: ");
                this.password = UI.promptText("Please choose your password: ");
                IO.saveUserData("data/userData.csv", username, password);

            break;

            case 3: UI.displayMessage("Closing the program");
            System.exit(0); //Shuts the program down.
            break;

            default: UI.displayMessage("Write a valid number."); startMenu();

        }


        return 0;
    }

    public boolean userLoggingIn() {

        String userLogin = username + "," + password;
        ArrayList<String> users = IO.loadUserData("data/userData.csv");

        for (String user : users) {
            if (user.trim().equals(userLogin)) {

                return true;
            }
        }

        return false;
    }


}
