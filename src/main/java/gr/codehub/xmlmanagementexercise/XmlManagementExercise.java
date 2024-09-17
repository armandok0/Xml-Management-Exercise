package gr.codehub.xmlmanagementexercise;

import gr.codehub.xmlmanagementexercise.domain.Book;
import gr.codehub.xmlmanagementexercise.service.StaxTextToXml;
import gr.codehub.xmlmanagementexercise.service.XmlValidator;
import gr.codehub.xmlmanagementexercise.service.JaxbXsdGenerator;
import gr.codehub.xmlmanagementexercise.service.SaxChapterReader;
import gr.codehub.xmlmanagementexercise.service.SaxChapterWriter;
import java.io.IOException;
import java.util.List;
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
        int startChapter = 2;
        int endChapter = 4;

        StaxTextToXml textToXmlService = new StaxTextToXml();
        SaxChapterReader chapterReader = new SaxChapterReader();
        SaxChapterWriter chapterWriter = new SaxChapterWriter();

        try {
            // 1. Parser from Text to XML with created Statistics 
            textToXmlService.convertToXml(inputFilePath, outputFilePath, author, applicationClass);

            // 2. Provide an XSD for the book XMl 
            JaxbXsdGenerator.xsdGenerator(xsdFilePath);

            // 3. Validate the generated XML against the generated XSD
            boolean isValid = XmlValidator.xmlValidator(outputFilePath, xsdFilePath, Book.class);
            if (isValid) {
                log.info("The XML file is valid against the XSD schema.");
                log.info("End of XML validation");
            } else {
                log.error("The XML file is NOT valid against the XSD schema.");
                log.info("End of XML validation");
            }

            // 4. Read Chapters at a Selected Range from book XML 
            if (startChapter <= endChapter) {
                List<String> chapters = chapterReader.readChapters(outputFilePath, startChapter, endChapter);

                // 5. Write the Chapters read above in a new XML file
                chapterWriter.writeChapters(writerFilePath, chapters);
            } else {
                log.error("Skipping chapter reading and writing: startChapter {} is greater than endChapter {}", startChapter, endChapter);
            }

            // 6. Read  paragraphs from Selected Chapter Range
            
            // 7. Write the paragraphs read above in a new XML file
            
        } catch (IOException | XMLStreamException | TransformerException | IllegalArgumentException e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }

        log.info("Completed the XML Management Exercise Application");
    }
}