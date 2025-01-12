package exceptions;

public class DirectoryNotFoundException extends FileSystemNodeException {
    public DirectoryNotFoundException(String message) {
        super(message);
    }
}
