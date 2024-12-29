package software.ulpgc.imageviewer.architecture.model;

public interface Image {
    String name();

    byte[] content();

    Format format();

    Image next();

    Image previous();

    enum Format {
        jpg, Jpeg, Png, Gif
    }

}
