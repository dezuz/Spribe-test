package table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleTable {

    private Long id;
    private String authorName;
    private String postedDate;
    private String name;
    private String content;
    private Long sectionId;
}
