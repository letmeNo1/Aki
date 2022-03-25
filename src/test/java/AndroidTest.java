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

    @Test
    public void testCase() throws InterruptedException, IOException {
        Thread.sleep(3000);
        String imageFolderPath = System.getProperty("user.dir") + "/src/test/java/Image/";
        app.findElementByImage(imageFolderPath + "message.png").longClick(2000);
        app.findElementByImage(imageFolderPath + "newMessage.png").click();
    }

    @After
    public void end() throws IOException {
//        Operation.killApp();
    }


}
