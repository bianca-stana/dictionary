package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class FirstLettersWindow extends JFrame {

    private JScrollPane scrollPane;
    private JTable table;

    public FirstLettersWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Find words by their first letter");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbLetter = new JLabel("First letter:");
        lbLetter.setBounds(10, 15, 75, 14);
        mainPanel.add(lbLetter);

        // create the letters drop down
        DataAccessLayer dal = new DataAccessLayer();
        Vector<String> letters = dal.getWordsFirstLetters();

        JComboBox list = new JComboBox<>(letters);
        list.setBounds(90, 11, 50, 25);
        Font font = new Font(list.getFont().getName(), Font.PLAIN, list.getFont().getSize());
        list.setFont(font);
        mainPanel.add(list);

        JButton btnGetWords = new JButton("Get the words");
        btnGetWords.addActionListener(arg0 -> {
            String[] columnNames = {"Word", "Explanation"};
            DataAccessLayer dal2 = new DataAccessLayer();
            Vector<Word> words = dal2.getWordsByFirstLetter(list.getSelectedItem().toString());
            Object[][] data = new Object[words.size()][2];
            for (int i = 0; i < words.size(); i++) {
                data[i][0] = words.get(i).getWord();
                data[i][1] = words.get(i).getExplanation();
            }

            mainPanel.remove(scrollPane);

            table = new JTable(data, columnNames);
            table.setDefaultEditor(Object.class, null);
            table.getSelectionModel().addListSelectionListener(event -> {
                int selectedRow = table.getSelectedRow();
                String word = table.getModel().getValueAt(selectedRow, 0).toString();
                String explanation = table.getModel().getValueAt(selectedRow, 1).toString();

                DataAccessLayer dal3 = new DataAccessLayer();
                try {
                    Word w = dal3.getWord(word);

                    dispose();
                    WordExplanationWindow wordExplanationWindow = new WordExplanationWindow(user, word, explanation,
                            w.getCategory(), "FirstLettersWindow");
                    wordExplanationWindow.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            });

            scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            scrollPane.setBounds(10, 46, 394, 185);
            mainPanel.add(scrollPane);
        });
        btnGetWords.setBounds(160, 11, 120, 23);
        btnGetWords.setForeground(Color.BLACK);
        btnGetWords.setBackground(Color.GRAY);
        mainPanel.add(btnGetWords);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            UserWindow userWindow = new UserWindow(user);
            userWindow.setVisible(true);
        });
        btnBack.setBounds(312, 11, 90, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);

        // create the table
        String[] columnNames = {"Word", "Explanation"};
        Object[][] data = new Object[0][2];

        table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);

        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
