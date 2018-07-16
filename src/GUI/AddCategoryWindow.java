package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class AddCategoryWindow extends JFrame {

    public AddCategoryWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Add a new category");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbCategory = new JLabel("New category:");
        lbCategory.setBounds(10, 15, 110, 14);
        mainPanel.add(lbCategory);

        JTextField tfCategory = new JTextField();
        tfCategory.setBounds(120, 11, 150, 25);
        mainPanel.add(tfCategory);

        JLabel lbText = new JLabel();
        lbText.setBounds(10, 200, 400, 50);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnAddCategory = new JButton("Add category");
        btnAddCategory.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                dal.addCategory(tfCategory.getText());
                lbText.setText("The category was added successfully to the dictionary!");
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnAddCategory.setBounds(10, 50, 120, 23);
        btnAddCategory.setForeground(Color.BLACK);
        btnAddCategory.setBackground(Color.GRAY);
        mainPanel.add(btnAddCategory);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(arg0 -> {
            dispose();
            AdminCategoriesWindow adminCategoriesWindow = new AdminCategoriesWindow(user);
            adminCategoriesWindow.setVisible(true);
        });
        btnBack.setBounds(150, 50, 80, 23);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(Color.GRAY);
        mainPanel.add(btnBack);
    }
}
