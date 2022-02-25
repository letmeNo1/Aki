import aki.Helper.Operation;

import aki.LaunchOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windows_test {
    aki.Windows.UIElementRef app;

    @Before
    public void initializeUIElement() throws InterruptedException {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(false);
        app = Operation.initializeAppRefForWin("C:\\WINDOWS\\System32\\calc.exe",launchOption);
//        app = Operation.initializeAppRefForWin("C:\\Program Files (x86)\\ABB\\ABB Ability Device Registration Tool\\DeviceRegistrationTool.exe",launchOption);

    }

    @Test
    public void testCase() {
        app.release();
        app.findElementByAutomationId("num3Button").click();
        app.findElementByAutomationId("num2Button").click();
        app.findElementByAutomationId("multiplyButton").click();
        app.findElementByAutomationId("num3Button").click();
        app.findElementByAutomationId("num2Button").click();
        app.findElementByAutomationId("equalButton").click();
        String res = app.findElementByAutomationId("CalculatorResults").get_Name();
        assertEquals(res, "Display is 1,024");
        String Desktop =FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        app.takeScreenshot(Desktop+"/Screenshot_1634617817.png");
    }

    @After
    public void end() throws IOException {
        app.kill();
        app.release();
    }


}
