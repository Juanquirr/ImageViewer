package software.ulpgc.imageviewer.windows;

import software.ulpgc.imageviewer.windows.io.APIImageLoader;
import software.ulpgc.imageviewer.windows.view.SwingMainFrame;
import software.ulpgc.imageviewer.architecture.control.ImagePresenter;
import software.ulpgc.imageviewer.architecture.control.NextCommand;
import software.ulpgc.imageviewer.architecture.control.PreviousCommand;
import software.ulpgc.imageviewer.architecture.model.Image;

public class Main {
    public static void main(String[] args) {
        SwingMainFrame swingMainFrame = SwingMainFrame.create();
        ImagePresenter imagePresenter = new ImagePresenter(swingMainFrame.imageDisplay());
        swingMainFrame
                .add("Next", new NextCommand(imagePresenter))
                .add("Previous", new PreviousCommand(imagePresenter));
        imagePresenter.show(firstImage());
        swingMainFrame.setVisible(true);
    }
    private static Image firstImage() {
        return new APIImageLoader().load();
    }

}
