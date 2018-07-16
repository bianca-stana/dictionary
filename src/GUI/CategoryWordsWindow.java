package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class CategoryWordsWindow extends JFrame {

    public CategoryWordsWindow(User user, String category) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel(category + " words");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbSeeExplanation = new JLabel("Click on a word to see its full explanation.");
        lbSeeExplanation.setBounds(10, 15, 250, 15);
        Font font = new Font(lbSeeExplanation.getFont().getName(), Font.PLAIN, lbSeeExplanation.getFont().getSize());
        lbSeeExplanation.setFont(font);
        mainPanel.add(lbSeeExplanation);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            UserCategoriesWindow userCategoriesWindow = new UserCategoriesWindow(user);
            userCategoriesWindow.setVisible(true);
        });
        btnBack.setBounds(312, 11, 90, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);

        // create the table
        String[] columnNames = {"Word", "Explanation"};
        DataAccessLayer dal = new DataAccessLayer();
        Vector<Word> words = dal.getCategoryWords(category);
        Object[][] data = new Object[words.size()][2];
        for (int i = 0; i < words.size(); i++) {
            data[i][0] = words.get(i).getWord();
            data[i][1] = words.get(i).getExplanation();
        }

        JTable table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            String word = table.getModel().getValueAt(selectedRow, 0).toString();
            String explanation = table.getModel().getValueAt(selectedRow, 1).toString();

            dispose();
            WordExplanationWindow wordExplanationWindow = new WordExplanationWindow(user, word, explanation, category, "CategoryWordsWindow");
            wordExplanationWindow.setVisible(true);
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
