package gr.codehub.xmlmanagementexercise.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import org.xml.sax.SAXException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlValidator {

    public static boolean xmlValidator(String xmlFileName, String xsdFileName, Class<?> xmlClass) {
        log.info("Starting XML validation for {}", xmlFileName);
        boolean isValid = false;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));
            unmarshaller.setSchema(schema);

            File xmlFile = new File(xmlFileName);
            Object object = unmarshaller.unmarshal(xmlFile);
            isValid = true;

        } catch (JAXBException | SAXException e) {
            log.error("Invalid XML: {}", e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred during validation: {}", e.getMessage(), e);
        }
        return isValid;
    }
}
