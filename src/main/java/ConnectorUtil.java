import table.ArticleTable;
import table.SectionTable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorUtil {
    private final String path = "src/main/resources/application.properties";
    private final String ARTICLE_INSERT = "INSERT INTO article(author_name, posted_date, name, content) VALUES (?,?,?,?);";
    private final String SECTION_INSERT = "INSERT INTO section(name) VALUES (?);";
    private final String ARTICLE_SELECT = "SELECT content FROM article WHERE id=(?);";
    private Connection connection;
    private Properties properties;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String url;
    private String username;
    private String password;

    public boolean insertArticlesValues(ArticleTable article) throws SQLException {
        connection = getConnection();
        int wasAdded;
        statement = connection.prepareStatement(ARTICLE_INSERT);
        statement.setString(1, article.getAuthorName());
        statement.setString(2, article.getPostedDate());
        statement.setString(3, article.getName());
        statement.setString(4, article.getContent());
        wasAdded = statement.executeUpdate();
        closeConnection();
        return wasAdded > 0;
    }

    public boolean insertSectionsValues(SectionTable section) throws SQLException {
        connection = getConnection();
        int wasAdded;
        statement = connection.prepareStatement(SECTION_INSERT);
        statement.setString(1, section.getName());
        wasAdded = statement.executeUpdate();
        closeConnection();
        return wasAdded > 0;
    }

    public String selectArticle(Long id) throws SQLException {
        String result = null;
        connection = getConnection();
        statement = connection.prepareStatement(ARTICLE_SELECT);
        statement.setLong(1, id);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result = resultSet.getString("content");
        }
        return result;
    }

    private Connection getConnection() throws SQLException {
        properties = new Properties();
        connection = null;
        try {
            InputStream input = new FileInputStream(path);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    private void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }
}

