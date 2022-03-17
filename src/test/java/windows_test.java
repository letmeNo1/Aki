import aki.Helper.Operation;

import aki.Common.LaunchOption;
import aki.Windows.WinUIElementRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windows_test {
    WinUIElementRef app;

    @Before
    public void initializeUIElement() {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(true);
        launchOption.setLaunchTimeoutTimeout(2000);
        app = Operation.initializeAppRefForWin("C:\\Program Files\\OldClassicCalc\\calc1.exe",launchOption);
    }

    @Test
    public void testCase() {
        app.setTimeout(1900);
        app.findElementByAutomationId("num3Button").click();
        System.out.println(app.assertElementExist("num3Button","ByAutomationId"));
        app.findElementByAutomationId("num2Button",100000).click();
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
