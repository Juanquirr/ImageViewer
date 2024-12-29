package software.ulpgc.imageviewer.windows.io;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import software.ulpgc.imageviewer.architecture.io.ImageLoader;
import software.ulpgc.imageviewer.architecture.model.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APIImageLoader implements ImageLoader {
    private final List<Image> imagesToDisplay;

    public APIImageLoader() {
        this.imagesToDisplay = new ArrayList<>();
        List<String> imagesFromAPI = new ArrayList<>();

        for (int i = 0; i <= 4; i++) imagesFromAPI.add("https://picsum.photos/1280/1080.jpg");

        for (int i = 0; i < imagesFromAPI.size(); i++)
            try {
                imagesToDisplay.add(createImage("Picture" + i + ".jpg",
                        readImageFromAPI(imagesFromAPI.get(i)), i));
            } catch (IOException e) {
                throw new RuntimeException("Could not get that image from API", e);
            }
    }

    @Override
    public Image load() {
        return imagesToDisplay.getFirst();
    }

    private Image createImage(String name, byte[] content, int index) {
        return new Image() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public byte[] content() {
                return content;
            }

            @Override
            public Format format() {
                return Format.jpg;
            }

            @Override
            public Image next() {
                return imagesToDisplay.get(nextIndex());
            }

            @Override
            public Image previous() {
                return imagesToDisplay.get(previousIndex());
            }

            private int nextIndex() {
                return (index + 1) % imagesToDisplay.size();
            }

            private int previousIndex() {
                return (index - 1 + imagesToDisplay.size()) % imagesToDisplay.size();
            }
        };
    }

    private byte[] readImageFromAPI(String imageURL) throws IOException {
        return Jsoup.connect(imageURL)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute()
                .bodyAsBytes();
    }
}
