package main;

import java.util.ArrayList;

public abstract class MainMenu {
    /*+ WatchHistoryDATA / continue watching
    + WatchLater // UserWatchLaterData

    - Logout
    - abstract  returnToMainMenu{}
    + Search
    + run();
    + exit();*/

    ArrayList<String> WatchHistoryData = new ArrayList<>();
    ArrayList <String> WatchLater = new ArrayList<>();
    MainMenu(ArrayList<String> WatchHistory, ArrayList<String> WatchLater){

    }

    public void exit(){}
    private void Logout (){}
    public abstract void returnToMainMenu();

}
