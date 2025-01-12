package filesystem;

import exceptions.FileSystemNodeException;

public interface FileSystemManager {
    // Adds a file to the file system
    void addFile(String parentDirName, String fileName, int fileSize) throws FileSystemNodeException;

    // Adds a directory to the file system
    void addDir(String parentDirName, String dirName) throws FileSystemNodeException;

    // Deletes a file or directory
    void delete(String name) throws FileSystemNodeException;

    // Shows the file system structure
    void showFileSystem();
}
