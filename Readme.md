# File System Manager - Java Application

This project is a simple in-memory file system manager written in Java. It handles operations like adding files, creating directories, retrieving file sizes, displaying the file system structure, and deleting files or directories.

## Features
- Add files and directories
- Get the size of a file
- Find the largest file
- Display the file system hierarchy
- Delete files or directories

---

## Project Structure
```
src
├── filesystem
│   ├── exceptions
│   │   ├── FileSystemException.java
│   │   ├── manager
│   │   │   ├── DirectoryNotFoundException.java
│   │   │   └── NameAlreadyExistsException.java
│   │   └── nodes
│   │       ├── InvalidFileSizeException.java
│   │       └── InvalidNameException.java
│   ├── nodes
│   │   ├── Directory.java
│   │   ├── File.java
│   │   └── FileSystemNode.java
│   └── operations
│       ├── BasicFileSystemManager.java
│       └── FileSystemManager.java
└── test
    └── filesystem
        └── BasicFileSystemManagerTest.java
```

---

## Core Functionalities
### 1. `addFile(String parentDirName, String fileName, int fileSize)`
Adds a file to the specified directory.

### 2. `addDir(String parentDirName, String dirName)`
Creates a new directory under the specified parent directory.

### 3. `getFileSize(String fileName)`
Returns the size of the specified file.

### 4. `getBiggestFile()`
Returns the name of the file with the largest size.

### 5. `showFileSystem()`
Displays the hierarchical structure of the file system.

### 6. `delete(String name)`
Deletes a specified file or directory.

---

## Design Considerations
- The file system is implemented as a tree structure, with directories containing files or subdirectories.
- A `HashMap` is used for fast lookups.
- A `PriorityQueue` is used to efficiently track the largest file.
- A `parent` node exists for each file or directory to support the deletion operation. In fact this created a double link-list kind of structure. This requires us to make sure we link/unlink between parent and child every time we add/delete a file or directory.
- A `DFS` based traversal is used since file systems are usually shallow and wide which can make BFS memory inefficient.
- A `FileSystemException` class is used as base custom exception class to handle all exceptions.

---

## Notes
- All file and directory names must be unique within the file system.
- The root directory cannot be deleted.
- The application handles exceptions for invalid inputs and duplicate or missing entries.

