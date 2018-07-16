package GUI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class AdminCategoriesWindow extends JFrame {

    private JTable table;

    public AdminCategoriesWindow(User user) {
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
            AddCategoryWindow addCategoryWindow = new AddCategoryWindow(user);
            addCategoryWindow.setVisible(true);
        });
        btnAdd.setBounds(10, 11, 90, 23);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBackground(Color.GRAY);
        mainPanel.add(btnAdd);

        JButton btnRename = new JButton("Rename");
        btnRename.addActionListener(arg0 -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a category!");
            } else {
                int selectedRow = table.getSelectedRow();
                String category = table.getModel().getValueAt(selectedRow, 0).toString();

                dispose();
                RenameCategoryWindow renameCategoryWindow = new RenameCategoryWindow(user, category);
                renameCategoryWindow.setVisible(true);
            }
        });
        btnRename.setBounds(110, 11, 90, 23);
        btnRename.setForeground(Color.BLACK);
        btnRename.setBackground(Color.GRAY);
        mainPanel.add(btnRename);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(arg0 -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a category!");
            } else {
                int selectedRow = table.getSelectedRow();
                String category = table.getModel().getValueAt(selectedRow, 0).toString();
                DataAccessLayer dal = new DataAccessLayer();
                dal.deleteCategory(category);
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
        String[] columnNames = {"Category"};
        DataAccessLayer dal = new DataAccessLayer();
        Vector<String> categories = dal.getCategories();
        Object[][] data = new Object[categories.size()][1];
        for (int i = 0; i < categories.size(); i++) {
            data[i][0] = categories.get(i);
        }

        table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
