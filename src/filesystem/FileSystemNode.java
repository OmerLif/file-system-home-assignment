package filesystem;

import exceptions.InvalidNameException;
import java.time.LocalDateTime;

/**
 * Abstract base class for all file system entities.
 * Provides common attributes and behavior for files and directories.
 */
public abstract class FileSystemNode {
    private static final int MAX_NAME_LENGTH = 32;
    private final String name;
    private final LocalDateTime creationDate;
    private Directory parent;

    /**
     * Creates a new FileSystemNode with the specified name.
     *
     * @param name The name of the file system node
     * @throws InvalidNameException if the name is null or exceeds 32 characters
     */
    protected FileSystemNode(String name) throws InvalidNameException {
        validateName(name);
        this.name = name;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Validates the name according to file system rules.
     *
     * @param name The name to validate
     * @throws InvalidNameException if the name is invalid
     */
    private void validateName(String name) throws InvalidNameException {
        if (name == null) {
            throw new InvalidNameException("Name cannot be null");
        }
        if (name.isEmpty()) {
            throw new InvalidNameException("Name cannot be empty");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new InvalidNameException(
                    String.format("Name cannot be longer than %d characters: %s",
                            MAX_NAME_LENGTH, name));
        }
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Directory getParent() {
        return parent;
    }

    void setParent(Directory parent) {
        this.parent = parent;
    }
}