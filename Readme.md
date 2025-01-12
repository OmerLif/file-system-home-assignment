## ControlMonkey Home Assignment - Java Backend Developer

**Welcome!**

This assignment is part of the ControlMonkey Backend Developer recruitment process.

**About ControlMonkey**

[Link to ControlMonkey website here](link to your website)

We encourage you to visit our website to learn more about our product and services.

**Assignment**

**Target:**

Develop a Java program to handle and manage a file system structure.

**File System Entities and Operations:**

* **File:**
    * Attributes:
        * name (string, up to 32 characters)
        * size (long integer, positive)
        * creation date (date type)
* **Directory:**
    * Attributes:
        * name (string, up to 32 characters)
        * creation date (date type)
    * Can contain directories or files.

**Program Functionalities:**

| Function Prototype | Description | Complexity |
|---|---|---|
| `addFile(String parentDirName, String fileName, int fileSize)` | Adds a new file under the specified directory. | Time: O(N), Space: O(N) |
| `addDir(String parentDirName, String dirName)` | Adds a new directory under the specified parent directory. | Time: O(N), Space: O(N) |
| `getFileSize(String fileName)` | Returns the size of the specified file. | Time: O(N), Space: O(1) |
| `getBiggestFile()` | Returns the name of the file with the maximum size. | Time: O(N), Space: O(1) |
| `showFileSystem()` | Displays all files and directories with their hierarchical structure. | Time: O(N), Space: O(N) |
| `delete(String name)` | Deletes the directory or file with the specified name. | Time: O(N), Space: O(1) |

**Additional Notes:**

* All file and directory names must be unique within the file system.
* Directories can contain an unlimited number of files or subdirectories.
* The file system data structure should reside in memory only, without persisting to disk.
* Each method should include a description of its time and space complexity in Big O notation.

**Feel free to reach out if you have any questions!**

**Good luck!**

**@ ControlMonkey Team**
