import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContentManager {
    private Movies[] movies;
    private Series[] series;
    private int visible;

    private List<Content> data = new ArrayList<>();

    public ContentManager() {

        List<Movies> movieList = Movies.getMoviesFromCSV("data/movieData.csv");
        movies = movieList.toArray(new Movies[movieList.size()]);

        List<Series> serieList = Series.getSeriesFromCSV("data/seriesData.csv");
        series = new Series[serieList.size()];
        series = serieList.toArray(series);
        visible = 3;

    }

    public List<Content> getContent() {
        return data;
    }

    public void setVisible(int visible) {
        data.clear();
        this.visible = visible;
        if ((visible & 1) != 0) {
            for (Movies m : movies) {
                data.add(m);
            }
        }
        if ((visible & 2) != 0) {
            for (Series s : series) {
                data.add(s);
            }
        }
    }

    public void sortRating(boolean lowest) {
        if (!lowest)
            data.sort(Comparator.comparing(Content::getRating).reversed());
        else
            data.sort(Comparator.comparing(Content::getRating));
    }

    public void search(String title) {
        data.clear();
        String query = title.toLowerCase();
        if ((visible & 1) != 0) {
            for (Movies m : movies) {
                if (m.title.toLowerCase().contains(query))
                    data.add(m);
            }
        }
        if ((visible & 2) != 0) {
            for (Series s : series) {
                if (s.title.toLowerCase().contains(query))
                    data.add(s);
            }
        }
    }
}
