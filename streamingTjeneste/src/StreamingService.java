import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class StreamingService {
    public static User user;
    private static JPanel cards;
    private final static String MAINPANEL = "MainMenu";
    private final static String STARTPANEL = "StartMenu";

    private static MainMenu mainMenu;
    private static StartMenu startMenu;

    public static void showStartMenu() {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, STARTPANEL);
    }

    public static void showMainMenu() {
        mainMenu.update();
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, MAINPANEL);
    }

    private static void createGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame();

        // Set frame properties
        frame.setTitle("Streaming Service");
        frame.setResizable(true);
        frame.setSize(1280, 720);
        frame.setPreferredSize(new Dimension(1280, 720));

        frame.setIconImage(new ImageIcon("data/img/icon.png").getImage());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Create and set up the content pane.
        startMenu = new StartMenu();
        mainMenu = new MainMenu();

        // Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(startMenu, STARTPANEL);
        cards.add(mainMenu, MAINPANEL);

        frame.getContentPane().add(cards, BorderLayout.CENTER);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static void start() {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
