import aki.Helper.Operation;
import aki.LaunchOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windowsTest2 {
    aki.Windows.UIElementRef app;

    @Before
    public void initializeUIElement() {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(false);
        launchOption.setAlreadyLaunch(true);
        launchOption.setLaunchTimeoutTimeout(30000);
        app = Operation.initializeAppRefForWin("C:\\Program Files (x86)\\ABB\\ABB Provisioning Tool\\1.3.0.56\\ELConnect.exe",launchOption);
    }

    @Test
    public void testCase() {
        app.setTimeout(1900);
        app.findElementByAutomationId("num3Button").click();
//        app.findElementByAutomationId("num2Button",100000).click();
//        app.findElementByAutomationId("multiplyButton").click();
//        app.findElementByAutomationId("num3Button").click();
//        app.findElementByAutomationId("num2Button").click();
//        app.findElementByAutomationId("equalButton").click();
//        String res = app.findElementByAutomationId("CalculatorResults").get_Name();
//        assertEquals(res, "Display is 1,024");
//        String Desktop =FileSystemView.getFileSystemView().getHomeDirectory().getPath();
//        app.takeScreenshot(Desktop+"/Screenshot_1634617817.png");
    }

    @After
    public void end() throws IOException {
        app.kill();
        app.release();
    }


}
