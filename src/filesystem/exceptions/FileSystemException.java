package filesystem.exceptions;

/**
 * This class represents a generic exception that can be thrown by the file system.
 */
public class FileSystemException extends Throwable {
    public FileSystemException(String message) {
        super(message);
    }
}
