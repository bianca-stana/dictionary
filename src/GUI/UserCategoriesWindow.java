package GUI;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class UserCategoriesWindow extends JFrame {

    public UserCategoriesWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("The categories of words in the dictionary");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbSeeExplanation = new JLabel("Click on a category to see the words it contains.");
        lbSeeExplanation.setBounds(10, 15, 350, 15);
        Font font = new Font(lbSeeExplanation.getFont().getName(), Font.PLAIN, lbSeeExplanation.getFont().getSize());
        lbSeeExplanation.setFont(font);
        mainPanel.add(lbSeeExplanation);

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
        String[] columnNames = {"Category"};
        DataAccessLayer dal = new DataAccessLayer();
        Vector<String> categories = dal.getCategories();
        Object[][] data = new Object[categories.size()][1];
        for (int i = 0; i < categories.size(); i++) {
            data[i][0] = categories.get(i);
        }

        JTable table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            String category = table.getModel().getValueAt(selectedRow, 0).toString();

            dispose();
            CategoryWordsWindow categoryWordsWindow = new CategoryWordsWindow(user, category);
            categoryWordsWindow.setVisible(true);
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
