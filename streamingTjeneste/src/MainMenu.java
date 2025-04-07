import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel implements ActionListener {


    private JButton button;
    private JLabel text;

    MainMenu() {
        this.setLayout(new BorderLayout());


        text = new JLabel();
        button = new JButton("Back");
        button.addActionListener(this);

        JPanel panelN = new JPanel();
        JPanel panelW = new JPanel();
        JPanel panelE = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelC = new JPanel();

        panelN.setBackground(Color.red);
        panelW.setBackground(Color.green);
        panelE.setBackground(Color.yellow);
        panelS.setBackground(Color.magenta);
        panelC.setBackground(Color.blue);

        panelN.setPreferredSize(new Dimension(100,100));
        panelW.setPreferredSize(new Dimension(100,100));
        panelE.setPreferredSize(new Dimension(100,100));
        panelS.setPreferredSize(new Dimension(100,100));
        panelC.setPreferredSize(new Dimension(100,100));

        this.add(panelN, BorderLayout.NORTH);
        this.add(panelW, BorderLayout.WEST);
        this.add(panelE, BorderLayout.EAST);
        this.add(panelS, BorderLayout.SOUTH);
        this.add(panelC, BorderLayout.CENTER);

        panelN.add(text);
        panelN.add(button);

    }
    Arraylist <String> Genre;

    public void update() {
        text.setText("Welcome "+StreamingService.user.name);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //JOptionPane.showMessageDialog(this, "Button Clicked! ");
        StreamingService.showStartMenu();
    }
}
