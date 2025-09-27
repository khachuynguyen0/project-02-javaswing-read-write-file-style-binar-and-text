# Makefile for Java Swing File Reader/Writer
# Usage: make [compile|run|clean|test]

# Java source directory
SRC_DIR = src
# Java main class
MAIN_CLASS = FileReaderWriter

# Default target
all: compile

# Compile the Java source code
compile:
	@echo "Compiling Java source code..."
	javac -d . $(SRC_DIR)/$(MAIN_CLASS).java
	@echo "✓ Compilation successful!"

# Run the application
run: compile
	@echo "Starting $(MAIN_CLASS)..."
	java $(MAIN_CLASS)

# Clean compiled files
clean:
	@echo "Cleaning compiled files..."
	find . -name "*.class" -type f -delete
	@echo "✓ Clean complete!"

# Test compilation only
test: compile
	@echo "✓ Build test passed!"

# Show help
help:
	@echo "Java Swing File Reader/Writer - Makefile"
	@echo "Available targets:"
	@echo "  compile  - Compile Java source code"
	@echo "  run      - Compile and run the application"
	@echo "  clean    - Remove compiled class files"
	@echo "  test     - Test compilation"
	@echo "  help     - Show this help"

.PHONY: all compile run clean test help