import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileOrganizer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the directory to organize: ");
        String sourceDirectoryPath = scanner.nextLine();
        File sourceDirectory = new File(sourceDirectoryPath);

        if (!sourceDirectory.isDirectory()) {
            System.err.println("Invalid directory path.");
            scanner.close();
            return;
        }

        System.out.println("Organizing files by extension...");

        File[] files = sourceDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        String extension = fileName.substring(dotIndex + 1).toLowerCase();
                        File destinationDirectory = new File(sourceDirectory, extension);
                        if (!destinationDirectory.exists()) {
                            destinationDirectory.mkdirs();
                        }
                        Path sourcePath = Paths.get(file.getAbsolutePath());
                        Path destinationPath = Paths.get(destinationDirectory.getAbsolutePath(), fileName);
                        try {
                            Files.move(sourcePath, destinationPath);
                            System.out.println("Moved '" + fileName + "' to '" + extension + "' directory.");
                        } catch (IOException e) {
                            System.err.println("Error moving '" + fileName + "': " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("File organization complete.");
        } else {
            System.err.println("Could not list files in the directory.");
        }

        scanner.close();
    }
}
