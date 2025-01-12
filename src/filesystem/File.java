package filesystem;

import exceptions.FileSystemNodeException;
import exceptions.InvalidFileSizeException;

/**
 * Represents a file in the file system.
 * Files have a name, creation date, and size.
 */
public class File extends FileSystemNode {
    private final long size;

    /**
     * Creates a new File with the specified name and size.
     *
     * @param name The name of the file
     * @param size The size of the file in bytes
     * @throws FileSystemNodeException if the name is invalid or the size is negative
     */
    public File(String name, int size) throws FileSystemNodeException {
        super(name);
        validateSize(size);
        this.size = size;
    }

    /**
     * Validates that the file size is non-negative.
     *
     * @param size The size to validate
     * @throws InvalidFileSizeException if the size is negative
     */
    private void validateSize(int size) throws InvalidFileSizeException {
        if (size < 0) {
            throw new InvalidFileSizeException(
                    String.format("File size cannot be negative: %d", size)
            );
        }
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("%s [size=%d bytes, created=%s]", getName(), size, getCreationDate());
    }
}