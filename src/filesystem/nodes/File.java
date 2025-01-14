package filesystem.nodes;

import filesystem.exceptions.FileSystemException;
import filesystem.exceptions.nodes.InvalidFileSizeException;

/**
 * Represents a file in the file system.
 */
public class File extends FileSystemNode implements Comparable<File> {
    private final long size;

    /**
     * Creates a new file with the given name and size.
     * @param name The name of the file
     * @param size The size of the file in bytes
     * @throws FileSystemException if the size is negative
     */
    public File(String name, long size) throws FileSystemException {
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
    private static void validateSize(long size) throws InvalidFileSizeException {
        if (size <= 0) {
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

    @Override
    public int compareTo(File o) {
        return Long.compare(size, o.size);
    }
}