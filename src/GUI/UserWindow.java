package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class UserWindow extends JFrame {

    public UserWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("USER MENU");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JButton btnChangePassword = new JButton("Change password");
        btnChangePassword.addActionListener(arg0 -> {
            dispose();
            ChangePasswordWindow changePasswordWindow = new ChangePasswordWindow(user);
            changePasswordWindow.setVisible(true);
        });
        btnChangePassword.setBounds(81, 11, 250, 23);
        btnChangePassword.setForeground(Color.YELLOW);
        btnChangePassword.setBackground(Color.GRAY);
        mainPanel.add(btnChangePassword);

        JButton btnSeeCategories = new JButton("See the categories of words");
        btnSeeCategories.addActionListener(arg0 -> {
            dispose();
            UserCategoriesWindow userCategoriesWindow = new UserCategoriesWindow(user);
            userCategoriesWindow.setVisible(true);
        });
        btnSeeCategories.setBounds(81, 44, 250, 23);
        btnSeeCategories.setForeground(Color.YELLOW);
        btnSeeCategories.setBackground(Color.GRAY);
        mainPanel.add(btnSeeCategories);

        JButton btnFindByLetter = new JButton("Find words by their first letter");
        btnFindByLetter.addActionListener(arg0 -> {
            dispose();
            FirstLettersWindow firstLettersWindow = new FirstLettersWindow(user);
            firstLettersWindow.setVisible(true);
        });
        btnFindByLetter.setBounds(81, 77, 250, 23);
        btnFindByLetter.setForeground(Color.YELLOW);
        btnFindByLetter.setBackground(Color.GRAY);
        mainPanel.add(btnFindByLetter);

        JButton btnSearchWords = new JButton("Search words");
        btnSearchWords.addActionListener(arg0 -> {
            dispose();
            SearchWordsWindow searchWordsWindow = new SearchWordsWindow(user);
            searchWordsWindow.setVisible(true);
        });
        btnSearchWords.setBounds(81, 110, 250, 23);
        btnSearchWords.setForeground(Color.YELLOW);
        btnSearchWords.setBackground(Color.GRAY);
        mainPanel.add(btnSearchWords);

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
