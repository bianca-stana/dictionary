package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class ModifyWordWindow extends JFrame {

    private JComboBox list;

    public ModifyWordWindow(User user, int wordId, String word, String explanation, String category) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Modify word");
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
        tfWord.setText(word);
        mainPanel.add(tfWord);

        JLabel lbExplanation = new JLabel("New explanation:");
        lbExplanation.setBounds(10, 44, 110, 14);
        mainPanel.add(lbExplanation);

        JTextField tfExplanation = new JTextField();
        tfExplanation.setBounds(120, 40, 150, 25);
        tfExplanation.setText(explanation);
        mainPanel.add(tfExplanation);

        JLabel lbCategory = new JLabel("New category:");
        lbCategory.setBounds(10, 73, 110, 14);
        mainPanel.add(lbCategory);

        // create the categories drop down
        DataAccessLayer dal = new DataAccessLayer();
        Vector<String> categories = dal.getCategories();

        list = new JComboBox<>(categories);
        list.setSelectedItem(category);
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

        JButton btnAddWord = new JButton("Save changes");
        btnAddWord.addActionListener(arg0 -> {
            DataAccessLayer dal2 = new DataAccessLayer();
            try {
                dal2.modifyWord(wordId, tfWord.getText(), tfExplanation.getText(), list.getSelectedItem().toString());
                lbText.setText("The word was modified successfully!");
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
