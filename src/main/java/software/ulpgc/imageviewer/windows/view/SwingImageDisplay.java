package software.ulpgc.imageviewer.windows.view;

import software.ulpgc.imageviewer.architecture.io.ImageDeserializer;
import software.ulpgc.imageviewer.architecture.view.ImageDisplay;
import software.ulpgc.imageviewer.architecture.view.ViewPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final ImageDeserializer deserializer;
    private final List<PaintOrder> orders;
    private Shift shift;
    private Released released;
    private int initX;


    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    public SwingImageDisplay(ImageDeserializer deserializer) {
        this.deserializer = deserializer;
        this.orders = new ArrayList<>();
        this.shift = Shift.NULL;
        this.released = Released.NULL;
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
    }

    @Override
    public void on(Released released) {
        this.released = released;
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        orders.forEach(o -> paintOrder(g, o));
    }

    @Override
    public void paint(PaintOrder... orders) {
        this.orders.clear();
        Collections.addAll(this.orders, orders);
        repaint();
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initX);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
    }

    private MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initX = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX() - initX);
            }
        };
    }


    private void paintOrder(Graphics g, PaintOrder order) {
        Image image = deserialize(order.content());
        ViewPort viewPort = ViewPort.ofSize(this.getWidth(), this.getHeight())
                .fit(image.getWidth(null), image.getHeight(null));
        g.drawImage(image,viewPort.x() + order.offset(), viewPort.y(),viewPort.width(), viewPort.height(), null);
    }

    private Image deserialize(byte[] content) {
        return (Image) deserializer.deserialize(content);
    }
}
