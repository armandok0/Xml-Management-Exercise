package gr.codehub.xmlmanagementexercise.service;

import lombok.extern.slf4j.Slf4j;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TextToXml {

    private Set<String> distinctWords = new HashSet<>();
    private int paragraphCount = 0;
    private int lineCount = 0;
    private int chapterCount = 1;
    private int wordCount = 0;

    public void convertToXml(String inputFilePath, String outputFilePath, String author, String applicationClass) throws IOException, XMLStreamException {
        log.info("Starting Xml Parsing");

        // Initialize file reader and XML writer
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath)); FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("book");

            writer.writeStartElement("chapter");
            writer.writeAttribute("id", String.valueOf(chapterCount));

            processText(reader, writer);

            writer.writeEndElement();
            writeStatistics(writer, author, applicationClass);
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        }

        log.info("Xml file generated successfully");
        log.info("Completed Txt to Xml Parsing");

    }

    private void processText(BufferedReader reader, XMLStreamWriter writer) throws IOException, XMLStreamException {
        String line;
        boolean paragraphOpen = false;
        int lineInParagraph = 1;

        // Skip empty lines
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            if (!paragraphOpen) {
                // Every 20 paragraphs, start a new chapter
                if (paragraphCount % 20 == 0 && paragraphCount != 0) {
                    writer.writeEndElement();
                    chapterCount++;
                    writer.writeStartElement("chapter");
                    writer.writeAttribute("id", String.valueOf(chapterCount));
                }
                paragraphCount++;
                writer.writeStartElement("paragraph");
                writer.writeAttribute("id", String.valueOf(paragraphCount));
                paragraphOpen = true;
                lineInParagraph = 1;
            }

            // Split line into sentences 
            String[] sentences = line.split("(?<=[a-zA-Z0-9][.?!])\\s+(?=[A-Z])");
            for (String sentence : sentences) {
                if (!sentence.trim().isEmpty()) {
                    int count = processWords(sentence);
                    wordCount += count;

                    writer.writeStartElement("line");
                    writer.writeAttribute("id", String.valueOf(lineInParagraph++));
                    writer.writeCharacters(sentence.trim());
                    writer.writeEndElement();
                    lineCount++;
                }
            }

            writer.writeEndElement();
            paragraphOpen = false;
        }
    }

    private void writeStatistics(XMLStreamWriter writer, String author, String applicationClass) throws XMLStreamException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.writeStartElement("statistics");

        writer.writeStartElement("paragraphCount");
        writer.writeCharacters(String.valueOf(paragraphCount));
        writer.writeEndElement();

        writer.writeStartElement("lineCount");
        writer.writeCharacters(String.valueOf(lineCount));
        writer.writeEndElement();

        writer.writeStartElement("wordCount");
        writer.writeCharacters(String.valueOf(wordCount));
        writer.writeEndElement();

        writer.writeStartElement("distinctWordCount");
        writer.writeCharacters(String.valueOf(distinctWords.size()));
        writer.writeEndElement();

        writer.writeStartElement("creationDate");
        writer.writeCharacters(dtf.format(LocalDateTime.now()));
        writer.writeEndElement();

        writer.writeStartElement("author");
        writer.writeCharacters(author);
        writer.writeEndElement();

        writer.writeStartElement("applicationClass");
        writer.writeCharacters(applicationClass);
        writer.writeEndElement();

        writer.writeEndElement();
    }

    private int processWords(String sentence) {
        // Split sentence into words
        String[] words = sentence.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                distinctWords.add(word.toLowerCase());
            }
        }
        return words.length;
    }
}
