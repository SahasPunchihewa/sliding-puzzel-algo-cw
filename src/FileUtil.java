import java.awt.*;
import java.io.File;

public class FileUtil {

    public File getFile() {
        Frame frame = new Frame();
        FileDialog fileDialog = new FileDialog(frame, "Choose a file", FileDialog.LOAD);

        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);

        for (Window window : Window.getWindows()) { window.dispose(); }

        return new File(fileDialog.getDirectory() + fileDialog.getFile());
    }
}
