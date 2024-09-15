package gr.codehub.xmlmanagementexercise.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private int chapterId;
    private List<Paragraph> paragraphs = new ArrayList<>();
}
