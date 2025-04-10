import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContentManager {
    private Movies[] movies;
    private Series[] series;
    public static final int MOVIES = 1;
    public static final int SERIES = 2;
    public static final int WATCH_LATER = 4;
    private int visible;

    private List<Content> data = new ArrayList<>();

    public ContentManager() {

        List<Movies> movieList = Movies.getMoviesFromCSV("data/movieData.csv");
        movies = movieList.toArray(new Movies[movieList.size()]);

        List<Series> serieList = Series.getSeriesFromCSV("data/seriesData.csv");
        series = new Series[serieList.size()];
        series = serieList.toArray(series);
        visible = MOVIES;

    }

    public List<Content> getContent() {
        return data;
    }

    public void setVisible(int visible) {
        data.clear();
        this.visible = visible;

        if ((visible & WATCH_LATER) != 0) {
            StreamingService.user.loadWatchLater(movies, series);
            for (Content c : StreamingService.user.watchLater) {
                data.add(c);
            }
            return;
        }
        if ((visible & MOVIES) != 0) {
            for (Movies m : movies) {
                data.add(m);
            }
        }
        if ((visible & SERIES) != 0) {
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

        if ((visible & WATCH_LATER) != 0) {
            StreamingService.user.loadWatchLater(movies, series);
            for (Content c : StreamingService.user.watchLater) {
                if (c.title.toLowerCase().contains(query))
                    data.add(c);
            }
            return;
        }
        if ((visible & MOVIES) != 0) {
            for (Movies m : movies) {
                if (m.title.toLowerCase().contains(query))
                    data.add(m);
            }
        }
        if ((visible & SERIES) != 0) {
            for (Series s : series) {
                if (s.title.toLowerCase().contains(query))
                    data.add(s);
            }
        }
    }

    public void searchGenre(String title, String genres) {
        data.clear();

        String query = title.toLowerCase();
        String queryGenres = genres.toLowerCase();
        if ((visible & WATCH_LATER) != 0) {
            StreamingService.user.loadWatchLater(movies, series);
            for (Content c : StreamingService.user.watchLater) {
                if (c.title.toLowerCase().contains(query) && c.genres.toLowerCase().contains(queryGenres))
                    data.add(c);
            }
            return;
        }
        if ((visible & MOVIES) != 0) {
            for (Movies m : movies) {
                if (m.title.toLowerCase().contains(query) && m.genres.toLowerCase().contains(queryGenres))
                    data.add(m);
            }
        }
        if ((visible & SERIES) != 0) {
            for (Series s : series) {
                if (s.title.toLowerCase().contains(query) && s.genres.toLowerCase().contains(queryGenres))
                    data.add(s);
            }
        }
    }
}
