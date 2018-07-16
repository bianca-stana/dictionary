package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class SearchWordsWindow extends JFrame {

    public SearchWordsWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Search words");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbWord = new JLabel("Word:");
        lbWord.setBounds(10, 15, 75, 14);
        mainPanel.add(lbWord);

        JTextField tfWord = new JTextField();
        tfWord.setBounds(65, 11, 150, 25);
        mainPanel.add(tfWord);

        JLabel lbText = new JLabel();
        lbText.setBounds(250, 15, 200, 14);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                Word word = dal.getWord(tfWord.getText());

                if (word != null) {
                    dispose();
                    WordExplanationWindow wordExplanationWindow = new WordExplanationWindow(user, word.getWord(),
                            word.getExplanation(), word.getCategory(), "SearchWordsWindow");
                    wordExplanationWindow.setVisible(true);
                } else {
                    lbText.setText("No results found for \"" + tfWord.getText() + "\"!");
                }
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnSearch.setBounds(10, 50, 80, 23);
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setBackground(Color.GRAY);
        mainPanel.add(btnSearch);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            UserWindow userWindow = new UserWindow(user);
            userWindow.setVisible(true);
        });
        btnBack.setBounds(110, 50, 80, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);
    }
}
