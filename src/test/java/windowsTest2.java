import aki.Helper.Operation;
import aki.Common.LaunchOption;
import aki.Windows.WinUIElementRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class windowsTest2 {
    WinUIElementRef app;
    public final static String TEST_ACCOUNT = "testuser1.elbackoffice@in.abb.com";
    public final static String USE_ANOTHER_ACCOUNT_TEXT = "Use another account";
    public final static String ACCOUNT_INPUT_FIELD_TEXT = "someone@example.com";
    public final static String NEXT_BUTTON_TEXT = "Next";
    public final static String PASSWORD_INPUT_FIELD_TEXT = "Enter the password for testuser1.elbackoffice@in.abb.com";
    public final static String SIGN_IN_BUTTON = "Sign in";
    public final static String SIGN_IN_WINDOW_NAME = "Sign in to your account";

    @Before
    public void initializeUIElement() {
        LaunchOption launchOption = new LaunchOption();
        launchOption.setIsUWPApp(false);
        launchOption.setAlreadyLaunch(false);
        launchOption.setLaunchTimeoutTimeout(30000);
        app = Operation.initializeAppRefForWin("C:\\Program Files (x86)\\ABB\\ABB Provisioning Tool\\1.5.0.15\\ELConnect.exe",launchOption);
    }

    @Test
    public void testCase() throws InterruptedException {

        WinUIElementRef signWindow;
        app.findElementByAutomationId("LoginCommand").click();
        app.assertElementExistByImage("src/test/Image/pick_an_account.png",0.3f);
        signWindow = app.findWindowByWindowName(SIGN_IN_WINDOW_NAME,3000);
        Thread.sleep(2000);
        signWindow.findElementByText(USE_ANOTHER_ACCOUNT_TEXT).click();
        signWindow.findElementByText(ACCOUNT_INPUT_FIELD_TEXT).click();
        signWindow.findElementByText(ACCOUNT_INPUT_FIELD_TEXT).type(TEST_ACCOUNT);
        signWindow.findElementByText(NEXT_BUTTON_TEXT).click();
        signWindow.findElementByText(PASSWORD_INPUT_FIELD_TEXT).hover();
        signWindow.findElementByText(PASSWORD_INPUT_FIELD_TEXT).type("TEST_PASSWORD");
//        app.findElementByAutomationId("StartProvisioning").click();
//        app.findElementsByAutomationId("GotoConfiguration",3).click();
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
//        app.kill();
//        app.release();
    }


}
