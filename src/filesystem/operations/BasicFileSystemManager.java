package filesystem.operations;

import filesystem.exceptions.FileSystemException;
import filesystem.exceptions.manager.DirectoryNotFoundException;
import filesystem.exceptions.manager.NameAlreadyExistsException;
import filesystem.nodes.Directory;
import filesystem.nodes.File;
import filesystem.nodes.FileSystemNode;

import java.util.*;


/**
 * Basic implementation of a file system manager.
 * The file system is represented as a tree structure where each node is a file or directory.
 * Method Signatures and Complexities Summary:
 *
 * 1. public void addFile(String parentDirName, String fileName, int fileSize)
 *    - Time Complexity: O(1) (Insertion in the HashMap)
 *    - Space Complexity: O(1)
 *
 * 2. public void addDir(String parentDirName, String dirName)
 *    - Time Complexity: O(1)
 *    - Space Complexity: O(1)
 *
 * 3. public String getBiggestFile()
 *    - Time Complexity: O(1) (Peek operation on the max heap)
 *    - Space Complexity: O(1)
 *
 * 4. public long getFileSize(String fileName)
 *    - Time Complexity: O(1) (Lookup in the name map - HashMap)
 *    - Space Complexity: O(1)
 *
 * 5. public void showFileSystem()
 *    - Time Complexity: O(N) (DFS traversal of the file system tree)
 *    - Space Complexity: O(h)
 *
 * 6. public void delete(String name)
 *    - Time Complexity: O(N).
 *    - Space Complexity: O(n + h) (n is the maximum number of nodes for a directory and h is the height of the file system tree)
 */

public class BasicFileSystemManager implements FileSystemManager {

    private final Directory root;
    private final HashMap<String, Directory> directoryNameMap;
    private final HashMap<String, File> fileNameMap;
    private File largestFile;


    public BasicFileSystemManager() throws FileSystemException {
        this.root = new Directory("root");
        this.directoryNameMap = new HashMap<>();
        this.directoryNameMap.put(root.getName(), root);
        this.fileNameMap = new HashMap<>();
        this.largestFile = null;
    }

    /**
     * Adds a file to the file system.
     * Time complexity: O(1), we are creating a new file adding him to his parent and fileName map and updating the largest file.
     * Space complexity: O(1) since we are only adding a file node.
     * @param parentDirName
     * @param fileName
     * @param fileSize
     * @throws FileSystemException
     */
    public void addFile(String parentDirName, String fileName, int fileSize) throws FileSystemException {
        Directory parent = getParentDirectory(parentDirName);
        checkFileExistence(fileName);

        File file = new File(fileName, fileSize);
        // Add file while making sure a double link is established
        file.setParent(parent);
        parent.addChild(file);
        updateLargestFileAfterInsertion(file);
        fileNameMap.put(fileName, file);
    }

    /**
     * Adds a directory to the file system.
     * Time complexity: O(1) on average, as the directory is added to the parent directory's children map and the directoryNameMap.
     * Space complexity: O(1) since we are only adding a directory node.
     * @param parentDirName
     * @param dirName
     * @throws FileSystemException
     */
    public void addDir(String parentDirName, String dirName) throws FileSystemException {
        Directory parent = getParentDirectory(parentDirName);
        checkFileExistence(dirName);

        Directory dir = new Directory(dirName);
        // Add directory while making sure a double link is established
        dir.setParent(parent);
        parent.addChild(dir);
        directoryNameMap.put(dirName, dir);
    }

    /**
     * Returns the name of the biggest file in the file system.
     * Time complexity: O(1) since we are just returning the name of the largest file node.
     * Space complexity: O(1) since we are not using any additional space.
     * @return String
     */
    public String getBiggestFile() throws FileSystemException {
        if (largestFile == null) {
            throw new FileSystemException("No files found in the file system, can't get the biggest file.");
        }
        return largestFile.getName();
    }

