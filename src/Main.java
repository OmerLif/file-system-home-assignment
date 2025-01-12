import exceptions.DirectoryNotFoundException;
import exceptions.FileSystemNodeException;
import exceptions.NameAlreadyExistsException;
import filesystem.BasicFileSystemManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Initialize FileSystemManager
            BasicFileSystemManager fileSystemManager = new BasicFileSystemManager();

            // Add directories
            fileSystemManager.addDir("root", "Documents");
            fileSystemManager.addDir("root", "Pictures");
            fileSystemManager.addDir("Documents", "Work");

            // Add files
            fileSystemManager.addFile("Documents", "resume.docx", 500);
            fileSystemManager.addFile("Documents", "budget.xlsx", 1200);
            fileSystemManager.addFile("Pictures", "vacation.jpg", 3000);
            fileSystemManager.addFile("Pictures", "profile_pic.jpg", 3000);
            fileSystemManager.addFile("Work", "project.docx", 800);

            // Display the file system
            System.out.println("File System Structure:");
            fileSystemManager.showFileSystem();

            // Get the biggest file
            System.out.println("\nBiggest File: " + fileSystemManager.getBiggestFile());

            // Delete a file
            System.out.println("\nDeleting 'vacation.jpg'...");
            fileSystemManager.delete("vacation.jpg");

            // Display the file system after deletion
            System.out.println("\nFile System Structure After Deletion:");
            fileSystemManager.showFileSystem();

            // Attempt to delete a non-existent file
            try {
                fileSystemManager.delete("non_existent_file.txt");
            } catch (FileSystemNodeException e) {
                System.err.println("Error: " + e.getMessage());
            }

            // Attempt to add a duplicate file
            try {
                fileSystemManager.addFile("Documents", "resume.docx", 300);
            } catch (NameAlreadyExistsException e) {
                System.err.println("Error: " + e.getMessage());
            }

            // Attempt to add a file to a non-existent directory
            try {
                fileSystemManager.addFile("NonExistentDir", "newfile.txt", 100);
            } catch (DirectoryNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            }


            // Delete a directory
            System.out.println("\nDeleting 'Documents dir'...");
            fileSystemManager.delete("Work");
            // Display the file system after deletion
            System.out.println("\nFile System Structure After Deletion:");
            fileSystemManager.showFileSystem();



        } catch (FileSystemNodeException e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }
}