package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.model.Image;
import software.ulpgc.imageviewer.architecture.view.ImageDisplay;

public class ImagePresenter {
    private final ImageDisplay display;
    private Image image;

    public ImagePresenter(ImageDisplay display) {
        this.display = display;
        this.display.on(shift());
        this.display.on(released());
    }

    private ImageDisplay.Released released() {
        return this::changeCurrentImageOn;
    }

    private void changeCurrentImageOn(int offset) {
        if (isDisplayingCurrentImageWith(offset))
            image = offset > 0 ? image.previous() : image.next();
        display.paint(paintOrderForCurrentImageWith(0));
    }

    private static boolean isDisplayingPreviousImage(int offset) {
        return offset > 0;
    }

    private boolean isDisplayingCurrentImageWith(int offset) {
        return Math.abs(offset) > display.width() / 2;
    }

    private ImageDisplay.Shift shift() {
        return offset -> display.paint(
            paintOrderForCurrentImageWith(offset),
            isDisplayingPreviousImage(offset) ?
                    paintOrderForPreviousImageWith(offset - display.width()) :
                    paintOrderForNextImageWith(display.width() + offset)
            );
    }

    private ImageDisplay.PaintOrder paintOrderForNextImageWith(int offset) {
        return new ImageDisplay.PaintOrder(image.next().content(), offset);
    }

    private ImageDisplay.PaintOrder paintOrderForPreviousImageWith(int offset) {
        return new ImageDisplay.PaintOrder(image.previous().content(), offset);
    }

    private ImageDisplay.PaintOrder paintOrderForCurrentImageWith(int offset) {
        return new ImageDisplay.PaintOrder(image.content(), offset);
    }

    public void show(Image image) {
        this.image = image;
        display.paint(paintOrderForCurrentImageWith(0));
    }

    public Image currentImage() {
        return image;
    }
}
