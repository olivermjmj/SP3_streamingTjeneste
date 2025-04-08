
public class Content {

    public final int genres;
    public final String title;
    public final int yearOfRelease;
    public final float rating;

    public final short[] seasons;

    public Content(String title, int yearOfRelease, int genres, float rating) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genres = genres;
        this.rating = rating;
        this.seasons = null;
    }
    public Content(String title, int yearOfRelease, int genres, float rating, short[] seasons) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genres = genres;
        this.rating = rating;
        this.seasons = seasons;
    }
}
