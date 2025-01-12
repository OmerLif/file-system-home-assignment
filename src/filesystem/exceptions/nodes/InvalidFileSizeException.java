package filesystem.exceptions.nodes;

import filesystem.exceptions.FileSystemException;

/**
 * Basic exception for invalid file size parameter.
 */
public class InvalidFileSizeException extends FileSystemException {
    public InvalidFileSizeException(String message) {
        super(message);
    }
}
