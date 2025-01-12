package exceptions;

public class NameAlreadyExistsException extends FileSystemNodeException {
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
