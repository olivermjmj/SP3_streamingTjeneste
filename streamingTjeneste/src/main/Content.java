package main;

import java.util.ArrayList;

public class Content {
    public final ArrayList <String> genres;
    public final String title;
    public final int yearOfRelease;
    public final float rating;
   public final short[] seasons;
   public  int choice;

    public Content(String title, int year, ArrayList <String> genres, float rating,short[] seasons) {
        this.title = title;
        this.yearOfRelease = year;
        this.genres = genres;
        this.rating = rating;
        this.seasons = seasons ;
    }


}
