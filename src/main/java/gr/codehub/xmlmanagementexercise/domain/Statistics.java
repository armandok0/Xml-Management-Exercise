package gr.codehub.xmlmanagementexercise.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {
    @XmlElement
    private int paragraphCount;
    @XmlElement
    private int lineCount;
    @XmlElement
    private int wordCount;
    @XmlElement
    private int distinctWordCount;
    @XmlElement
    private String creationDate;
    @XmlElement
    private String author;
    @XmlElement
    private String applicationClass;
}
