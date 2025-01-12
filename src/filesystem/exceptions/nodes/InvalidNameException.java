package filesystem.exceptions.nodes;

import filesystem.exceptions.FileSystemException;

/**
 * Basic exception for invalid name.
 */
public class InvalidNameException extends FileSystemException {
    public InvalidNameException(String message) {super(message);}
}
