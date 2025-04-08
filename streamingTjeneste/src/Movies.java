import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Movies extends Content {

    public Movies(String title, int genres, int yearOfRelease, double rating) {
        super(title, yearOfRelease, 0, (float) rating);
    }


    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "Title: " + title + ", Genres: " + genres + ", Release Year: " + yearOfRelease + ", Rating: " + rating;
    }


    public static List<Movies> getMovieData(String filePath) {
        List<Movies> movies = new ArrayList<>(); // ArrayListe til at gemme film

        try (Scanner scanner = new Scanner(new File(filePath))) { // Åbner filen ved hjælp af Scanner
            while (scanner.hasNextLine()) { // Gennemgår hver linje i filen
                String line = scanner.nextLine(); // Læser hver linje i filen

                try {
                    Movies movie = parseMovieDataLine(line); // Parser linjen til et Movie objekt
                    if (movie != null) { // Hvis parsing lykkes
                        movies.add(movie); // Tilføjer vi filmen til listen
                    } else {
                        System.out.println(" Ignoring line" + line); // Ignorerer linjen, hvis parsing fejler
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error processing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return movies; // Returnerer listen af film
    }

    private static Movies parseMovieDataLine(String line) {
        String[] values = line.split(","); // Opdeler linjen i sektioner (parametre) ved hvert komma
        if (values.length < 4) { // Tjekker om der er tilstrækkelig parametre.
            return null; // Returner null, hvis der mangler parametre
        }

        try {
            String title = values[0].trim();
            int year = Integer.parseInt(values[1].trim()); // Årstallet konverteres til et heltal
            int genres = Integer.parseInt(values[2].trim()); // Genrer konverteres til et heltal
            float rating = Float.parseFloat(values[3].trim()); // Rating konverteres til en float

            return new Movies(title, year, genres, rating); // Opret og returnér en ny film
        } catch (NumberFormatException e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage()); // printer fejlmeddelelse hvis der er parsing problemer
            return null; // Returner null, hvis parsing fejler
        }
    }
}



