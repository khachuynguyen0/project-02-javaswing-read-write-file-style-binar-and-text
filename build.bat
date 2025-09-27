@echo off
REM Windows batch script to compile and run the Java Swing File Reader/Writer

echo Building Java Swing File Reader/Writer...

REM Compile the Java source code
javac -d . src\FileReaderWriter.java

if %ERRORLEVEL% == 0 (
    echo ✓ Compilation successful!
    echo.
    echo To run the application, use:
    echo   java FileReaderWriter
    echo.
    set /p input="Run the application now? (y/n): "
    if /i "%input%"=="y" (
        echo Starting application...
        java FileReaderWriter
    )
) else (
    echo ✗ Compilation failed!
    pause
    exit /b 1
)