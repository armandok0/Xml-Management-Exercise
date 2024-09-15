package gr.codehub.xmlmanagementexercise.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paragraph {
    private int paragraphId;
    private List<String> lines = new ArrayList<>();

}