package test.filesystem;

import filesystem.exceptions.FileSystemException;
import filesystem.exceptions.manager.DirectoryNotFoundException;
import filesystem.exceptions.manager.NameAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import filesystem.operations.BasicFileSystemManager;
import static org.junit.Assert.*;

public class BasicFileSystemManagerTest {
    private BasicFileSystemManager fileSystemManager;

    @Before
    public void setUp() throws FileSystemException {
        fileSystemManager = new BasicFileSystemManager();
    }

    @Test
    public void testAddDirectoryStructure() throws FileSystemException {
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("root", "Pictures");
        fileSystemManager.addDir("Documents", "Work");
        // Visual verification
        fileSystemManager.showFileSystem();
    }

    @Test(expected = NameAlreadyExistsException.class)
    public void testAddDuplicateDirectory() throws FileSystemException {
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("root", "Documents"); // Should throw exception
    }

    @Test
    public void testAddFiles() throws FileSystemException {
        // Set up directory structure
        fileSystemManager.addDir("root", "Documents");

        // Add files
        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Documents", "budget.xlsx", 1200);

        // Verify file sizes
        assertEquals(500, fileSystemManager.getFileSize("resume.docx"));
        assertEquals(1200, fileSystemManager.getFileSize("budget.xlsx"));
    }

    @Test(expected = NameAlreadyExistsException.class)
    public void testAddDuplicateFile() throws FileSystemException {
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Documents", "resume.docx", 300); // Should throw exception
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void testAddFileToNonExistentDirectory() throws FileSystemException {
        fileSystemManager.addFile("NonExistentDir", "newfile.txt", 100);
    }

    @Test
    public void testGetBiggestFile() throws FileSystemException {
        // Set up directory structure with files
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("root", "Pictures");

        fileSystemManager.addFile("Documents", "small.txt", 100);
        fileSystemManager.addFile("Pictures", "large.jpg", 6400);
        fileSystemManager.addFile("Documents", "medium.doc", 1000);

        assertEquals("large.jpg", fileSystemManager.getBiggestFile());
    }

    @Test
    public void testFileDelete() throws FileSystemException {
        // Set up directory structure
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("Documents", "Work");
        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Work", "project.docx", 800);

        // Delete a file
        fileSystemManager.delete("resume.docx");

        // Verify file is deleted by checking if size lookup throws exception
        try {
            fileSystemManager.getFileSize("resume.docx");
            fail("Expected FileSystemException was not thrown");
        } catch (FileSystemException e) {
            // Expected
        }

        // Delete a directory
        fileSystemManager.delete("Work");
        fileSystemManager.showFileSystem(); // Visual verification of structure
    }

    @Test
    public void testSimpleDirectoryDelete() throws FileSystemException {
        // Set up directory structure
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("Documents", "Work");
        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Work", "project.docx", 800);

        // Delete a file
        fileSystemManager.delete("Work");

        // Verify "Work" directory is deleted
        try {
            fileSystemManager.delete("Work");
            fail("Expected FileSystemException was not thrown for Work directory");
        } catch (FileSystemException e) {
            // Expected exception for deleted directory
        }

        // Verify child file is deleted as well
        try {
            fileSystemManager.getFileSize("project.docx");
            fail("Expected FileSystemException was not thrown for project.docx file");
        } catch (FileSystemException e) {
            // Expected
        }

        fileSystemManager.showFileSystem(); // Visual verification of structure

    }


    @Test
    public void testRecursiveDirectoryDelete() throws FileSystemException {
        // Set up directory structure
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("Documents", "Work");
        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Work", "project.docx", 800);

        // Delete a file
        fileSystemManager.delete("Documents");

        // Verify "Work" directory is deleted
        try {
            fileSystemManager.delete("Work");
            fail("Expected FileSystemException was not thrown for Work directory");
        } catch (FileSystemException e) {
            // Expected exception for deleted directory
        }

        // Verify child file is deleted as well
        try {
            fileSystemManager.getFileSize("project.docx");
            fail("Expected FileSystemException was not thrown for project.docx file");
        } catch (FileSystemException e) {
            // Expected
        }

        // Verify child file is deleted as well
        try {
            fileSystemManager.getFileSize("resume.docx");
            fail("Expected FileSystemException was not thrown for project.docx file");
        } catch (FileSystemException e) {
            // Expected
        }

        fileSystemManager.showFileSystem(); // Visual verification of structure

    }

    @Test
    public void testShowFileSystem() throws FileSystemException {
        // Set up a complex directory structure
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addDir("root", "Pictures");
        fileSystemManager.addDir("Documents", "Work");

        fileSystemManager.addFile("Documents", "resume.docx", 500);
        fileSystemManager.addFile("Pictures", "vacation.jpg", 6400);
        fileSystemManager.addFile("Work", "project.docx", 800);

        // This is primarily a visual test
        fileSystemManager.showFileSystem();
    }

    @Test
    public void testGetFileSize() throws FileSystemException {
        fileSystemManager.addDir("root", "Documents");
        fileSystemManager.addFile("Documents", "test.txt", 1234);

        assertEquals(1234, fileSystemManager.getFileSize("test.txt"));
    }

    @Test(expected = FileSystemException.class)
    public void testGetFileSizeNonExistent() throws FileSystemException {
        fileSystemManager.getFileSize("nonexistent.txt");
    }

    @Test(expected = FileSystemException.class)
    public void testInvalidFileName() throws FileSystemException {
        String fileName = "a".repeat(33);
        fileSystemManager.addFile("root", fileName, 123);
    }

    @Test(expected = FileSystemException.class)
    public void testInvalidFileSize() throws FileSystemException {
        String fileName = "a".repeat(32);
        fileSystemManager.addFile("root", fileName, -123);
    }
}