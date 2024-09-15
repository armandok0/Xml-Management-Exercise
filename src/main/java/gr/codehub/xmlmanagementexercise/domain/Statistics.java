package gr.codehub.xmlmanagementexercise.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private int paragraphCount;
    private int lineCount;
    private int wordCount;
    private int distinctWordCount;
    private String author;
    private String creationDate;
    private String applicationClass;
}
