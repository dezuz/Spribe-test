import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import table.ArticleTable;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class ParserTest {
    private Parser parser;
    private ConnectorUtil util;
    private ArticleService service;
    private ArticleTable article;

    @Before
    public void init() {
        parser = new Parser();
        util = new ConnectorUtil();
        service = new ArticleService();
        article = new ArticleTable();
    }

    @Test
    public void testParserMethodToTakeArticlesFromWebsite() throws IOException {
        int articleListSizeBefore = parser.getArticles().size();
        int sectionListSizeBefore = parser.getSections().size();
        parser.parser();
        int articleListSize = parser.getArticles().size();
        int sectionListSize = parser.getSections().size();
        assertNotEquals(articleListSizeBefore, articleListSize);
        assertNotEquals(sectionListSizeBefore, sectionListSize);
    }

    @Test
    public void testSetArticlesMethodToLoadDataInParallel() throws IOException {
        parser.parser();
        parser.getArticles().parallelStream().map(x->parser.setArticles(x)).forEach(Assert::assertNotNull);
    }

    @Test
    public void testSetSectionsMethodToLoadDataInParallel() throws IOException {
        parser.parser();
        parser.getSections().parallelStream().map(x->parser.setSections(x)).forEach(Assert::assertNotNull);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetArticlesMethodForNull() throws IOException {
        parser.parser();
        parser.setArticles(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSectionsMethodForNull() throws IOException {
        parser.parser();
        parser.setSections(null);
    }
}