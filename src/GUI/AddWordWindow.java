package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class AddWordWindow extends JFrame {

    public AddWordWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Add a new word");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbWord = new JLabel("New word:");
        lbWord.setBounds(10, 15, 110, 14);
        mainPanel.add(lbWord);

        JTextField tfWord = new JTextField();
        tfWord.setBounds(120, 11, 150, 25);
        mainPanel.add(tfWord);

        JLabel lbExplanation = new JLabel("Explanation:");
        lbExplanation.setBounds(10, 44, 110, 14);
        mainPanel.add(lbExplanation);

        JTextField tfExplanation = new JTextField();
        tfExplanation.setBounds(120, 40, 150, 25);
        mainPanel.add(tfExplanation);

        JLabel lbCategory = new JLabel("Category:");
        lbCategory.setBounds(10, 73, 110, 14);
        mainPanel.add(lbCategory);

        // create the categories drop down
        DataAccessLayer dal = new DataAccessLayer();
        Vector<String> categories = dal.getCategories();

        JComboBox list = new JComboBox<>(categories);
        list.setBounds(120, 69, 150, 25);
        Font font2 = new Font(list.getFont().getName(), Font.PLAIN, list.getFont().getSize());
        list.setFont(font2);
        mainPanel.add(list);

        JLabel lbText = new JLabel();
        lbText.setBounds(10, 200, 400, 50);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnAddWord = new JButton("Add word");
        btnAddWord.addActionListener(arg0 -> {
            DataAccessLayer dal2 = new DataAccessLayer();
            try {
                dal2.addWord(tfWord.getText(), tfExplanation.getText(), list.getSelectedItem().toString());
                lbText.setText("The word was added successfully to the dictionary!");
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnAddWord.setBounds(10, 110, 120, 23);
        btnAddWord.setForeground(Color.BLACK);
        btnAddWord.setBackground(Color.GRAY);
        mainPanel.add(btnAddWord);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            WordsWindow wordsWindow = new WordsWindow(user);
            wordsWindow.setVisible(true);
        });
        btnBack.setBounds(150, 110, 80, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);
    }
}
