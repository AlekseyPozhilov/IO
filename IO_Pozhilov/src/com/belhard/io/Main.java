package com.belhard.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void CopyImages() {
        Path sourceDirectory = Path.of("C:\\Users\\User\\Java\\Eclipse\\IO_Pozhilov\\resources\\in");
        Path targetDirectory = Path.of("C:\\Users\\User\\Java\\Eclipse\\IO_Pozhilov\\resources\\out");

        try {
            Files.createDirectories(targetDirectory);

            List<Path> files = Files.walk(sourceDirectory).filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".jpeg"))
                    .collect(Collectors.toList());

            for (Path file : files) {
                Path relativePath = sourceDirectory.relativize(file);
                Path targetPath = targetDirectory.resolve(relativePath);

                Files.copy(file, targetPath);
                RecordingСopyHistory(file.getFileName().toString(), file);
            }

            System.out.println("All JPG files were copied!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RecordingСopyHistory(String fileName, Path file) throws IOException {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String historyEntry = dateTime + " - added image: " + fileName + ", size: " + getFileSize(file) + " bytes\n";
        
        try (FileWriter fr = new FileWriter("C:\\Users\\User\\Java\\Eclipse\\IO_Pozhilov\\resources\\out\\history.txt",
                true)) {
            fr.append(historyEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getFileSize(Path file) throws IOException {
        return Files.size(file);
    }
    
    public static void main(String[] args) {
        CopyImages();
    }
}
