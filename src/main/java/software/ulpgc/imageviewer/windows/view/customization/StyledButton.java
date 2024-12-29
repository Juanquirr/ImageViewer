package software.ulpgc.imageviewer.windows.view.customization;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledButton extends JButton {

    public StyledButton(String name) {
        this.setText(name);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setBorderPainted(true);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(100, 30));
        this.setBorder(new LineBorder(Color.WHITE, 1));
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                StyledButton.this.setBackground(Color.WHITE);
                StyledButton.this.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent evt) {
                StyledButton.this.setBackground(Color.BLACK);
                StyledButton.this.setForeground(Color.WHITE);
            }
        });
    }
}
