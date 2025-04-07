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

    StartMenu() {
        this.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
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
                        .addComponent(registerButton))
                .addComponent(showPassword)
        );
        layout.setVerticalGroup( layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userLabel)
                        .addComponent(userTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addComponent(showPassword)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(registerButton))
        );

        // add Action listener to components
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        showPassword.addActionListener(this);
        passwordField.setEchoChar('*');

        this.add(inner);
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
                StreamingService.showMainMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
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
                JOptionPane.showMessageDialog(this, "Failed to register");
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
