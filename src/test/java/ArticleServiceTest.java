import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ArticleServiceTest {
    private ArticleService articleService;
    private ConnectorUtil util;
    private Parser parser;

    @Before
    public void init() {
        articleService = new ArticleService();
        util = new ConnectorUtil();
        parser = new Parser();
    }

    @Test
    public void testCountMatchesMethod() throws SQLException {
        String articleContent = util.selectArticle(1L);
        int actual = articleService.countMatches(articleContent, "Republique");
        assertEquals(5, actual);
    }

    @Test
    public void testCountMatchesMethodForNull() throws SQLException {
        String articleContent = util.selectArticle(1L);
        int wordIsNull = articleService.countMatches(articleContent, null);
        assertEquals(-1, wordIsNull);
        int articleIsNull = articleService.countMatches(null, "давно");
        assertEquals(-1, articleIsNull);
    }
}