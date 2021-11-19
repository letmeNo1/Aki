import aki.Helper.Operation;
import aki.Mac.UIElementRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class mac_test {
    UIElementRef app;

    @Before
    public void initializeUIElement(){
        app = Operation.initializeAppRefForMac("com.apple.calculator");
    }

    @Test
    public void testCase() {
        app.findElementByIdentifier("_NS:510").click();
        app.findElementsByText("3",0).click();
        app.findElementsByText("2",0).click();
        app.findElementsByText("×",0).click();
        app.findElementsByText("3",0).click();
        app.findElementsByText("2",0).click();
        app.findElementsByText("=",0).click();
        String res = app.findElementByIdentifier("_NS:16").get_Value();
        assertEquals(res, "1024");
        String Desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        Operation.takeScreenshot(Desktop+ "Screenshot_1634617817.png",0);
    }

    @After
    public void end() throws IOException {
        Operation.killApp();
    }
}