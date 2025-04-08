import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Series extends Content {

    public Series(String title, int genres, int yearOfRelease, float rating, short[] seasons) {
        super(title, genres, yearOfRelease, rating, seasons);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Genres: " + genres + ", Release Year: " + yearOfRelease + ", Rating: " + rating + ", Seasons: " + seasons.length;
    }

    public static List<Series> getSerieData(String filePath) {
        List<Series> seriesList = new ArrayList<>(); // ArrayListe til at gemme serier

        try (Scanner scanner = new Scanner(new File(filePath))) { // Vi bruger en scanner nu til at åbne filen
            while (scanner.hasNextLine()) { // Gennemgår hver linje i filen
                String line = scanner.nextLine(); // Læser linjen

                try {
                    Series series = parseSeriesDataLine(line); // Parse linjen til en serie
                    if (series != null) { // Hvis parsing lykkes
                        seriesList.add(series); // Tilføjer serien til listen
                    } else {
                        System.out.println("Ignoring line: " + line); // Ignorer linjen, hvis parsing fejler
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error processing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage()); // Printer fejlmeddelelse ved filproblemer
        }

        return seriesList; // Returnerer listen af serier
    }

    private static Series parseSeriesDataLine(String line) {
        String[] values = line.split(","); // Opdeler linjen i sektioner ved hvert komma
        if (values.length < 5) { // Tjekker om der er tilstrækkelige sektioner i data filen
            return null; // Returner null, hvis der mangler sektioner i data filen
        }

        try {
            String title = values[0].trim(); // Titel på serien
            int year = Integer.parseInt(values[1].trim()); // Årstallet konverteres til et heltal
            int genres = Integer.parseInt(values[2].trim()); // Genrer konverteres til et heltal
            float rating = Float.parseFloat(values[3].trim()); // Rating konverteres til en float

            String[] seasonsArray = values[4].trim().split("-"); // Splitter sæsondata (og episode data)
            short[] seasons = new short[seasonsArray.length];
            for (int i = 0; i < seasonsArray.length; i++) {
                seasons[i] = Short.parseShort(seasonsArray[i].trim());
            }

            return new Series(title, genres, year, rating, seasons); // Opretter og returnerer en ny serie
        } catch (NumberFormatException e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            return null; // Returnerer bare null, hvis parsing fejler
        }
    }
}



