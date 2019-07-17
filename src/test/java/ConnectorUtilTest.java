import org.junit.Before;
import org.junit.Test;
import table.ArticleTable;
import table.SectionTable;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ConnectorUtilTest {
    private ConnectorUtil util;
    private ArticleTable article;
    private SectionTable section;

    @Before
    public void init() {
        util = new ConnectorUtil();
        article = new ArticleTable();
        section = new SectionTable();
    }

    @Test
    public void testInsertArticlesValuesMethodToLoadDataIntoDataBase() throws SQLException {
        article.setAuthorName("Serhij")
                .setPostedDate("17 July 2019")
                .setName("Java Multithreading")
                .setContent("Java support multithreading");
        assertTrue(util.insertArticlesValues(article));
    }

    @Test
    public void testInsertSectionsValuesMethodToLoadDataIntoDataBase() throws SQLException {
        section.setName("Java");
        assertTrue(util.insertSectionsValues(section));
    }

    @Test
    public void testSelectArticlesMethodToSendDataBack() throws SQLException {
        String content = util.selectArticle(46L);
        assertEquals(content,"Java support multithreading");
    }

}