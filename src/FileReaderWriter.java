import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Java Swing application for reading and writing files in both binary and text formats
 */
public class FileReaderWriter extends JFrame {
    private JTextArea textArea;
    private JTextArea hexArea;
    private JRadioButton textModeButton;
    private JRadioButton binaryModeButton;
    private JLabel statusLabel;
    private JButton openButton;
    private JButton saveButton;
    private JButton clearButton;
    private File currentFile;
    
    public FileReaderWriter() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultSettings();
    }
    
    private void initializeComponents() {
        // Text display area
        textArea = new JTextArea(15, 40);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // Hex display area for binary mode
        hexArea = new JTextArea(15, 40);
        hexArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        hexArea.setEditable(false);
        hexArea.setBackground(Color.LIGHT_GRAY);
        
        // Mode selection buttons
        textModeButton = new JRadioButton("Text Mode", true);
        binaryModeButton = new JRadioButton("Binary Mode", false);
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(textModeButton);
        modeGroup.add(binaryModeButton);
        
        // Control buttons
        openButton = new JButton("Open File");
        saveButton = new JButton("Save File");
        clearButton = new JButton("Clear");
        
        // Status label
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel with mode selection and buttons
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(textModeButton);
        topPanel.add(binaryModeButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(openButton);
        topPanel.add(saveButton);
        topPanel.add(clearButton);
        
        // Center panel with tabbed pane for text and hex views
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Text View", new JScrollPane(textArea));
        tabbedPane.addTab("Hex View", new JScrollPane(hexArea));
        
        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContent();
            }
        });
        
        textModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null) {
                    loadFileContent();
                }
            }
        });
        
        binaryModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null) {
                    loadFileContent();
                }
            }
        });
    }
    
    private void setDefaultSettings() {
        setTitle("File Reader/Writer - Binary and Text");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Enable text area only in text mode initially
        textArea.setEditable(true);
    }
    
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (textModeButton.isSelected()) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "java", "xml", "html", "css", "js"));
        }
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            loadFileContent();
        }
    }
    
    private void loadFileContent() {
        if (currentFile == null) return;
        
        try {
            if (textModeButton.isSelected()) {
                loadTextFile();
            } else {
                loadBinaryFile();
            }
            statusLabel.setText("Loaded: " + currentFile.getName());
        } catch (IOException e) {
            showError("Error loading file: " + e.getMessage());
        }
    }
    
    private void loadTextFile() throws IOException {
        String content = new String(Files.readAllBytes(currentFile.toPath()));
        textArea.setText(content);
        textArea.setEditable(true);
        
        // Also update hex view
        updateHexView(Files.readAllBytes(currentFile.toPath()));
    }
    
    private void loadBinaryFile() throws IOException {
        byte[] bytes = Files.readAllBytes(currentFile.toPath());
        
        // Display as hex in text area for editing
        StringBuilder hexText = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i % 16 == 0 && i > 0) {
                hexText.append("\n");
            }
            hexText.append(String.format("%02X ", bytes[i]));
        }
        
        textArea.setText(hexText.toString());
        textArea.setEditable(true);
        
        // Update hex view
        updateHexView(bytes);
    }
    
    private void updateHexView(byte[] bytes) {
        StringBuilder hexDisplay = new StringBuilder();
        StringBuilder asciiDisplay = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
            if (i % 16 == 0) {
                if (i > 0) {
                    hexDisplay.append("  ").append(asciiDisplay.toString()).append("\n");
                    asciiDisplay = new StringBuilder();
                }
                hexDisplay.append(String.format("%08X  ", i));
            }
            
            hexDisplay.append(String.format("%02X ", bytes[i]));
            
            // Add ASCII representation
            char c = (char) (bytes[i] & 0xFF);
            if (c >= 32 && c <= 126) {
                asciiDisplay.append(c);
            } else {
                asciiDisplay.append('.');
            }
            
            if (i % 8 == 7) {
                hexDisplay.append(" ");
            }
        }
        
        // Handle last line
        if (bytes.length % 16 != 0) {
            int padding = 16 - (bytes.length % 16);
            for (int i = 0; i < padding; i++) {
                hexDisplay.append("   ");
                if (i == 7) hexDisplay.append(" ");
            }
            hexDisplay.append("  ").append(asciiDisplay.toString());
        }
        
        hexArea.setText(hexDisplay.toString());
    }
    
    private void saveFile() {
        if (textArea.getText().trim().isEmpty()) {
            showError("No content to save.");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        if (currentFile != null) {
            fileChooser.setSelectedFile(currentFile);
        }
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            
            try {
                if (textModeButton.isSelected()) {
                    saveTextFile(saveFile);
                } else {
                    saveBinaryFile(saveFile);
                }
                currentFile = saveFile;
                statusLabel.setText("Saved: " + saveFile.getName());
            } catch (IOException e) {
                showError("Error saving file: " + e.getMessage());
            }
        }
    }
    
    private void saveTextFile(File file) throws IOException {
        Files.write(file.toPath(), textArea.getText().getBytes());
    }
    
    private void saveBinaryFile(File file) throws IOException {
        String hexText = textArea.getText().replaceAll("\\s+", "");
        
        // Validate hex string
        if (hexText.length() % 2 != 0) {
            throw new IOException("Invalid hex string: odd number of characters");
        }
        
        byte[] bytes = new byte[hexText.length() / 2];
        for (int i = 0; i < hexText.length(); i += 2) {
            try {
                bytes[i / 2] = (byte) Integer.parseInt(hexText.substring(i, i + 2), 16);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid hex string: " + hexText.substring(i, i + 2));
            }
        }
        
        Files.write(file.toPath(), bytes);
    }
    
    private void clearContent() {
        textArea.setText("");
        hexArea.setText("");
        currentFile = null;
        statusLabel.setText("Ready");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("Error: " + message);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FileReaderWriter().setVisible(true);
        });
    }
}