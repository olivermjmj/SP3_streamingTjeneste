package main;
import java.util.Set;

public class Content {
    public final Set<String> genres;
    public final String title;
    public final int yearOfRelease;
    public final float rating;
    public final short[] seasons;

    public Content(Set<String> genres, String title, int yearOfRelease, float rating, short[] seasons) {
        this.genres = genres;
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.rating = rating;
        this.seasons = seasons;
    }



}
