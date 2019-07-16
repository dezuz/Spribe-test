import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import table.ArticleTable;
import table.SectionTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    private Document document;
    private String url;
    private List<String> sections = new ArrayList<>();
    private List<String> articles = new ArrayList<>();

    public void parser() throws IOException {
        url = "https://ain.ua/";
        document = Jsoup.connect(url).get();

        document.select("div.tag div.block-title")
                .stream()
                .map(Element::text)
                .distinct()
                .forEach(sections::add);

        document.select(".tag .block-posts .post-item .post-link")
                .stream()
                .map(element -> element.attr("href"))
                .distinct()
                .forEach(articles::add);


        sections.remove(8);
        for (int i = 0; i < 5; i++) {
            articles.remove(40);
        }
        /*sections.remove(8);
        for (int i = 0; i < 5; i++) {
            articles.remove(40);
        }*/
        /*Random random = new Random();
        int x = random.ints(1, 11).findFirst().getAsInt();

        System.out.println(x);

        for (int i = (x * 5) - 5; i < x * 5; i++) {
            System.out.println(postsHref.get(i));
        }*/
    }


    public void setSections(String section) {
        ConnectorUtil util = new ConnectorUtil();
        int index = sections.indexOf(section);
        try {
            SectionTable sectionEntity = new SectionTable()
                    .setName(sections.get(index));
            util.insertSectionsValues(sectionEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setArticles(String article) {
        ConnectorUtil util = new ConnectorUtil();
        try {
            ArticleTable articleEntity = new ArticleTable()
                    .setAuthorName(getAuthorName(articles, articles.indexOf(article)))
                    .setPostedDate(getPostedDate(articles, articles.indexOf(article)))
                    .setName(getName(articles, articles.indexOf(article)))
                    .setContent(getContent(articles, articles.indexOf(article)));
            util.insertArticlesValues(articleEntity);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getContent(List<String> articles, int index) throws IOException {
        url = articles.get(index);
        document = Jsoup.connect(url).get();
        Elements elements = document.select(".post-content");
        elements.select("h1").remove();
        elements.select(".post-data").remove();
        elements.select(".custom-related-posts").remove();

        return elements.text();
    }

    private String getPostedDate(List<String> articles, int index) throws IOException {
        url = articles.get(index);
        document = Jsoup.connect(url).get();
        return document.select(".left-side span")
                .first()
                .text()
                .replaceAll(",", "");
    }

    private String getName(List<String> articles,  int index) throws IOException {
        url = articles.get(index);
        document = Jsoup.connect(url).get();
        return document.select("h1")
                .first()
                .text();
    }

    private String getAuthorName(List<String> articles,  int index) throws IOException {
        url = articles.get(index);
        document = Jsoup.connect(url).get();
        return document.select(".paid-author-wrapper .author .author_name")
                .first()
                .text();
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.parser();
        Long start = System.currentTimeMillis();
        parser.articles.parallelStream().forEach(parser::setArticles);
        parser.sections.parallelStream().forEach(parser::setSections);
        System.out.println(System.currentTimeMillis() - start);
    }
}
