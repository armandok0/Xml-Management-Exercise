package gr.codehub.xmlmanagementexercise.service;

import lombok.extern.slf4j.Slf4j;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SaxParagraphReader {

    public List<String> readParagraphs(String sourceFilePath, int startParagraph, int endParagraph) throws Exception {
        if (startParagraph > endParagraph) {
            log.error("Invalid paragraph range: startParagraph {} is greater than endParagraph {}", startParagraph, endParagraph);
            throw new IllegalArgumentException("StartParagraph cannot be greater than endParagraph");
        }

        log.info("Reading paragraphs {} to {} from {}", startParagraph, endParagraph, sourceFilePath);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        ParagraphHandler handler = new ParagraphHandler(startParagraph, endParagraph);

        try {
            saxParser.parse(sourceFilePath, handler);
        } catch (Exception e) {
            log.error("Error occurred during SAX parsing: {}", e.getMessage(), e);
            throw e;
        }

        log.info("Successfully read selected paragraphs.");
        return handler.getParagraphs();
    }

    static class ParagraphHandler extends DefaultHandler {

        private int currentParagraph;
        private int currentChapterId;
        private boolean inSelectedParagraph;
        private int startParagraph, endParagraph;
        private List<String> paragraphs;
        private StringBuilder chapterContent;
        private boolean isNewChapter = true;

        public ParagraphHandler(int startParagraph, int endParagraph) {
            this.startParagraph = startParagraph;
            this.endParagraph = endParagraph;
            this.paragraphs = new ArrayList<>();
        }

        public List<String> getParagraphs() {
            return paragraphs;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("chapter".equals(qName)) {
                currentChapterId = Integer.parseInt(attributes.getValue("id"));
                if (isNewChapter) {
                    chapterContent = new StringBuilder();
                    chapterContent.append(String.format("<chapter id=\"%d\">", currentChapterId));
                    isNewChapter = false;
                }
            } else if ("paragraph".equals(qName)) {
                currentParagraph = Integer.parseInt(attributes.getValue("id"));
                inSelectedParagraph = currentParagraph >= startParagraph && currentParagraph <= endParagraph;
                if (inSelectedParagraph) {
                    chapterContent.append(String.format("<paragraph id=\"%d\">", currentParagraph));
                    log.info("Started reading Paragraph {} from Chapter {}", currentParagraph, currentChapterId);
                }
            } else if (inSelectedParagraph && "line".equals(qName)) {
                chapterContent.append(String.format("<line id=\"%s\">", attributes.getValue("id")));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (inSelectedParagraph) {
                chapterContent.append(new String(ch, start, length));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (inSelectedParagraph) {
                chapterContent.append("</").append(qName).append(">");
                if ("paragraph".equals(qName)) {
                    log.info("Finished Paragraph {} from Chapter {}", currentParagraph, currentChapterId);
                    inSelectedParagraph = false;
                }
            }
            if ("chapter".equals(qName) && !isNewChapter) {
                chapterContent.append("</chapter>");
                paragraphs.add(chapterContent.toString());
                isNewChapter = true;
            }
        }
    }
}
