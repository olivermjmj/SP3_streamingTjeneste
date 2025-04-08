package main;

import java.util.ArrayList;

public class ChooseGenre {
    private ArrayList<String> genres;

    public ChooseGenre() {
        this.genres = new ArrayList<>();
    }

    public ChooseGenre(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    public void displayGenres() {
        if (genres.isEmpty()) {
            System.out.println("No genres available.");
        } else {
            System.out.println("Available genres:");
            for (int i = 0; i < genres.size(); i++) {
                System.out.println((i + 1) + ". " + genres.get(i));
            }
        }
    }

}
