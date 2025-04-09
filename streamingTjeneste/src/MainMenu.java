import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class MainMenu extends JPanel implements ActionListener, ListSelectionListener, ItemListener {

    private Movies[] movies;
    private Series[] series;
    private DefaultListModel<Content> listModel;
    private DefaultListModel<Content> listModelSeries;
    private JButton logoutButton;
    private JLabel text;
    private JPanel selectedPanel = new JPanel();
    private JLabel label= new JLabel();
    private JButton playButton = new JButton("PLAY");
    private JButton addWatchLaterButton = new JButton("Watch later");
    private JTextField searchField = new JTextField();
    private JButton searchButton = new JButton("Search");
    private JComboBox<String> cb;
    private JList<Content> list;

    MainMenu() {
        this.setLayout(new BorderLayout());

        text = new JLabel();
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JPanel panelN = new JPanel();
        panelN.add(text);
        panelN.add(logoutButton);

        JPanel panelW = new JPanel(new GridBagLayout());

        // search
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchField.setPreferredSize(new Dimension(100,20));
        panelW.add(searchField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        searchButton.setPreferredSize(new Dimension(100,20));
        searchButton.addActionListener(this);
        panelW.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        String[] choices = {"Movies", "Series"};
        cb = new JComboBox<String>(choices);
        //cb.setMaximumSize(cb.getPreferredSize());
        cb.addItemListener(this);
        panelW.add(cb, gbc);

        JPanel panelE = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelC = new JPanel(new BorderLayout());
        panelC.setMinimumSize(new Dimension(0, 0));

        // Content view
        List<Movies> moviesList = Movies.getMoviesFromCSV("data/movieData.csv");
        movies = new Movies[moviesList.size()];
        movies = moviesList.toArray(movies);

        List<Series> seriesList = Series.getSeriesFromCSV("data/seriesData.csv");
        series = new Series[seriesList.size()];
        series = seriesList.toArray(series);

        listModel = new DefaultListModel<>();
        listModelSeries = new DefaultListModel<>();
        list = new JList<>(listModel);
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

        searchField.setText("");
        selectedPanel.hide();
        cb.setSelectedIndex(0);
        listModel = new DefaultListModel<>();
        for (int i = 0; i < movies.length; i++)
            listModel.addElement(movies[i]);
        listModelSeries = new DefaultListModel<>();
        for (int i = 0; i < series.length; i++)
            listModelSeries.addElement(series[i]);
        list.setModel(listModel);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            Content c = list.getSelectedValue();
            if (c == null)
                return;
            selectedPanel.show();
            label.setText("<html><h1>"+c.title+"</h1><br>"+c.releaseYear+"<br>"+c.rating+"</html>");
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
            if (cb.getSelectedItem().equals("Series"))
                list.setModel(listModelSeries);
            else
                list.setModel(listModel);
            selectedPanel.hide();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String query = searchField.getText().toLowerCase();
            if (cb.getSelectedIndex() == 0) {
                for (Movies movie : movies) {
                    if (movie.title.toLowerCase().contains(query) || movie.getMoviesGenre().toLowerCase().contains(query)) { //title or moviegenres
                        if (!listModel.contains(movie))
                            listModel.addElement(movie);
                    } else if (listModel.contains(movie)) {
                        listModel.removeElement(movie);
                    }
                }
            } else if (cb.getSelectedIndex() == 1) {
                for (Series serie : series) {
                    if (serie.title.toLowerCase().contains(query)  || serie.getSeriesGenre().toLowerCase().contains(query)) { //title or seriesgenres
                        if (!listModelSeries.contains(serie))
                            listModelSeries.addElement(serie);
                    } else if (listModelSeries.contains(serie)) {
                        listModelSeries.removeElement(serie);
                    }
                }
            }
        }
        if (e.getSource() == logoutButton) {
            StreamingService.user.save();
            StreamingService.user = null;
            StreamingService.showStartMenu();
        }
        if (e.getSource() == playButton) {
            Content c = list.getSelectedValue();
            if (c != null)
                JOptionPane.showMessageDialog(this, "Now playing "+c.title);
        }
        if (e.getSource() == addWatchLaterButton) {
            Content c = list.getSelectedValue();
            if (c == null)
                return;
            System.out.println("FIXME: Add "+c.title+" to watch later");
        }
    }
}
