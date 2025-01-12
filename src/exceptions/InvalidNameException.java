package exceptions;

public class InvalidNameException extends FileSystemNodeException {
    public InvalidNameException(String message) {
        super(message);
    }
}
