# Java Swing File Reader/Writer - Binary and Text

A Java Swing application that allows users to read and write files in both binary and text formats. This project provides a graphical interface for viewing and editing files with support for hexadecimal display and editing.

## Features

- **Dual Mode Operation**: Switch between text and binary modes
- **Text Mode**: Read, edit, and save plain text files
- **Binary Mode**: View files as hexadecimal values and edit in hex format
- **Hex Viewer**: Professional hex editor with ASCII representation
- **File Operations**: Open, save, and clear file content
- **User-Friendly Interface**: Tabbed layout with status feedback
- **Multiple File Format Support**: Works with any file type

## Requirements

- Java 8 or higher
- Java Swing (included in standard JDK)

## Compilation and Running

### Quick Start
```bash
# Make the build script executable and run it
chmod +x build.sh
./build.sh
```

### Manual Compilation
```bash
# Compile the source code
javac -d . src/FileReaderWriter.java

# Run the application
java FileReaderWriter
```

## Usage

1. **Launch the application** - The GUI will open with text mode selected by default
2. **Choose mode** - Select either "Text Mode" or "Binary Mode" using the radio buttons
3. **Open a file** - Click "Open File" to browse and select a file
4. **View/Edit content**:
   - **Text Mode**: Content appears as readable text in the Text View tab
   - **Binary Mode**: Content appears as hex values (editable) in Text View, formatted hex in Hex View tab
5. **Save changes** - Click "Save File" to save your modifications
6. **Clear content** - Use "Clear" button to reset the editor

## Interface Components

- **Mode Selection**: Radio buttons to switch between text and binary modes
- **Control Buttons**: Open File, Save File, Clear
- **Text View Tab**: Main editing area (text or hex input)
- **Hex View Tab**: Professional hex dump display with ASCII representation
- **Status Bar**: Shows current operation status and feedback

## File Handling

### Text Mode
- Reads files as UTF-8 text
- Allows direct text editing
- Saves content as plain text

### Binary Mode  
- Reads files as raw bytes
- Displays content as hexadecimal values (e.g., "41 42 43" for "ABC")
- Allows hex editing (input format: space-separated hex values)
- Validates hex input before saving

## Sample Files

The project includes sample files for testing:
- `sample.txt` - A text file with application description
- `sample.bin` - A binary file for hex viewing demonstration

## Technical Details

- Built with Java Swing for cross-platform GUI compatibility
- Uses `java.nio.file` for efficient file I/O operations
- Implements proper error handling and user feedback
- Tabbed interface for better user experience
- Monospace font for proper hex alignment

## Architecture

The application is structured as a single main class `FileReaderWriter` with the following components:
- GUI initialization and layout management
- File I/O operations for both text and binary modes
- Hex display formatting with ASCII representation
- Event handling for user interactions
- Error handling and status reporting
