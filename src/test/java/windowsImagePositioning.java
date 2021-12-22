import aki.Electon.UIElementRef;
import aki.Helper.Operation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windowsImagePositioning {
    aki.Electon.UIElementRef app = new UIElementRef();

    @Before
    public void initializeUIElement() throws InterruptedException {

        Operation.initializeAppRefForElectron("C:\\Program Files\\YesPlayMusic\\YesPlayMusic.exe");
    }

    @Test
    public void testCase() {
        String imageFolderPath = System.getProperty("user.dir") + "/src/test/java/Image/";
        app.findElementLocationByImage(imageFolderPath + "001.png").click();
        app.findElementLocationByImage(imageFolderPath + "back.png").click();
        app.findElementLocationByImage(imageFolderPath + "002.png").click();
        app.findElementLocationByImage(imageFolderPath + "back.png").click();
        app.findElementLocationByImage(imageFolderPath + "003.png").click();
        app.findElementLocationByImage(imageFolderPath + "back.png").click();
        String Desktop =FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        Operation.takeScreenshotForDesktop(Desktop+"/Screenshot_1634617817.png");
    }

    @After
    public void end() throws IOException {
        Operation.killApp();
    }


}
