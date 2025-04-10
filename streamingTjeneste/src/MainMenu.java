import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainMenu extends JPanel implements ActionListener, ListSelectionListener, ItemListener {

    private final JButton logoutButton;
    private final JLabel text;
    private final JPanel selectedPanel = new JPanel();
    private final JLabel label= new JLabel();
    private final JButton playButton = new JButton("PLAY");
    private final JButton addWatchLaterButton = new JButton("Watch later");
    private final JButton removeButton = new JButton("Remove");
    private final JTextField searchField = new JTextField();
    private final JComboBox genreCb;
    private final JButton searchButton = new JButton("Search");
    private final JComboBox<String> cb;
    private JComboBox<String> ratingCb;
    private JList<Content> list;
    private ContentListModel<Content> listModelContent;

    MainMenu() {
        this.setLayout(new BorderLayout());

        text = new JLabel();
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 0;

        JPanel panelN = new JPanel();
        panelN.add(text);
        panelN.add(logoutButton);

        JPanel panelW = new JPanel(new GridBagLayout());

        // search
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchField.setPreferredSize(new Dimension(100,20));
        panelW.add(searchField, gbc);

        gbc.gridy = 1;
        String[] gchoices = {"All Genres", "Crime", "Drama", "Adventure", "Romance","War", "Sci-Fi","Thriller"};
        genreCb = new JComboBox<String>(gchoices);
        panelW.add(genreCb, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        searchButton.setPreferredSize(new Dimension(100,20));
        searchButton.addActionListener(this);
        panelW.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        String[] choices = {"Movies and Series", "Movies", "Series", "Watch later", "Watch Again"};
        cb = new JComboBox<String>(choices);
        //cb.setMaximumSize(cb.getPreferredSize());
        cb.addItemListener(this);
        panelW.add(cb, gbc);
        String[] rchoices = { "Highest Rating", "Lowest Rating" };
        ratingCb = new JComboBox<String>(rchoices);
        ratingCb.addItemListener(this);
        gbc.gridx = 1;
        panelW.add(ratingCb, gbc);

        JPanel panelE = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelC = new JPanel(new BorderLayout());
        panelC.setMinimumSize(new Dimension(0, 0));

        // Content view
        listModelContent = new ContentListModel<>();
        list = new JList<>(listModelContent);
        list.setVisibleRowCount(0);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.addListSelectionListener(this);
        list.setCellRenderer(new ContentRenderer());
        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        selectedPanel.add(label);
        playButton.addActionListener(this);
        playButton.setBackground(Color.RED);
        playButton.setForeground(Color.WHITE);
        selectedPanel.add(playButton);
        addWatchLaterButton.addActionListener(this);
        selectedPanel.add(addWatchLaterButton);
        removeButton.addActionListener(this);
        selectedPanel.add(removeButton);
        removeButton.hide();

        selectedPanel.hide();
        panelC.add(selectedPanel, BorderLayout.NORTH);
        panelC.add(scrollPane, BorderLayout.CENTER);

        panelW.setBorder(BorderFactory.createLineBorder(Color.black));
        panelC.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(panelN, BorderLayout.NORTH);
        this.add(panelW, BorderLayout.WEST);
        this.add(panelE, BorderLayout.EAST);
        this.add(panelS, BorderLayout.SOUTH);
        this.add(panelC, BorderLayout.CENTER);
    }

    public void update() {
        text.setText("Welcome "+StreamingService.user.name);

        // reset
        searchField.setText("");
        selectedPanel.hide();
        genreCb.setSelectedIndex(0);
        ratingCb.setSelectedIndex(0);
        cb.setSelectedIndex(1);
        listModelContent.loadData();
        listModelContent.setVisible(1);
        listModelContent.sortRating(false);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Content c = list.getSelectedValue();
            if (c == null)
                return;
            selectedPanel.show();
            label.setText("<html><h1>"+c.title+"</h1><br>"+c.genres+"<br>"+c.releaseYear+"<br>"+c.rating+"</html>");
            String path = c.title.replace('\'', '_');
            path = path.replace('&', '_');
            if (c instanceof Movies)
                label.setIcon(new ImageIcon("data/img_movies/"+path+".jpg"));
            else if (c instanceof Series)
                label.setIcon(new ImageIcon("data/img_series/"+path+".jpg"));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cb) {
            int idx = cb.getSelectedIndex();
            if (idx == 0) // both
                listModelContent.setVisible(ContentManager.MOVIES | ContentManager.SERIES);
            else if (idx == 1) // movies
                listModelContent.setVisible(ContentManager.MOVIES);
            else if (idx == 2) // series
                listModelContent.setVisible(ContentManager.SERIES);
            else if (idx == 3)
                listModelContent.setVisible(ContentManager.WATCH_LATER);
            else if (idx == 4)
                listModelContent.setVisible(ContentManager.WATCH_AGAIN);
            if (idx < 3) { // not user watch data
                removeButton.hide();
                addWatchLaterButton.show();
            } else {
                removeButton.show();
                addWatchLaterButton.hide();
            }
            selectedPanel.hide();
        } else if (e.getSource() == ratingCb) {
            listModelContent.sortRating(ratingCb.getSelectedIndex() == 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            selectedPanel.hide();
            if (genreCb.getSelectedIndex() == 0)
                listModelContent.filterTitle(searchField.getText());
            else
                listModelContent.filterTitleGenre(searchField.getText(), (String)genreCb.getSelectedItem());
        }
        if (e.getSource() == logoutButton) {
            StreamingService.user = null;
            StreamingService.showStartMenu();
        }


        //Plays and adds a given piece of content to a 'User has Watched' list
        if (e.getSource() == playButton) {
            Content c = list.getSelectedValue();
            if (c == null) {
                return;
            }


            if(!StreamingService.user.watchAgain.contains(c)) {
                StreamingService.user.watchAgain.add(c);
                StreamingService.user.addContentWatched(c.title);
                JOptionPane.showMessageDialog(this, "You are now watching " + c.title + " for the first time!");
            } else {
                JOptionPane.showMessageDialog(this, "You are now watching " + c.title + "...");
            }

        }

        //Add to 'Watch Later' list
        if (e.getSource() == addWatchLaterButton) {
            Content c = list.getSelectedValue();
            if (c == null) {
                return;
            }

            if(!StreamingService.user.watchLater.contains(c)) {

                StreamingService.user.watchLater.add(c);
                StreamingService.user.addWatchLater(c.title);
                JOptionPane.showMessageDialog(this, "Added to 'watch later'");
            } else {

                JOptionPane.showMessageDialog(this, c.title + " has already been added to 'Watch Later'");
            }

        }
        if (e.getSource() == removeButton) {
            Content c = list.getSelectedValue();
            if (c == null) {
                return;
            }

            if (cb.getSelectedIndex() == 3) {
                if (StreamingService.user.watchLater.contains(c)) {
                    StreamingService.user.watchLater.remove(c);
                    StreamingService.user.removeWatchLater(c.title);
                    selectedPanel.hide();
                    // update displayed content
                    if (genreCb.getSelectedIndex() == 0)
                        listModelContent.filterTitle(searchField.getText());
                    else
                        listModelContent.filterTitleGenre(searchField.getText(), (String) genreCb.getSelectedItem());
                    JOptionPane.showMessageDialog(this, "removed from 'watch later'");
                }
            } else {

                if (StreamingService.user.watchAgain.contains(c)) {
                    StreamingService.user.watchAgain.remove(c);
                    StreamingService.user.removeContentWatched(c.title);
                    selectedPanel.hide();
                    // update displayed content
                    if (genreCb.getSelectedIndex() == 0)
                        listModelContent.filterTitle(searchField.getText());
                    else
                        listModelContent.filterTitleGenre(searchField.getText(), (String) genreCb.getSelectedItem());
                    JOptionPane.showMessageDialog(this, "removed from 'watch again'");
                }

            }
        }
    }
}
