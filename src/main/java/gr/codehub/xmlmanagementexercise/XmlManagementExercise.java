package gr.codehub.xmlmanagementexercise;

import gr.codehub.xmlmanagementexercise.service.TextToXml;
import gr.codehub.xmlmanagementexercise.service.XmlValidator;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlManagementExercise {

    public static void main(String[] args) throws IOException, XMLStreamException, TransformerException {
        log.info("Starting the XML Management Exercise Application");

        // 1. Parser from Txt to Xml
        String inputFilePath = "input_files/sample-lorem-ipsum-text-file.txt";
        String outputFilePath = "output_files/output.xml";
        String author = "Armando Kostas";
        String applicationClass = XmlManagementExercise.class.getName();

        TextToXml service = new TextToXml();
        XmlValidator validator = new XmlValidator();

        service.convertToXml(inputFilePath, outputFilePath, author, applicationClass);

        if (validator.validateXml(outputFilePath)) {
            log.info("XML is well-formed.");
        } else {
            log.error("XML is not well-formed.");
        }

        // 2. Reader and writer of the XML file
    }
}