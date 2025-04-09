import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void save() {
        System.out.println("FIXME: implement User.Save()");
    }





    public void SearchByChoice() {
        Scanner scanner = new Scanner(System.in);  // If you already have one globally, reuse that instead

        System.out.println("What would you like to search by genre?");
        System.out.println("1. Movies");
        System.out.println("2. Series");
        System.out.print("Enter your choice (1 or 2): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                searchMovieGenre(); // Calls the movie genre search method
                break;
            case "2":
                searchSeriesGenre(); // Calls the series genre search method
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 for Movies or 2 for Series.");
                break;
        }
    }


    public void searchMovieGenre() {
        Scanner scanner = new Scanner(System.in); // if not already declared globally

        try {
            System.out.print("Enter the genre of the movies you would like to see: ");
            String query = scanner.nextLine().trim().toLowerCase();

            List<Movies> moviesList = Movies.getMoviesFromCSV("data/movies.csv");
            if (moviesList.isEmpty()) {
                System.out.println("No movies found in the file.");
                return;

            }

            boolean movieFound = false;
            System.out.println("\nSearch results:");
            for (Movies movie : moviesList) {
                if (movie.getMoviesGenre().toLowerCase().contains(query)) {
                    System.out.println(movie);
                    movieFound = true;
                }
            }

            if (!movieFound) {
                System.out.println("No movies found matching your genre.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred during the search: " + e.getMessage());
        }


    }





    public void searchSeriesGenre () {
        Scanner scanner2 = new Scanner(System.in); // if not already declared globally

        try {
            System.out.print("Enter the genre of the series you would like to see: ");
            String query = scanner2.nextLine().trim().toLowerCase();

            List<Series> seriesList = Series.getSeriesFromCSV("data/seriesData.csv");
            if (seriesList.isEmpty()) {
                System.out.println("No series found in the genre.");
                return;
            }

            boolean seriesFound = false;
            System.out.println("\nSearch results:");
            for (Series series : seriesList) {
                if (series.getSeriesGenre().toLowerCase().contains(query)) {
                    System.out.println(series);
                    seriesFound = true;
                }
            }

            if (!seriesFound) {
                System.out.println("No Series found matching your genre.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred during the search: " + e.getMessage());
        }

        // scanner.close(); // Only close if you're not using it again elsewhere
    }

}





