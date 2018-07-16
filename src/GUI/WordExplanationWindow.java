package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class WordExplanationWindow extends JFrame {

    private JComboBox list;

    public WordExplanationWindow(User user, String word, String explanation, String category, String backWindow) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel(word);
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.GRAY);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbExplanation = new JLabel(explanation);
        lbExplanation.setBounds(10, 46, 394, 185);
        lbExplanation.setVerticalAlignment(JLabel.TOP);
        Font font = new Font(lbExplanation.getFont().getName(), Font.PLAIN, lbExplanation.getFont().getSize());
        lbExplanation.setFont(font);
        mainPanel.add(lbExplanation);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();

            switch (backWindow) {
                case "CategoryWordsWindow":
                    CategoryWordsWindow categoryWordsWindow = new CategoryWordsWindow(user, category);
                    categoryWordsWindow.setVisible(true);
                    break;
                case "FirstLettersWindow":
                    FirstLettersWindow firstLettersWindow = new FirstLettersWindow(user);
                    firstLettersWindow.setVisible(true);
                    break;
                case "SearchWordsWindow":
                    SearchWordsWindow searchWordsWindow = new SearchWordsWindow(user);
                    searchWordsWindow.setVisible(true);
                    break;
            }
        });
        btnBack.setBounds(312, 11, 90, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);

        DataAccessLayer dal = new DataAccessLayer();
        dal.incrementViewsNumber(word);
    }
}
