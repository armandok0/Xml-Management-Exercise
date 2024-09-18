package gr.codehub.xmlmanagementexercise.service;

import lombok.extern.slf4j.Slf4j;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.helpers.AttributesImpl;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.transform.sax.SAXResult;

@Slf4j
public class SaxWriter {

    public void writeFile(String destinationFilePath, List<String> chapters) throws Exception {
        log.info("Writing to {}", destinationFilePath);

        SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();

        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        try (FileOutputStream outputStream = new FileOutputStream(destinationFilePath)) {
            StreamResult result = new StreamResult(outputStream);
            handler.setResult(result);

            handler.startDocument();

            AttributesImpl attr = new AttributesImpl();
            handler.startElement("", "", "book", attr);

            for (String chapter : chapters) {
                StreamSource chapterSource = new StreamSource(new java.io.StringReader(chapter));
                factory.newTransformer().transform(chapterSource, new SAXResult(handler));
            }

            handler.endElement("", "", "book");

            handler.endDocument();
        } catch (Exception e) {
            log.error("Error occurred while writing chapters: {}", e.getMessage(), e);
            throw e;
        }

        log.info("Successfully written to {}", destinationFilePath);
    }
}
