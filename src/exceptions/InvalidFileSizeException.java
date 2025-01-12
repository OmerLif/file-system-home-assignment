package exceptions;

public class InvalidFileSizeException extends FileSystemNodeException {
    public InvalidFileSizeException(String message) {
        super(message);
    }
}
