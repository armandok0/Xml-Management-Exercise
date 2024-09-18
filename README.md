# XMl Management Exercise

## Overview
This project is a convertion of a raw text document into an XML file.This is done with a specific structure to its elements  **book**, **chapters**, **paragraphs**, **lines**, and **statistics**. It also generates some statistics such as paragraph count, line count, word count, and distinct word count, incorporating metadata like the document's creation date, the author's name, and the application class name. The application operates standalone, demonstrating its functionality through various use cases without user interaction.

## How It Works
The application processes the text as follows:
- Each paragraph is defined as a file's line.
- Each line is defined by where we see punctuation marks like periods or other similar endpoints.
- A chapter is created for every 20 paragraphs.
- It groups lines into paragraphs, and paragraphs into chapters.
- Calculates statistics including paragraph count, word count, and distinct word count.
  
## Project Structure
- **gr.codehub.xmlmanagementexercise**: Main package with the application's entry point.

- **gr.codehub.xmlmanagementexercise.domain**: Contains domain models for the XML document structure.

- **gr.codehub.xmlmanagementexercise.service**: Implements services for text parsing, XML generation, validation, and statistics generation.
  - **StaxTextToXml**: Handles raw text to XML conversion.
  - **SaxChapterReader**: Reads selected chapters from an XML document.
  - **SaxParagraphReader**: Reads specific paragraphs from an XML document.
  - **SaxWriter**: Writes selected XML chapters or paragraphs into a new file.
  - **XmlValidator**: Validates the XML file against the provided XSD schema.
  - **JaxbXsdGenerator**: Generates an XSD schema from the `Book` class.

## Output Files
- **XML File**: `output_files/book.xml`
- **XSD File**: `output_files/book-schema.xsd`
- **Selected Chapters**: `output_files/book-selected-chapters.xml`
- **Selected Paragraphs**: `output_files/book-selected-paragraphs.xml`

## Technologies Used
- **StAX (Streaming API for XML)**: For efficient raw text parsing and XML conversion.
- **SAX (Simple API for XML)**: For reading and writing specific sections of the XML document.
- **JAXB**: For XSD schema generation and XML validation.
- **Lombok**: Reduces code, and useful for logging usage.

## Requirements and Setup
To run this project, the following steps and requirements must be met:
1. **Java SE 21 Installation**: Ensure Java SE 21 is installed and configured on your system.
2. **Clone Repository**: Clone the project repository from its source.
3. **Run the Application**: Execute `XmlManagementExercise.java` to start the application and view the XML outputs.

