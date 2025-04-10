import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Series extends Content {
    //private final String title;
    //private final String genres;
    //private final int releaseYear;
    //private final double rating;
    public final String seasonsAndEpisodes;
    Scanner scanner = new Scanner(System.in);

    public Series(String title, String genres, int releaseYear, float rating, String seasonsAndEpisodes) {
        super(title, releaseYear, genres, rating);
        //this.title = title;
        //this.genres = genres;
        //this.releaseYear = releaseYear;
        //this.rating = rating;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Genres: " + genres + ", Release Year: " + releaseYear + ", Rating: " + rating + ", Seasons and Episodes: " + seasonsAndEpisodes;
    }

    public static List<Series> getSeriesFromCSV(String filePath) {
        List<Series> seriesList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Series series = parseSeriesLine(line);
                    if (series != null) {
                        seriesList.add(series);
                    } else {
                        System.out.println("Ignoring malformed line: " + line);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error processing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return seriesList;
    }

    private static Series parseSeriesLine(String line) {
        String[] values = line.split(";");
        if (values.length != 5) {
            return null;
        }

        String title = values[0].trim();
        String releaseYearStr = values[1].trim();
        StringBuilder genresBuilder = new StringBuilder();
        String seasonsAndEpisodes = values[values.length - 1].trim();
        String ratingStr = values[values.length - 2].trim();

        for (int i = 2; i < values.length - 1; i++) {
            if (genresBuilder.isEmpty()) {
                genresBuilder.append(", ");
            }
            genresBuilder.append(values[i].trim());
        }

        String genres = genresBuilder.toString();

        int releaseYear = 0;
        //try {
        //    releaseYear = Integer.parseInt(releaseYearStr);
        //} catch (NumberFormatException e) {
        //    throw new IllegalArgumentException("Release year must be a valid integer.");
        //}

        double rating;
        try {
            rating = Double.parseDouble(ratingStr.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rating must be a valid decimal number.");
        }

        return new Series(title, genres, releaseYear, (float)rating, seasonsAndEpisodes);
    }

    public String getSeriesGenre(){
        return genres;
    }
}
