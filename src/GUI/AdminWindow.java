package GUI;

import java.awt.Color;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class AdminWindow extends JFrame {

    public JPanel mainPanel;

    public AdminWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("ADMINISTRATOR MENU");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JButton btnSeeAuthUsers = new JButton("See the authorized users");
        btnSeeAuthUsers.addActionListener(arg0 -> {
            dispose();
            AuthUsersWindow authUsersWindow = new AuthUsersWindow(user);
            authUsersWindow.setVisible(true);
        });
        btnSeeAuthUsers.setBounds(81, 11, 250, 23);
        btnSeeAuthUsers.setForeground(Color.YELLOW);
        btnSeeAuthUsers.setBackground(Color.GRAY);
        mainPanel.add(btnSeeAuthUsers);

        JButton btnSeeUnauthUsers = new JButton("See the unauthorized users");
        btnSeeUnauthUsers.addActionListener(arg0 -> {
            dispose();
            UnauthUsersWindow unauthUsersWindow = new UnauthUsersWindow(user);
            unauthUsersWindow.setVisible(true);
        });
        btnSeeUnauthUsers.setBounds(81, 44, 250, 23);
        btnSeeUnauthUsers.setForeground(Color.YELLOW);
        btnSeeUnauthUsers.setBackground(Color.GRAY);
        mainPanel.add(btnSeeUnauthUsers);

        JButton btnSeeCategories = new JButton("See the categories of the dictionary");
        btnSeeCategories.addActionListener(arg0 -> {
            dispose();
            AdminCategoriesWindow adminCategoriesWindow = new AdminCategoriesWindow(user);
            adminCategoriesWindow.setVisible(true);
        });
        btnSeeCategories.setBounds(81, 77, 250, 23);
        btnSeeCategories.setForeground(Color.YELLOW);
        btnSeeCategories.setBackground(Color.GRAY);
        mainPanel.add(btnSeeCategories);

        JButton btnSeeWords = new JButton("See the words of the dictionary");
        btnSeeWords.addActionListener(arg0 -> {
            dispose();
            WordsWindow wordsWindow = new WordsWindow(user);
            wordsWindow.setVisible(true);
        });
        btnSeeWords.setBounds(81, 110, 250, 23);
        btnSeeWords.setForeground(Color.YELLOW);
        btnSeeWords.setBackground(Color.GRAY);
        mainPanel.add(btnSeeWords);

        JButton btnLogOut = new JButton("Log out");
        btnLogOut.addActionListener(arg0 -> {
            dispose();
            String args[] = {""};
            WelcomeWindow.main(args);
        });
        btnLogOut.setBounds(164, 207, 80, 23);
        btnLogOut.setForeground(Color.YELLOW);
        btnLogOut.setBackground(Color.GRAY);
        mainPanel.add(btnLogOut);
    }
}
