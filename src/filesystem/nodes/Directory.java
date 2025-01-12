package filesystem.nodes;

import filesystem.exceptions.nodes.InvalidNameException;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents a directory in the file system.
 * Directories can contain other files and directories.
 */
public class Directory extends FileSystemNode {
    private final HashMap<String, FileSystemNode> children = new HashMap<>();

    /**
     * Creates a new directory with the given name.
     * @param name The name of the directory
     * @throws InvalidNameException if the name is invalid
     */
    public Directory(String name) throws InvalidNameException {
        super(name);
    }

    public void addChild(FileSystemNode child) {
        children.put(child.getName(), child);
        child.setParent(this);
    }

    public void removeChild(FileSystemNode child) {
        children.remove(child.getName());
        child.setParent(null);
    }

    public Collection<FileSystemNode> getChildren() {
        return children.values();
    }

    @Override
    public String toString() {
        return String.format("%s [created=%s] [files=%s]", getName(), getCreationDate(), children.size());
    }

}