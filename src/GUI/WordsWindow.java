package GUI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class WordsWindow extends JFrame {

    private JTable table;

    public WordsWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("The words in the dictionary");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        initMainPanel(user);
    }

    private void initMainPanel(User user) {
        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(arg0 -> {
            dispose();
            AddWordWindow addWordWindow = new AddWordWindow(user);
            addWordWindow.setVisible(true);
        });
        btnAdd.setBounds(10, 11, 90, 23);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBackground(Color.GRAY);
        mainPanel.add(btnAdd);

        JButton btnModify = new JButton("Modify");
        btnModify.addActionListener(arg0 -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a word!");
            } else {
                int selectedRow = table.getSelectedRow();
                int wordId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
                String word = table.getModel().getValueAt(selectedRow, 1).toString();
                String explanation = table.getModel().getValueAt(selectedRow, 2).toString();
                String category = table.getModel().getValueAt(selectedRow, 3).toString();

                dispose();
                ModifyWordWindow modifyWordWindow = new ModifyWordWindow(user, wordId, word, explanation, category);
                modifyWordWindow.setVisible(true);
            }
        });
        btnModify.setBounds(110, 11, 90, 23);
        btnModify.setForeground(Color.BLACK);
        btnModify.setBackground(Color.GRAY);
        mainPanel.add(btnModify);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(arg0 -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a word!");
            } else {
                int selectedRow = table.getSelectedRow();
                int wordId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
                DataAccessLayer dal = new DataAccessLayer();
                dal.deleteWord(wordId);
                mainPanel.removeAll();
                initMainPanel(user);
            }
        });
        btnDelete.setBounds(210, 11, 90, 23);
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setBackground(Color.GRAY);
        mainPanel.add(btnDelete);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            AdminWindow adminWindow = new AdminWindow(user);
            adminWindow.setVisible(true);
        });
        btnBack.setBounds(312, 11, 90, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);

        // create the table
        String[] columnNames = {"ID", "Word", "Explanation", "Category", "Views"};
        DataAccessLayer dal = new DataAccessLayer();
        Vector<Word> words = dal.getWords();
        Object[][] data = new Object[words.size()][5];
        for (int i = 0; i < words.size(); i++) {
            data[i][0] = words.get(i).getID();
            data[i][1] = words.get(i).getWord();
            data[i][2] = words.get(i).getExplanation();
            data[i][3] = words.get(i).getCategory();
            data[i][4] = words.get(i).getViews();
        }

        table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
