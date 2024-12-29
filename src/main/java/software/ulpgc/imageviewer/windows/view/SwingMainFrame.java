package software.ulpgc.imageviewer.windows.view;

import software.ulpgc.imageviewer.windows.io.SwingImageDeserializer;
import software.ulpgc.imageviewer.architecture.control.Command;
import software.ulpgc.imageviewer.architecture.view.ImageDisplay;
import software.ulpgc.imageviewer.windows.view.customization.StyledButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SwingMainFrame extends JFrame {
    private final SwingImageDisplay imageDisplay;
    private final Map<String, Command> commands;

    private SwingMainFrame() throws HeadlessException {
        this.commands = new HashMap<>();
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.BLACK);
        this.add(imageDisplay = createImageDisplay(), BorderLayout.CENTER);
        this.add(toolbar(), BorderLayout.SOUTH);
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

        StyledButton prevButton = new StyledButton("Previous");
        prevButton.addActionListener(_ -> commands.get("Previous").execute());

        StyledButton nextButton = new StyledButton("Next");
        nextButton.addActionListener(_ -> commands.get("Next").execute());

        panel.add(prevButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(nextButton);
        return panel;
    }

    public ImageDisplay imageDisplay() {
        return imageDisplay;
    }

    private SwingImageDisplay createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay(new SwingImageDeserializer());
        display.setBackground(Color.BLACK);
        display.setOpaque(true);
        return display;
    }

    public static SwingMainFrame create() {
        return new SwingMainFrame();
    }

    public SwingMainFrame add(String name, Command command) {
        commands.put(name, command);
        return this;
    }
}