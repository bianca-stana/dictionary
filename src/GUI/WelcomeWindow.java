package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

public class WelcomeWindow {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                WelcomeWindow welcomeWindow = new WelcomeWindow();
                welcomeWindow.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public WelcomeWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Dictionary");
        frame.setBounds(750, 200, 450, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        frame.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("WELCOME!");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.RED);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(mainPanel);

        JLabel lbUsername = new JLabel("Username:");
        lbUsername.setBounds(10, 15, 75, 14);
        mainPanel.add(lbUsername);

        JTextField tfUsername = new JTextField();
        tfUsername.setBounds(95, 11, 150, 25);
        mainPanel.add(tfUsername);

        JLabel lbPassword = new JLabel("Password:");
        lbPassword.setBounds(10, 43, 75, 21);
        mainPanel.add(lbPassword);

        JPasswordField pfPassword = new JPasswordField();
        pfPassword.setBounds(95, 40, 150, 25);
        mainPanel.add(pfPassword);

        JLabel lbText = new JLabel();
        lbText.setBounds(10, 200, 400, 50);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnLogIn = new JButton("Log in");
        btnLogIn.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                User user = dal.logIn(tfUsername.getText(), pfPassword.getText());
                if (user != null) {
                    switch (user.getType()) {
                        case "Administrator":
                            frame.dispose();
                            AdminWindow adminWindow = new AdminWindow(user);
                            adminWindow.setVisible(true);
                            break;
                        case "Authorized":
                            frame.dispose();
                            UserWindow userWindow = new UserWindow(user);
                            userWindow.setVisible(true);
                            break;
                        case "Unauthorized":
                            lbText.setText("Please wait for an administrator to authorize your account!");
                            break;
                    }
                } else {
                    if (tfUsername.getText().isEmpty() || pfPassword.getText().isEmpty()) {
                        lbText.setText("Please fill in all of the fields!");
                    } else {
                        lbText.setText("The username or password is wrong!");
                    }
                }
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnLogIn.setBounds(10, 90, 80, 23);
        btnLogIn.setForeground(Color.BLACK);
        btnLogIn.setBackground(Color.GRAY);
        mainPanel.add(btnLogIn);

        JButton btnCreateAccount = new JButton("Create account");
        btnCreateAccount.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                dal.createAccount(tfUsername.getText(), pfPassword.getText());
                lbText.setText("Your account was created and is currently pending authorization.");
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnCreateAccount.setBounds(110, 90, 133, 23);
        btnCreateAccount.setForeground(Color.BLACK);
        btnCreateAccount.setBackground(Color.GRAY);
        mainPanel.add(btnCreateAccount);
    }
}
