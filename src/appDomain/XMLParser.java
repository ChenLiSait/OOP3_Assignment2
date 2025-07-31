package appDomain;

import implementations.MyStack;
import implementations.MyQueue;
import utilities.StackADT;
import utilities.QueueADT;
import exceptions.EmptyQueueException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an XML file and reports any mismatched or unclosed tags.
 */
public class XMLParser {
    /**
     * Reads an XML file path from args, validates tag matching, and prints
     * errors with line information. If no errors are found, reports success.
     *
     * @param args args[0] must be the path to the XML file to parse
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java XMLParser <xml_file>");
            return;
        }

        String fileName = args[0];
        StackADT<String> tagStack = new MyStack<>();
        QueueADT<String> errors = new MyQueue<>();
        QueueADT<String> extras = new MyQueue<>();
        // Pattern: group(1)=leading slash, group(2)=tag name, group(3)=self-close slash
        Pattern tagPattern = Pattern.compile("<(/?)([^\\s>/]+)(/?)>");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNo = 0;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                Matcher matcher = tagPattern.matcher(line);
                while (matcher.find()) {
                    String slashOpen = matcher.group(1);
                    String name = matcher.group(2);
                    String slashClose = matcher.group(3);

                    // Skip XML declarations and self-closing tags
                    if (line.trim().startsWith("<?") && line.trim().endsWith("?>")) continue;
                    if ("/".equals(slashClose)) continue;

                    // Opening tag
                    if (slashOpen.isEmpty()) {
                        tagStack.push(name);
                    } else {
                        // Closing tag
                        if (!tagStack.isEmpty() && name.equals(tagStack.peek())) {
                            tagStack.pop();
                        } else {
                            int depth = tagStack.search(name);
                            if (depth > 0) {
                                // Pop intermediate tags as errors
                                while (!name.equals(tagStack.peek())) {
                                    errors.enqueue(formatError(lineNo, tagStack.pop()));
                                }
                                tagStack.pop();
                            } else if (tagStack.isEmpty()) {
                                extras.enqueue(formatError(lineNo, "/" + name));
                            } else {
                                extras.enqueue(formatError(lineNo, "/" + name));
                            }
                        }
                    }
                }
            }
            // Any unclosed tags at EOF
            while (!tagStack.isEmpty()) {
                errors.enqueue(formatErrorEOF(tagStack.pop()));
            }
            // Print results
            if (errors.isEmpty() && extras.isEmpty()) {
                System.out.println("XML document is constructed correctly.");
            } else {
                while (!errors.isEmpty()) System.out.println(errors.dequeue());
                while (!extras.isEmpty()) System.out.println(extras.dequeue());
            }

        } catch (IOException | EmptyQueueException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Builds an error message for a mismatched or unexpected tag.
     *
     * @param line the line number of the error
     * @param tag the tag name, prefixed with '/' if a closing tag
     * @return formatted error string
     */
    private static String formatError(int line, String tag) {
        return "Error at line " + line + " <" + tag + "> is not constructed correctly.";
    }

    /**
     * Builds an error message for tags left unclosed at end of file.
     *
     * @param tag the tag name that was not closed
     * @return formatted EOF error string
     */
    private static String formatErrorEOF(String tag) {
        return "Error at EOF: <" + tag + "> is not constructed correctly.";
    }
}