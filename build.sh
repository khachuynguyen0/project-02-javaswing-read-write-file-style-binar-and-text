#!/bin/bash

# Java Swing File Reader/Writer - Build and Run Script
# This script compiles and runs the application

echo "Building Java Swing File Reader/Writer..."

# Compile the Java source code
javac -d . src/FileReaderWriter.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    echo "To run the application, use:"
    echo "  java FileReaderWriter"
    echo ""
    echo "Or run it now? (y/n)"
    read -n 1 -r
    echo    # Move to a new line
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo "Starting application..."
        java FileReaderWriter
    fi
else
    echo "✗ Compilation failed!"
    exit 1
fi