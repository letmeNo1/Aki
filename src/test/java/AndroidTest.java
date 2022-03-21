import aki.Android.AndroidUIElementRef;
import aki.Common.LaunchOption;
import aki.Electon.UIElementRef;
import aki.Helper.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;


public class AndroidTest {
    AndroidUIElementRef app = new AndroidUIElementRef("emulator-5554");

//    @Before
//    public void initializeUIElement() throws InterruptedException {
//        LaunchOption launchOption = new LaunchOption();
//        Operation.initializeAppRefForElectron("C:\\Program Files\\YesPlayMusic\\YesPlayMusic.exe",launchOption);
//    }

    @Test
    public void testCase() throws InterruptedException, IOException {
        Thread.sleep(3000);
        String imageFolderPath = System.getProperty("user.dir") + "/src/test/java/Image/";
        app.findElementByImage(imageFolderPath + "message.png").longClick(3000);
        app.findElementByImage(imageFolderPath + "newMessage.png").click();

//        app.findElementByImage(imageFolderPath + "Home.png",0.4f).click();
//        app.findElementByImage(imageFolderPath + "Play.png",0.4f).click();
//        app.findElementByImage(imageFolderPath + "home.png",0.4f).click();
//        app.findElementByImage(imageFolderPath + "003.png",0.4f).click();
//        app.findElementByImage(imageFolderPath + "breakHeart-pop.png").click();
//        app.findElementByImage(imageFolderPath + "Play.png").click();
//        app.findElementByImage(imageFolderPath + "home.png").click();
//        app.findElementByImage(imageFolderPath + "Explore.png").click();
//        app.findElementByImage(imageFolderPath + "Library.png").click();
        String Desktop =FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        Operation.takeScreenshotForDesktop(Desktop+"/Screenshot_1634617817.png");
    }

    @After
    public void end() throws IOException {
//        Operation.killApp();
    }


}
