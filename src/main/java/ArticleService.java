public class ArticleService {

    public int countMatches(String article, String word) {
        if(testForNull(article, word)) {
            return -1;
        }

        String[] articleArrays = article.toLowerCase().split("[,. ]");
        int matches = 0;
        for (int i = 0; i < articleArrays.length; i++) {
            if(articleArrays[i].equals(word.toLowerCase())){
                matches++;
            }
        }
        return matches;
    }

    private boolean testForNull(String article, String word) {
        return (word == null || article == null);
    }
}
