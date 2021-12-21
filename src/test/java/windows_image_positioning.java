import aki.Helper.Operation;

import aki.LaunchOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windows_image_positioning {
    aki.Windows.UIElementRef app;

    @Before
    public void initializeUIElement() throws InterruptedException {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(true);
        app = Operation.initializeAppRefForWin("C:\\Users\\CNHAHUA16\\AppData\\Local\\Microsoft\\Teams\\current\\Teams.exe",launchOption);
    }

    @Test
    public void testCase() {
        String imageFolderPath = System.getProperty("user.dir") + "/src/test/java/Image/";
        app.findElementLocationByImage(imageFolderPath + "1.png").click();
        app.findElementLocationByImage(imageFolderPath + "number2.png").click();
//        app.findElementLocationByImage(imageFolderPath + "X.png").click();
//        app.findElementLocationByImage(imageFolderPath + "number3.png").click();
//        app.findElementLocationByImage(imageFolderPath + "number2.png").click();
//        app.findElementLocationByImage(imageFolderPath + "=.png").click();
//        String res = app.findElementByAutomationId("CalculatorResults").get_Name();
//        assertEquals(res, "Display is 1,024");
        String Desktop =FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        Operation.takeScreenshotForDesktop(Desktop+"/Screenshot_1634617817.png");
    }

    @After
    public void end() throws IOException {
        Operation.killApp();
    }


}
