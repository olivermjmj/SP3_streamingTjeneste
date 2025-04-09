import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    public class Movies extends Content {
        //private final String title;
        //private final String genres;
        //private final int releaseYear;
        //private final double rating;
        Scanner scanner = new Scanner(System.in);

        public Movies(String title, String genres, int releaseYear, float rating) {
            super(title, releaseYear, genres, rating);
            //this.title = title;
            //this.genres = genres;
            //this.releaseYear = releaseYear;
            //this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Title: " + title + ", Genres: " + genres + ", Release Year: " + releaseYear + ", Rating: " + rating;
        }


        public static List<Movies> getMoviesFromCSV(String filePath) {
            List<Movies> movies = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        Movies movie = parseMovieLine(line);
                        if (movie != null) {
                            movies.add(movie);
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

            return movies;
        }

        public static Movies parseMovieLine(String line) {
            String[] values = line.split(";");
            if (values.length != 4) {
                return null;
            }

            String title = values[0].trim();
            String releaseYearStr = values[1].trim();
            StringBuilder genresBuilder = new StringBuilder();
            String ratingStr = values[values.length - 1].trim();

            for (int i = 2; i < values.length - 1; i++) {
                if (genresBuilder.isEmpty()) {
                    genresBuilder.append(", ");
                }
                genresBuilder.append(values[i].trim());
            }

            String genres = genresBuilder.toString();

            int releaseYear;
            try {
                releaseYear = Integer.parseInt(releaseYearStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Release year must be a valid integer.");
            }

            float rating;
            try {
                rating = Float.parseFloat(ratingStr.replace(",", "."));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Rating must be a valid decimal number.");
            }

            return new Movies(title, genres, releaseYear, rating);
        }


        public String getMoviesGenre(){
            return genres;
        }

    }



