package gr.codehub.xmlmanagementexercise;

import gr.codehub.xmlmanagementexercise.domain.Book;
import gr.codehub.xmlmanagementexercise.service.TextToXml;
import gr.codehub.xmlmanagementexercise.service.XmlValidator;
import gr.codehub.xmlmanagementexercise.service.JaxbXsdGenerator;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlManagementExercise {

    public static void main(String[] args)  {
        log.info("Starting the XML Management Exercise Application");

        String inputFilePath = "input_files/sample-lorem-ipsum-text-file.txt";
        String outputFilePath = "output_files/output.xml";
        String xsdFilePath = "output_files/book-schema.xsd";
        String author = "Armando Kostas";
        String applicationClass = XmlManagementExercise.class.getName();

        TextToXml textToXmlService = new TextToXml();
        XmlValidator xmlValidator = new XmlValidator();
        JaxbXsdGenerator xsdGenerator = new JaxbXsdGenerator();

        try {
            // 1. Convert text to XML
            textToXmlService.convertToXml(inputFilePath, outputFilePath, author, applicationClass);

            // 2. Generate XSD for the Book class
            xsdGenerator.xsdGenerator(xsdFilePath);

            // 3. Validate the generated XML against the generated XSD
            boolean isValid = XmlValidator.xmlValidator(outputFilePath, xsdFilePath, Book.class);
            if (isValid) {
                log.info("The XML file is valid against the XSD schema.");
                log.debug("End of XML validation");
            } else {
                log.error("The XML file is NOT valid against the XSD schema.");
                log.debug("End of XML validation");
            }
        } catch (IOException | XMLStreamException | TransformerException e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }

        log.info("Completed the XML Management Exercise Application");
    }
}
