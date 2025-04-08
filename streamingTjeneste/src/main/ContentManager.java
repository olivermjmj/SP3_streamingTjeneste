package main;

public class ContentManager extends Mainmenu {
    private Content[] data;
    private int genreFilter;

    public ContentManager(Content[] data) {
        this.data = data;
    }

    public void loadData(String filename) {
        // TODO: implement loading data from a file
    }


    public void setGenreFilter(int genres) {
        this.genreFilter = genres;
    }

    public int getGenreFilter() {
        return genreFilter;
    }


    public void listGenre() {
        switch (genreFilter) {
            case 1:
                System.out.println("Action");
                break;
            case 2:
                System.out.println("War");
                break;
            case 3:
                System.out.println("Drama");
                break;
            default:
                System.out.println("Unknown genre");
        }
    }
}
