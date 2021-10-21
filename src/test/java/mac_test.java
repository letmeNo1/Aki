import aki.Helper.Operation;
import aki.Mac.UIElementRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class mac_test {
    UIElementRef app;

    @Before
    public void initializeUIElement(){
        app = Operation.initializeAppRefForMac("com.apple.calculator");
    }

    @Test
    public void testCase() throws InterruptedException {
        app.findElementByIdentifier("_NS:510").click();
        app.findElementsByText("3",0).click();
        app.findElementsByText("2",0).click();
        app.findElementsByText("×",0).click();
        app.findElementsByText("3",0).click();
        app.findElementsByText("2",0).click();
        app.findElementsByText("=",0).click();
        String res = app.findElementByIdentifier("_NS:16").get_Value();
        assertEquals(res, "1024");
        Operation.takeScreenshot("/Users/rcadmin/Desktop/Screenshot_1634617817.png",0);
//        app.findElementsByText("Input phone number",0).type("18882355822");
//        app.findElementsByRole("AXTextField",2).type("Test!123");
//        app.findElementsByText("Sign In",0).click();
//        app.findElementsByText("Cancel",0).click();
//        Thread.sleep(1000);
//        app.findElementsByText("View settings, new window will be opened, (5 of 5)",0).click();
//        app.findElementsByText("Notifications and sounds Tab, non-selected (2 of 9)",0).click();
//        app.findElementsByText("Calls Tab, non-selected (3 of 9)",0).click();
//        app.findElementsByText("Messaging Tab, non-selected (4 of 9)",0).click();
//        app.findElementsByText("Contacts Tab, non-selected (5 of 9)",0).click();
//        app.findElementsByText("Join Now Tab, non-selected (6 of 9)",0).click();
//        app.findElementsByText("Hotkeys Tab, non-selected (7 of 9)",0).click();
//        app.findElementsByText("Support Tab, non-selected (8 of 9)",0).click();
//        app.findElementsByText("Help Tab, non-selected (9 of 9)",0).click();
//        Operation.takeScreenshot("/Users/hank.huang/Desktop/trst.png");
//        app.findElementsByText("Logout",0).click();
//        app.findElementsByText("Log out",0).click();
    }

    @After
    public void end() throws IOException {
//        Operation.killApp();
    }
}