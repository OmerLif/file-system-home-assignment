package filesystem;

import exceptions.DirectoryNotFoundException;
import exceptions.FileSystemNodeException;
import exceptions.InvalidNameException;
import exceptions.NameAlreadyExistsException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class BasicFileSystemManager implements FileSystemManager {

    private final FileSystemNode root;
    private final HashMap<String, FileSystemNode> nameMap;
    private final PriorityQueue<File> maxHeap;


    public BasicFileSystemManager() throws InvalidNameException {
        this.root = new Directory("root");
        this.nameMap = new HashMap<>();
        this.nameMap.put(root.getName(), root);
        this.maxHeap = new PriorityQueue<>(Comparator.comparingLong(File::getSize).reversed());
    }

    /**
     * Adds a file to the file system.
     * Running time: O(log M), where M is the number of files in the file system. The time complexity is due to the insertion operation in the priority queue.
     * @param parentDirName
     * @param fileName
     * @param fileSize
     * @throws FileSystemNodeException
     */
    public void addFile(String parentDirName, String fileName, int fileSize) throws FileSystemNodeException {
        Directory parent = getParentDirectory(parentDirName);
        checkFileExistence(fileName);

        File file = new File(fileName, fileSize);
        file.setParent(parent);
        parent.addChild(file);
        insertToDataStructures(file);
    }

    /**
     * Adds a directory to the file system.
     * Running time: O(1) on average, as the directory is added to the parent directory's children list and the name map but not to the priority queue.
     * @param parentDirName
     * @param dirName
     * @throws FileSystemNodeException
     */
    public void addDir(String parentDirName, String dirName) throws FileSystemNodeException {
        Directory parent = getParentDirectory(parentDirName);
        checkFileExistence(dirName);

        Directory dir = new Directory(dirName);
        dir.setParent(parent);
        parent.addChild(dir);
        nameMap.put(dirName, dir);
    }

    /**
     * Returns the name of the biggest file in the file system.
     * Running time: O(1) since we are just peeking at the top of the max heap.
     * @return
     */
    public String getBiggestFile() {
        return maxHeap.peek().getName();
    }

    /**
     * Deletes a file or directory from the file system using a recursive approach (DFS).
     * We choose this approach since File Systems are typically shallow and wide.
     * Running time: O(N + M) == O(N) where N is the number of nodes in the file system and M is the number of files in the priority queue.
     * @param name
     * @throws FileSystemNodeException
     */
    public void delete(String name) throws FileSystemNodeException {
        // Check if node exists
        FileSystemNode nodeToDelete = nameMap.get(name);
        if (nodeToDelete == null) {
            throw new FileSystemNodeException(String.format("Node not found: %s", name));
        }

        // Cannot delete root
        if (nodeToDelete == root) {
            throw new FileSystemNodeException("Cannot delete root directory");
        }

        // Remove from parent's children
        Directory parent = nodeToDelete.getParent();
        if (parent != null) {
            parent.removeChild(nodeToDelete);
        }

        // If it's a directory, recursively delete all contents
        if (nodeToDelete instanceof Directory) {
            deleteDirectoryContents((Directory) nodeToDelete);
        } else if (nodeToDelete instanceof File) {
            // If it's a file, remove from maxHeap
            maxHeap.remove(nodeToDelete);
        }

        // Remove from nameMap
        nameMap.remove(name);
    }

    /**
     * Displays the file system structure.
     * Running time: O(N + M) == O(N) where N is the number of nodes in the file system and M is the number of files in the priority queue.
     */
    public void showFileSystem() {
        System.out.println(formatFileSystem(root, 0));
    }

    private void deleteDirectoryContents(Directory directory) {
        // Create a copy of children to avoid concurrent modification
        var children = new ArrayList<>(directory.getChildren());

        for (FileSystemNode child : children) {
            // Remove from nameMap
            nameMap.remove(child.getName());

            // If child is a directory, recursively delete its contents
            if (child instanceof Directory) {
                deleteDirectoryContents((Directory) child);
            } else if (child instanceof File) {
                // If child is a file, remove from maxHeap
                maxHeap.remove(child);
            }
        }
    }



    private Directory getParentDirectory(String parentDirName) throws DirectoryNotFoundException {
        Directory parent = (Directory) nameMap.get(parentDirName);
        if (parent == null) {
            throw new DirectoryNotFoundException(String.format("Parent directory not found: %s", parentDirName));
        }
        return parent;
    }

    private void checkFileExistence(String fileName) throws NameAlreadyExistsException {
        if (nameMap.containsKey(fileName)) {
            throw new NameAlreadyExistsException(String.format("File already exists: %s", fileName));
        }
    }

    private void insertToDataStructures(File file) {
        nameMap.put(file.getName(), file);
        maxHeap.add(file);
    }


    private String formatFileSystem(FileSystemNode node, int indent) {
        StringBuilder sb = new StringBuilder();
        String indentation = " ".repeat(indent);

        sb.append(indentation).append(node.toString()).append("\n");

        // If node is a directory, format its children recursively
        if (node instanceof Directory) {
            for (FileSystemNode child : ((Directory) node).getChildren()) {
                sb.append(formatFileSystem(child, indent + 3));
            }
        }

        return sb.toString();
    }



}
