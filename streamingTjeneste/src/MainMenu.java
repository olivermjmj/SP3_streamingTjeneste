import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener, ListSelectionListener {

    private JButton logoutButton;
    private JLabel text;
    private JTextField searchField = new JTextField();
    private JButton searchButton = new JButton("Search");
    JList<Content> list;
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

        JPanel panelE = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelC = new JPanel(new BorderLayout());
        panelC.setMinimumSize(new Dimension(0, 0));
        // Data to be displayed in the JTable
        //create the model and add elements
        DefaultListModel<Content> listModel = new DefaultListModel<>();
        for (int i = 0; i < 800; i++)
            listModel.addElement(new Content("Movie"+(i+1), i, 0, 8.4f));
        //create the list
        list = new JList<>(listModel);
        list.setVisibleRowCount(0);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.addListSelectionListener(this);
        list.setCellRenderer(new ContentRenderer());
        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelC.add(scrollPane, BorderLayout.CENTER);

        panelW.setBorder(BorderFactory.createLineBorder(Color.black));
        panelC.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(panelN, BorderLayout.NORTH);
        this.add(panelW, BorderLayout.WEST);
        this.add(panelE, BorderLayout.EAST);
        this.add(panelS, BorderLayout.SOUTH);
        this.add(panelC, BorderLayout.CENTER);

        panelN.add(text);
        panelN.add(logoutButton);

    }

    public void update() {
        text.setText("Welcome "+StreamingService.user.name);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            Content c = list.getSelectedValue();
            System.out.println("Watching "+c.title+" "+c.yearOfRelease+" "+c.rating+"...");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            System.out.println("Search \""+searchField.getText()+"\"");
        }
        if (e.getSource() == logoutButton) {
            StreamingService.user.save();
            StreamingService.user = null;
            StreamingService.showStartMenu();
        }
    }
}
