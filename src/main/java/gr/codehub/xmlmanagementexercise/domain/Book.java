package gr.codehub.xmlmanagementexercise.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private List<Chapter> chapters = new ArrayList<>();
    private Statistics statistics;
}