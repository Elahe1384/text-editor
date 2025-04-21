package editor;

import javax.swing.*;
import java.io.*;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textEditor.currentFile = file;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor,
                        "Error opening file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                textEditor.currentFile = file;
                writeToFile(file, textArea.getText(), textEditor);
            }
        } else {
            writeToFile(textEditor.currentFile, textArea.getText(), textEditor);
        }
    }

    private static void writeToFile(File file, String content, TextEditor textEditor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(textEditor,
                    "Error saving file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        if (!textArea.getText().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(textEditor,
                    "Do you want to save changes?",
                    "Save Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                saveFile(textEditor, textArea);
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        textArea.setText("");
        textEditor.currentFile = null;    }
}
