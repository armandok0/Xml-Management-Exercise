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
public class SaxChapterReader {

    public List<String> readChapters(String sourceFilePath, int startChapter, int endChapter) throws Exception {
        if (startChapter > endChapter) {
            log.error("Invalid chapter range: startChapter {} is greater than endChapter {}", startChapter, endChapter);
            throw new IllegalArgumentException("startChapter cannot be greater than endChapter");
        }

        log.info("Reading chapters from {} (startChapter: {}, endChapter: {})", sourceFilePath, startChapter, endChapter);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        ChapterHandler chapterHandler = new ChapterHandler(startChapter, endChapter);

        try {
            saxParser.parse(sourceFilePath, chapterHandler);
        } catch (Exception e) {
            log.error("Error occurred during SAX parsing: {}", e.getMessage(), e);
            throw e;
        }

        log.info("Successfully read chapters");
        return chapterHandler.getChapters();
    }

    static class ChapterHandler extends DefaultHandler {

        private boolean readChapter;
        private int currentChapter;
        private int startChapter;
        private int endChapter;
        private List<String> chapters;
        private StringBuilder chapterContent;

        public ChapterHandler(int startChapter, int endChapter) {
            this.startChapter = startChapter;
            this.endChapter = endChapter;
            this.chapters = new ArrayList<>();
        }

        public List<String> getChapters() {
            return chapters;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("chapter".equals(qName)) {
                currentChapter = Integer.parseInt(attributes.getValue("id"));
                if (currentChapter >= startChapter && currentChapter <= endChapter) {
                    readChapter = true;
                    chapterContent = new StringBuilder();
                    chapterContent.append("<chapter id=\"").append(currentChapter).append("\">");
                    log.info("Started reading chapter {}", currentChapter);
                }
            } else if (readChapter) {
                if ("paragraph".equals(qName)) {
                    String paragraphId = attributes.getValue("id");
                    chapterContent.append("<paragraph id=\"").append(paragraphId).append("\">");
                } else if ("line".equals(qName)) {
                    String lineId = attributes.getValue("id");
                    chapterContent.append("<line id=\"").append(lineId).append("\">");
                } else {
                    chapterContent.append("<").append(qName).append(">");
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (readChapter) {
                chapterContent.append(new String(ch, start, length));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (readChapter) {
                chapterContent.append("</").append(qName).append(">");
                if ("chapter".equals(qName)) {
                    readChapter = false;
                    chapters.add(chapterContent.toString());
                    log.info("Finished reading chapter {}", currentChapter);
                }
            }
        }
    }
}
