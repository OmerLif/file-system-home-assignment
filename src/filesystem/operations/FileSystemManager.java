package filesystem.operations;

import filesystem.exceptions.FileSystemException;

public interface FileSystemManager {
    // Adds a file to the file system
    void addFile(String parentDirName, String fileName, int fileSize) throws FileSystemException;

    // Adds a directory to the file system
    void addDir(String parentDirName, String dirName) throws FileSystemException;

    // Deletes a file or directory
    void delete(String name) throws FileSystemException;

    // Shows the file system structure
    void showFileSystem();

    // Gets the size of a file
    long getFileSize(String name) throws FileSystemException;
}
