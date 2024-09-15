package gr.codehub.xmlmanagementexercise.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;

public class XmlValidator {

    public boolean validateXml(String xmlFilePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(xmlFilePath), new DefaultHandler());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}