package gr.codehub.xmlmanagementexercise.service;

import gr.codehub.xmlmanagementexercise.domain.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JaxbXsdGenerator {

    public static void xsdGenerator(String xsdFilePath) {
        log.info("Starting to generate XSD file at {}", xsdFilePath);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);

            jaxbContext.generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                    File file = new File(xsdFilePath);
                    StreamResult result = new StreamResult(file);
                    result.setSystemId(file.toURI().toString());
                    return result;
                }
            });
            log.info("XSD file created successfully at {}", xsdFilePath);
            log.info("End XSD generation");
        } catch (JAXBException e) {
            log.error("JAXBException occurred while generating XSD: {}", e.getMessage(), e);
        } catch (IOException e) {
            log.error("IOException occurred while generating XSD: {}", e.getMessage(), e);
        }
    }
}
