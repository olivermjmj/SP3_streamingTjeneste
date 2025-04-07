package main;

public class Content {
    public static final int GENRE_CRIME = 1;
    public static final int GENRE_DRAMA = 2;
    public final int GENRE_WAR = 3;
    public final int GENRE_FAMILY = 4;
    public final int GENRE_ROMANCE = 5;
    public final int GENRE_SCIFI = 6;

    public final int genres;
    public final String title;
    public final int yearOfRelease;
    public final float rating;

    public final short[] seasons;

    /*public Content(String title, int year, int genres, float rating) {
        this.title = title;
        this.yearOfRelease = year;
        this.genres = genres;
        this.rating = rating;
        this.seasons = null;
    }*/
    public Content(String title, int year, int genres, float rating, short[] seasons) {
        this.title = title;
        this.yearOfRelease = year;
        this.genres = genres;
        this.rating = rating;
        this.seasons = seasons;
    }


}
