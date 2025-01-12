package filesystem.exceptions.manager;

import filesystem.exceptions.FileSystemException;

/**
 * Directory not found exception being used by the FileSystemManager.
 */
public class DirectoryNotFoundException extends FileSystemException {
    public DirectoryNotFoundException(String message) {
        super(message);
    }
}
