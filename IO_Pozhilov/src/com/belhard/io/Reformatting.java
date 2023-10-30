package com.belhard.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Reformatting {
    public static void formattingText() {
        try {
            Path sourceFile = Path.of("resources", "in", "text.txt");
            Path destinationFile = Path.of("resources", "out", "formattedText.txt");

            List<String> lines = Files.readAllLines(sourceFile);

            StringBuilder formattedText = new StringBuilder();
            lines.stream()
            .filter(line -> !line.trim().isEmpty())
            .map(line -> line.replaceAll("\\s+", " ").trim())
            .forEach(line -> formattedText.append(line).append("\n"));

            boolean isFirstWord = true;
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        if (isFirstWord) {
                            formattedText.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                            isFirstWord = false;
                        } else {
                            formattedText.append(word);
                        }
                        formattedText.append(" ");
                        if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                            isFirstWord = true;
                        }
                    }
                }

                formattedText.append("\n");
            }

            if (formattedText.length() > 120) {
                formattedText.append("\n");
            }

            FileWriter fileWriter = new FileWriter(destinationFile.toFile());
            fileWriter.write(formattedText.toString());
            fileWriter.close();

            System.out.println("The file is formatted!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        formattingText();
    }
}
