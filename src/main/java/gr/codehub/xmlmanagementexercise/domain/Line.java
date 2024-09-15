package gr.codehub.xmlmanagementexercise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    private int lineId;
    private String content;
}