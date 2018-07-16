package GUI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.*;

import AppLogic.*;

@SuppressWarnings("serial")
public class AuthUsersWindow extends JFrame {

    private JTable table;

    public AuthUsersWindow(User user) {
        this.setTitle("Dictionary");
        this.setBounds(750, 200, 450, 350);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(10, 10, 414, 40);
        this.getContentPane().add(titlePanel);

        JLabel lbTitle = new JLabel("Authorized users");
        lbTitle.setForeground(Color.BLUE);
        lbTitle.setBounds(10, 11, 75, 15);
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

        JButton btnDeleteAccount = new JButton("Delete account");
        btnDeleteAccount.addActionListener(arg0 -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a user!");
            } else {
                int selectedRow = table.getSelectedRow();
                int userId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
                DataAccessLayer dal = new DataAccessLayer();
                dal.deleteUser(userId);

                mainPanel.removeAll();
                initMainPanel(user);
            }
        });
        btnDeleteAccount.setBounds(10, 11, 130, 23);
        btnDeleteAccount.setForeground(Color.BLACK);
        btnDeleteAccount.setBackground(Color.GRAY);
        mainPanel.add(btnDeleteAccount);

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
        String[] columnNames = {"ID", "Username", "Password"};
        DataAccessLayer dal = new DataAccessLayer();
        Vector<User> accounts = dal.getAccounts("Authorized");
        Object[][] data = new Object[accounts.size()][3];
        for (int i = 0; i < accounts.size(); i++) {
            data[i][0] = accounts.get(i).getID();
            data[i][1] = accounts.get(i).getUsername();
            data[i][2] = accounts.get(i).getPassword();
        }

        table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(10, 46, 394, 185);
        mainPanel.add(scrollPane);
    }
}
