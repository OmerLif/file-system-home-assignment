package filesystem.exceptions.manager;

import filesystem.exceptions.FileSystemException;

/**
 * Name duplication error
 */
public class NameAlreadyExistsException extends FileSystemException {
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