    /**
     * Returns the size of a file in the file system.
     * Time complexity: O(1) since we are just looking up the file in the fileNameMap.
     * Space complexity: O(1) since we are not using any additional space.
     * @param fileName
     * @return long
     * @throws FileSystemException
     */
    public long getFileSize(String fileName) throws FileSystemException {
        File node = fileNameMap.get(fileName);
        System.out.println(fileNameMap);
        if (node == null) {
            throw new FileSystemException(String.format("Node not found: %s", fileName));
        }
        return node.getSize();
    }

    /**
     * Displays the file system structure using helper recursive function (DFS).
     * We choose this approach since File Systems are typically shallow and wide.
     * Time complexity: O(N) where N is the number of nodes in the file system. In tree #Edges = #Nodes - 1, so O(N+E) = O(2N-1) = O(N).
     * Space complexity: O(h) where h is the depth of the file system tree.
     */
    public void showFileSystem() {
        System.out.println(formatFileSystem(root, 0));
    }

    /**
     * Deletes a file or directory from the file system using a recursive approach (DFS).
     * We choose this approach since File Systems are typically shallow and wide.
     * Time complexity: O(N) + O(F) = O(N) where N is the number of nodes in the file system and F is the number of files in the directory.
     * Explanation: At the worst case, we have to traverse and remove all nodes in the directory. And we have to update the largest file if it's removed.
     * Space complexity: O(n + h) where n is the maximum number of nodes for a directory and h is the height of the file system tree.
     * Explanation: The space complexity is due to the recursive call stack and the copy of children in the deleteDirectoryContents method.
     * @param name
     * @throws FileSystemException
     */
    public void delete(String name) throws FileSystemException {
        // Check if node exists
        FileSystemNode nodeToDelete = (fileNameMap.get(name) != null) ? fileNameMap.get(name) : directoryNameMap.get(name);
        if (nodeToDelete == null) {
            throw new FileSystemException(String.format("Node not found: %s", name));
        }

        // Cannot delete root
        if (nodeToDelete == root) {
            throw new FileSystemException("Cannot delete root directory");
        }

        // Remove from parent's children
        FileSystemNode parent = nodeToDelete.getParent();
        if (parent instanceof Directory) {
            ((Directory) parent).removeChild(nodeToDelete);
        }

        boolean[] isBiggestFileRemoved = new boolean[1];
        // If it's a directory, recursively delete all contents
        if (nodeToDelete instanceof Directory) {
            deleteDirectoryContents((Directory) nodeToDelete, isBiggestFileRemoved);
            directoryNameMap.remove(name);
        } else {
            fileNameMap.remove(name);
        }

        if (isBiggestFileRemoved[0] || Objects.equals(nodeToDelete, largestFile)) {
            updateLargestFileAfterDeletion();
        }

    }

    private void deleteDirectoryContents(Directory directory, boolean[] isBiggestFileRemoved) {
        for (FileSystemNode child : directory.getChildren()) {
            // Remove from maps
            directoryNameMap.remove(child.getName());
            fileNameMap.remove(child.getName());
            // If child is a directory, recursively delete its contents
            if (child instanceof Directory) {
                deleteDirectoryContents((Directory) child, isBiggestFileRemoved);
            } else if (child instanceof File && child == largestFile) {
                isBiggestFileRemoved[0] = true;
            }
        }
    }

    private void updateLargestFileAfterDeletion() {
        for (File file : fileNameMap.values()) {
            if (largestFile == null || file.getSize() >= largestFile.getSize()) {
                largestFile = file;
            }
        }
    }

    private void updateLargestFileAfterInsertion(File file) {
        if (largestFile == null || file.getSize() >= largestFile.getSize()) {
            largestFile = file;
        }
    }

    private Directory getParentDirectory(String parentDirName) throws DirectoryNotFoundException {
        Directory parent = directoryNameMap.get(parentDirName);
        if (parent == null) {
            throw new DirectoryNotFoundException(String.format("Parent directory not found: %s", parentDirName));
        }
        return parent;
    }

    private void checkFileExistence(String fileName) throws NameAlreadyExistsException {
        if (directoryNameMap.containsKey(fileName) || fileNameMap.containsKey(fileName)) {
            throw new NameAlreadyExistsException(String.format("File already exists: %s", fileName));
        }
    }

    private static String formatFileSystem(FileSystemNode node, int indent) {
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
