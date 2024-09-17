package gr.codehub.xmlmanagementexercise;

import gr.codehub.xmlmanagementexercise.domain.Book;
import gr.codehub.xmlmanagementexercise.service.StaxTextToXml;
import gr.codehub.xmlmanagementexercise.service.XmlValidator;
import gr.codehub.xmlmanagementexercise.service.JaxbXsdGenerator;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlManagementExercise {

    public static void main(String[] args) throws Exception {
        log.info("Starting the XML Management Exercise Application");

        String inputFilePath = "input_files/sample-lorem-ipsum-text-file.txt";
        String outputFilePath = "output_files/book.xml";
        String xsdFilePath = "output_files/book-schema.xsd";
        String writerFilePath = "output_files/book-chapter-writer.xml";
        String author = "Armando Kostas";
        String applicationClass = XmlManagementExercise.class.getName();

        StaxTextToXml textToXmlService = new StaxTextToXml();
        //SaxReadAndWriteChapters xmlChapterhandler = new SaxReadAndWriteChapters();
        
        try {
            // 1. Parser from Text to XML and Created Statistics 
            textToXmlService.convertToXml(inputFilePath, outputFilePath, author, applicationClass);

            // 2. Provide an XSD for the book XMl 
            JaxbXsdGenerator.xsdGenerator(xsdFilePath);

            // 3. Validate the generated XML against the generated XSD
            boolean isValid = XmlValidator.xmlValidator(outputFilePath, xsdFilePath, Book.class);
            if (isValid) {
                log.info("The XML file is valid against the XSD schema.");
                log.debug("End of XML validation");
            } else {
                log.error("The XML file is NOT valid against the XSD schema.");
                log.debug("End of XML validation");
            }

            // 4. Reader and writer of the XML file for Chapter from 3 to 5
            //xmlChapterhandler.processChapters(outputFilePath, writerFilePath);
            
            // 5. Creating XML with selected paragraphs from an existing XML

        } catch (IOException | XMLStreamException | TransformerException e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }

        log.info("Completed the XML Management Exercise Application");
    }
}
