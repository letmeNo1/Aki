
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.aki.Helper.Operation;
import org.aki.Windows.CallOleacc;
import org.aki.Windows.CallUser32;
import org.aki.Windows.UIElementRef;

import java.io.IOException;


public class windows_test {

    public static void main(String[] args) throws IOException, InterruptedException {
        String appLaunchPath = "C:\\Users\\86158\\AppData\\Local\\RingCentral\\SoftPhoneApp\\Softphone.exe";
        UIElementRef app = Operation.initializeAppRefForWin(appLaunchPath);
        app.findElementsByText("RingCentral Phone, Use your business phone anytime anywhere., Sign In",0).click();
        app.findElementsByText("Input phone number",0).clear();
        app.findElementsByText("Input phone number",0).type("18882355822");
        app.findElementByFullDescription("Input password").type("Test!123");
        app.findElementsByText("Sign In",0).click();
        app.findElementsByText("Cancel",0).click();
        Thread.sleep(2000);
        app.findElementByAutomationId("settingsButton").click();
        Thread.sleep(2000);
        UIElementRef window1 = Operation.initializeAppRefByWindowName("Settings");
        System.out.println(window1.get_Name());
        window1.findElementByAutomationId("elementGeneral").click();
        window1.findElementByAutomationId("elementCalls").click();
        window1.findElementByAutomationId("elementMessaging").click();
        window1.findElementByAutomationId("elementContacts").click();
        window1.findElementByAutomationId("elementJoinNow").click();
        window1.findElementByAutomationId("elementHotkeys").click();
        window1.findElementByAutomationId("elementSupport").click();
        window1.findElementsByText("Logout",0).click();
        window1.findElementsByText("Log out",0).click();
//        System.out.println(app.findElementsByText("Cancel",0).get_Name());
//        System.out.println(app.findElementsByText("Top Bar Menu",0).get_Location().w);
//        System.out.println(app.findElementsByText("Top Bar Menu",0).get_Location().h);
        Operation.killApp();
    }


}
