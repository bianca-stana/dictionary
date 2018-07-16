package GUI;

import java.awt.*;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class RenameCategoryWindow extends JFrame {

    public RenameCategoryWindow(User user, String category) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Rename category");
        lbTitle.setBounds(10, 11, 75, 15);
        lbTitle.setForeground(Color.BLUE);
        titlePanel.add(lbTitle);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 50, 414, 240);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(mainPanel);

        JLabel lbCategory = new JLabel("New category name:");
        lbCategory.setBounds(10, 15, 130, 14);
        mainPanel.add(lbCategory);

        JTextField tfNewCategory = new JTextField();
        tfNewCategory.setBounds(140, 11, 150, 25);
        mainPanel.add(tfNewCategory);

        JLabel lbText = new JLabel();
        lbText.setBounds(10, 200, 400, 50);
        lbText.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(lbText.getFont().getName(), Font.ITALIC, lbText.getFont().getSize());
        lbText.setFont(font);
        mainPanel.add(lbText);

        JButton btnChangeCategory = new JButton("Save changes");
        btnChangeCategory.addActionListener(arg0 -> {
            DataAccessLayer dal = new DataAccessLayer();
            try {
                dal.renameCategory(category, tfNewCategory.getText());
                lbText.setText("The category name was changed successfully!");
            } catch (Exception e) {
                lbText.setText(e.getMessage());
            }
        });
        btnChangeCategory.setBounds(10, 50, 120, 23);
        btnChangeCategory.setForeground(Color.BLACK);
        btnChangeCategory.setBackground(Color.GRAY);
        mainPanel.add(btnChangeCategory);

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
