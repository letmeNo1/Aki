import org.aki.Mac.UIElementRef;
import org.aki.Helper.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class mac_test {
    UIElementRef app;

    @Before
    public void initializeUIElement(){
        app = Operation.initializeAppRefForMac("com.apple.calculator");
    }

    @Test
    public void testCase() throws InterruptedException {
        app.findElementsByText("RingCentral Phone, Use your business phone anytime anywhere., Sign In",0).click();
        app.findElementsByText("Input phone number",0).clearInput();
        app.findElementsByText("Input phone number",0).type("18882355822");
        app.findElementsByRole("AXTextField",2).type("Test!123");
        app.findElementsByText("Sign In",0).click();
        app.findElementsByText("Cancel",0).click();
        Thread.sleep(1000);
        app.findElementsByText("View settings, new window will be opened, (5 of 5)",0).click();
        app.findElementsByText("Notifications and sounds Tab, non-selected (2 of 9)",0).click();
        app.findElementsByText("Calls Tab, non-selected (3 of 9)",0).click();
        app.findElementsByText("Messaging Tab, non-selected (4 of 9)",0).click();
        app.findElementsByText("Contacts Tab, non-selected (5 of 9)",0).click();
        app.findElementsByText("Join Now Tab, non-selected (6 of 9)",0).click();
        app.findElementsByText("Hotkeys Tab, non-selected (7 of 9)",0).click();
        app.findElementsByText("Support Tab, non-selected (8 of 9)",0).click();
        app.findElementsByText("Help Tab, non-selected (9 of 9)",0).click();
        Operation.takeScreenshot("/Users/hank.huang/Desktop/trst.png");
        app.findElementsByText("Logout",0).click();
        app.findElementsByText("Log out",0).click();
    }

    @After
    public void end(){
        app.release();
    }
}