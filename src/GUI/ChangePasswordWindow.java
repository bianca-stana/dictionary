package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class ChangePasswordWindow extends JFrame {

    public ChangePasswordWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Change password");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbPassword = new JLabel("New password:");
        lbPassword.setBounds(10, 15, 100, 14);
        mainPanel.add(lbPassword);

        JTextField tfPassword = new JTextField();
        tfPassword.setBounds(110, 11, 150, 25);
        mainPanel.add(tfPassword);

        JLabel lbText = new JLabel();
        lbText.setBounds(10, 200, 400, 50);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnChangePassword1 = new JButton("Save changes");
        btnChangePassword1.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                dal.changePassword(user.getID(), tfPassword.getText());
                lbText.setText("Your password was changed successfully!");
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnChangePassword1.setBounds(10, 50, 120, 23);
        btnChangePassword1.setForeground(Color.BLACK);
        btnChangePassword1.setBackground(Color.GRAY);
        mainPanel.add(btnChangePassword1);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            UserWindow userWindow = new UserWindow(user);
            userWindow.setVisible(true);
        });
        btnBack.setBounds(150, 50, 80, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);
    }
}
