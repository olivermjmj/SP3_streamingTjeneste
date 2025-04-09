public class Content {

    public final String genres;
    public final String title;
    public final int releaseYear;
    public final float rating;

    public Content(String title, int year, String genres, float rating) {
        this.title = title;
        this.releaseYear = year;
        this.genres = genres;
        this.rating = rating;
    }
}
