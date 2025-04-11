import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel implements ActionListener {

    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JLabel msg = new JLabel("");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton registerButton = new JButton("REGISTER");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel msgLabel = new JLabel("");
    JLabel chillLabel = new JLabel("Chill");


    StartMenu() {
        BackgroudPanel backgroud = new BackgroudPanel("data/img/The Godfather part II.jpg");
        backgroud.setLayout(new BoxLayout(backgroud, BoxLayout.Y_AXIS));

        chillLabel.setFont(new Font("Arial" ,Font.BOLD, 40));
        chillLabel.setForeground(Color.RED);
        chillLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chillLabel.setMaximumSize(new Dimension(300, 200));
        chillLabel.setPreferredSize(new Dimension(300, 200));
        chillLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTextField.setMaximumSize(new Dimension(150, 25));
        passwordField.setMaximumSize(new Dimension(150, 25));


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.setBackground(new Color(255, 255, 255));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setMaximumSize(new Dimension(400,200));
        inner.setPreferredSize(new Dimension(300,200));
        inner.setAlignmentX(Component.CENTER_ALIGNMENT);

        GroupLayout layout = new GroupLayout(inner);
        inner.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(userLabel)
                        .addComponent(passwordLabel)
                        .addComponent(loginButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(userTextField)
                        .addComponent(passwordField)
                        .addComponent(showPassword)
                        .addComponent(msgLabel)
                        .addComponent(registerButton))
        );
        layout.setVerticalGroup( layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userLabel)
                        .addComponent(userTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(msgLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(showPassword))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(registerButton))
        );

        msgLabel.setForeground(Color.RED);

        // add Action listener to components
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        showPassword.addActionListener(this);
        passwordField.setEchoChar('*');

        backgroud.add(Box.createHorizontalGlue());
        backgroud.add(chillLabel);
        backgroud.add(Box.createVerticalStrut(50));
        backgroud.add(inner);
        backgroud.add(Box.createVerticalGlue());

        this.add(Box.createVerticalStrut(20)); // spacing
        this.add(chillLabel);
        this.setLayout(new BorderLayout());
        this.add(backgroud, BorderLayout.CENTER);

        /*this.add(Box.createVerticalStrut(20));
        this.add(chillLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(inner);*/




        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            StreamingService.user = User.login(userText, pwdText);
            if (StreamingService.user != null) {
                userTextField.setText("");
                passwordField.setText("");
                msgLabel.setText("");
                StreamingService.showMainMenu();
            } else {
                //JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                msgLabel.setText("Invalid Username or Password");
            }
        }
        // REGISTER button
        if (e.getSource() == registerButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            boolean res = User.register(userText, pwdText);
            if (!res) {
                //JOptionPane.showMessageDialog(this, "Failed to register");
                msgLabel.setText("Failed to register");
            } else {
                msgLabel.setText("");
            }
        }
        // SHOW PASSWORD box
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar('\0');
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}
