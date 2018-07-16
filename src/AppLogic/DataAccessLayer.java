package AppLogic;

import java.sql.*;
import java.util.Vector;

public class DataAccessLayer {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement prepStatement = null;

    private static String CHECK_CONSTRAINT = "CHK_Category";

    private void createConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Dictionar;integratedSecurity=true;");
            connection.setAutoCommit(false);

            System.out.println("Database opened successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(String user, String pass) throws Exception {
        if (user.isEmpty() || pass.isEmpty()) {
            throw new Exception("Please fill in all of the fields!");
        }

        createConnection();

        try {
            String checkQuery = "SELECT * FROM Users WHERE Username = ?";
            prepStatement = connection.prepareStatement(checkQuery);
            prepStatement.setString(1, user);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                if (user.equals(username)) {
                    throw new Exception("This username is already used! Please use another one.");
                }
            }

            String query = "INSERT INTO Users(Username, Password) VALUES(?, ?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, user);
            prepStatement.setString(2, pass);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User logIn(String user, String pass) throws Exception {
        if (user.isEmpty() || pass.isEmpty()) {
            throw new Exception("In order to log in, please fill in all of the fields!");
        }

        createConnection();

        User result = null;
        try {
            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, user);
            prepStatement.setString(2, pass);

            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String type = rs.getString("Type");
                result = new User(id, username, password, type);
            }
            rs.close();
            prepStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void changePassword(int id, String pass) throws Exception {
        if (pass.isEmpty()) {
            throw new Exception("Please fill in all of the fields!");
        }

        createConnection();

        try {
            String query = "UPDATE Users SET Password = ? WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(2, id);
            prepStatement.setString(1, pass);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<User> getAccounts(String userType) {
        createConnection();

        Vector<User> users = new Vector<>();
        try {
            String query;
            if (userType == null || userType.equals("")) {
                query = "SELECT * FROM Users";
            } else {
                query = "SELECT * FROM Users WHERE Type = ?";
                prepStatement = connection.prepareStatement(query);
                prepStatement.setString(1, userType);
            }

            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String type = rs.getString("Type");
                User user = new User(id, username, password, type);
                users.add(user);
            }
            rs.close();
            prepStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void authorizeUser(int id) {
        createConnection();

        try {
            String query = "UPDATE Users SET Type = 'Authorized' WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        createConnection();

        try {
            String query = "DELETE FROM Users WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Word> getWords() {
        createConnection();

        Vector<Word> words = new Vector<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT d.ID, d.Word, d.Explanation, c.Category, d.Views " +
                    "FROM Dictionary d INNER JOIN Categories c ON d.Category = c.ID");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String word = rs.getString("Word");
                String explanation = rs.getString("Explanation");
                String category = rs.getString("Category");
                int views = rs.getInt("Views");
                Word wordObj = new Word(id, word, explanation, category, views);
                words.add(wordObj);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Vector<Word> getCategoryWords(String category) {
        createConnection();

        Vector<Word> words = new Vector<>();
        try {
            String query = "SELECT d.ID, d.Word, d.Explanation FROM Dictionary d " +
                    "INNER JOIN Categories c ON d.Category = c.ID WHERE c.Category = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, category);

            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String word = rs.getString("Word");
                String explanation = rs.getString("Explanation");
                Word wordObj = new Word(id, word, explanation, category, 0);
                words.add(wordObj);
            }
            rs.close();
            prepStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Vector<String> getWordsFirstLetters() {
        createConnection();

        Vector<String> letters = new Vector<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT LEFT(Word, 1) FROM Dictionary");
            while (rs.next()) {
                letters.add(rs.getString(""));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letters;
    }

    public Vector<Word> getWordsByFirstLetter(String letter) {
        createConnection();

        Vector<Word> words = new Vector<>();
        try {
            String query = "SELECT ID, Word, Explanation, Category FROM Dictionary WHERE LEFT(Word, 1) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, letter);

            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String word = rs.getString("Word");
                String explanation = rs.getString("Explanation");
                String category = rs.getString("Category");
                Word wordObj = new Word(id, word, explanation, category, 0);
                words.add(wordObj);
            }
            rs.close();
            prepStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public void incrementViewsNumber(String word) {
        createConnection();

        try {
            String query = "UPDATE Dictionary SET Views = Views + 1 WHERE Word = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, word);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Word getWord(String w) {
        createConnection();

        Word result = null;
        try {
            String query = "SELECT d.ID, d.Word, d.Explanation, c.Category, d.Views " +
                    "FROM Dictionary d INNER JOIN Categories c ON d.Category = c.ID WHERE d.Word = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, w);

            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String word = rs.getString("Word");
                String explanation = rs.getString("Explanation");
                String category = rs.getString("Category");
                int views = rs.getInt("Views");
                result = new Word(id, word, explanation, category, views);
            }
            rs.close();
            prepStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addWord(String word, String explanation, String category) throws Exception {
        if (word.isEmpty()) {
            throw new Exception("Please fill in the word name!");
        }

        createConnection();

        try {
            String checkQuery = "SELECT * FROM Dictionary WHERE Word = ?";
            prepStatement = connection.prepareStatement(checkQuery);
            prepStatement.setString(1, word);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                String w = rs.getString("Word");
                if (word.equals(w)) {
                    throw new Exception("This word is already in the dictionary!");
                }
            }

            String prepQuery = "SELECT * FROM Categories WHERE Category = ?";
            prepStatement = connection.prepareStatement(prepQuery);
            prepStatement.setString(1, category);
            ResultSet rs2 = prepStatement.executeQuery();
            int categoryId = 0;
            while (rs2.next()) {
                categoryId = rs2.getInt("ID");
            }

            String query = "INSERT INTO Dictionary(Word, Explanation, Category) VALUES(?, ?, ?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, word);
            prepStatement.setString(2, explanation);
            prepStatement.setInt(3, categoryId);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyWord(int id, String word, String explanation, String category) throws Exception {
        if (word.isEmpty()) {
            throw new Exception("Please fill in the word name!");
        }

        createConnection();

        try {
            String checkQuery = "SELECT * FROM Dictionary WHERE Word = ? AND ID <> ?";
            prepStatement = connection.prepareStatement(checkQuery);
            prepStatement.setString(1, word);
            prepStatement.setInt(2, id);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                String w = rs.getString("Word");
                if (word.equals(w)) {
                    throw new Exception("This word is already in the dictionary!");
                }
            }

            String prepQuery = "SELECT * FROM Categories WHERE Category = ?";
            prepStatement = connection.prepareStatement(prepQuery);
            prepStatement.setString(1, category);
            ResultSet rs2 = prepStatement.executeQuery();
            int categoryId = 0;
            while (rs2.next()) {
                categoryId = rs2.getInt("ID");
            }

            String query = "UPDATE Dictionary SET Word = ?, Explanation = ?, Category = ? WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, word);
            prepStatement.setString(2, explanation);
            prepStatement.setInt(3, categoryId);
            prepStatement.setInt(4, id);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWord(int id) {
        createConnection();

        try {
            String query = "DELETE FROM Dictionary WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<String> getCategories() {
        createConnection();

        Vector<String> categories = new Vector<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Categories");
            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void addCategory(String category) throws Exception {
        if (category.isEmpty()) {
            throw new Exception("Please fill in the category!");
        }

        createConnection();

        try {
            String checkQuery = "SELECT * FROM Categories WHERE Category = ?";
            prepStatement = connection.prepareStatement(checkQuery);
            prepStatement.setString(1, category);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                String cat = rs.getString("Category");
                if (category.equals(cat)) {
                    throw new Exception("This category already exists!");
                }
            }

            String query = "INSERT INTO Categories(Category) VALUES(?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, category);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void renameCategory(String category, String newCategory) {
        createConnection();

        try {
            String query = "UPDATE Categories SET Category = ? WHERE Category = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, newCategory);
            prepStatement.setString(2, category);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(String category) {
        createConnection();

        try {
            String query = "DELETE FROM Categories WHERE Category = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, category);
            prepStatement.execute();
            prepStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
