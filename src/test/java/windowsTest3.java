import aki.Common.LaunchOption;
import aki.Helper.Operation;
import aki.Windows.CallOleacc;
import aki.Windows.CallUser32;
import aki.Windows.WinUIElementRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class windowsTest3 {
    WinUIElementRef app;
    WinUIElementRef window;

    @Before
    public void initializeUIElement() {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(false);
        launchOption.setAlreadyLaunch(false);
        launchOption.setLaunchTimeoutTimeout(30000);
        app = Operation.initializeAppRefForWin("C:\\ABB\\MConfigSetup\\MConfig.exe",launchOption);
    }

    @Test
    public void testCase() throws InterruptedException {
        Thread.sleep(7000);
        window = app.findWindowByWindowName("MConfig",3000);
        window.findElementsByText("MDC4-M",0).click();
        window.findElementByText("PAIS").click();
        window.findElementByText("PGIS").click();
        window.findElementByText("RMU").click();
    }

    @After
    public void end() {
        window.kill();
        window.release();
    }


}
