
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import aki.Windows.CallOleacc;
import aki.Windows.UIElementRef;

public class test {
    public static WinDef.LPARAM makeLParam(int l, int h) {
        // note the high word bitmask must include L
        return new WinDef.LPARAM((l & 0xffff) | (h & 0xffffL) << 16);
    }

    public static void main(String[] args)  {
        WinDef.HWND app = User32.INSTANCE.FindWindow(null,"设置");
        User32.INSTANCE.SetForegroundWindow(app);
        UIElementRef aa = CallOleacc.getAccessibleObject(app);
        System.out.println(aa.get_Name());
//        WinUser.INPUT input2 = new WinUser.INPUT();
//        input2.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input2.input.setType("ki");
//        input2.input.ki.wScan = new WinDef.WORD(0);
//        input2.input.ki.time = new WinDef.DWORD(0);
//        input2.input.ki.wVk = new WinDef.WORD(VK_CONTROL);
//        input2.input.ki.dwFlags = new WinDef.DWORD(0);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input2.toArray(1), input2.size());
//
//        WinUser.INPUT input = new WinUser.INPUT();
//        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input.input.setType("ki");
//        input.input.ki.wScan = new WinDef.WORD(0);
//        input.input.ki.time = new WinDef.DWORD(0);
//        input.input.ki.wVk = new WinDef.WORD(VK_A);
//        input.input.ki.dwFlags = new WinDef.DWORD(0);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
//
//        WinUser.INPUT input3 = new WinUser.INPUT();
//        input3.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input3.input.setType("ki");
//        input3.input.ki.wScan = new WinDef.WORD(0);
//        input3.input.ki.time = new WinDef.DWORD(0);
//        input3.input.ki.wVk = new WinDef.WORD(VK_CONTROL);
//        input3.input.ki.dwFlags = new WinDef.DWORD(2);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input3.toArray(1), input3.size());
//
//        WinUser.INPUT input4 = new WinUser.INPUT();
//        input4.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input4.input.setType("ki");
//        input4.input.ki.wScan = new WinDef.WORD(0);
//        input4.input.ki.time = new WinDef.DWORD(0);
//        input4.input.ki.wVk = new WinDef.WORD(VK_A);
//        input4.input.ki.dwFlags = new WinDef.DWORD(2);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input4.toArray(1), input4.size());
//
//
//        WinUser.INPUT input5 = new WinUser.INPUT();
//        input5.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input5.input.setType("ki");
//        input5.input.ki.wScan = new WinDef.WORD(0);
//        input5.input.ki.time = new WinDef.DWORD(0);
//        input5.input.ki.wVk = new WinDef.WORD(VK_BACK_SPACE);
//        input5.input.ki.dwFlags = new WinDef.DWORD(0);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input5.toArray(1), input5.size());
//
//        WinUser.INPUT input6 = new WinUser.INPUT();
//        input6.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input6.input.setType("ki");
//        input6.input.ki.wScan = new WinDef.WORD(0);
//        input6.input.ki.time = new WinDef.DWORD(0);
//        input6.input.ki.wVk = new WinDef.WORD(VK_BACK_SPACE);
//        input6.input.ki.dwFlags = new WinDef.DWORD(2);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input6.toArray(1), input6.size());


//        WinUser.INPUT input5 = new WinUser.INPUT();
//        input5.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input5.input.setType("ki");
//        input5.input.ki.wScan = new WinDef.WORD(0);
//        input5.input.ki.time = new WinDef.DWORD(0);
//        input5.input.ki.wVk = new WinDef.WORD(VK_DELETE);
//        input5.input.ki.dwFlags = new WinDef.DWORD(0);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input2.toArray(1), input2.size());
//
//        WinUser.INPUT input6 = new WinUser.INPUT();
//        input6.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
//        input6.input.setType("ki");
//        input6.input.ki.wScan = new WinDef.WORD(0);
//        input6.input.ki.time = new WinDef.DWORD(0);
//        input6.input.ki.wVk = new WinDef.WORD(VK_DELETE);
//        input6.input.ki.dwFlags = new WinDef.DWORD(2);
//
//        User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input2.toArray(1), input2.size());
     }

//        UIElementRef app2 =  CallOleacc.getAccessibleObject(app);
////        System.out.println(app2.get_Location().x);
////        System.out.println(app2.get_Location().y);
////        System.out.println(app2.get_Location().w);
////        System.out.println(app2.get_Location().h);
////        System.out.println("        ");
////
////        System.out.println(app2.findElementByAutomationId("notifyArea_messages").get_Location().x);
////        System.out.println(app2.findElementByAutomationId("notifyArea_messages").get_Location().y);
////        System.out.println(app2.findElementByAutomationId("notifyArea_messages").get_Location().w);
////        System.out.println(app2.findElementByAutomationId("notifyArea_messages").get_Location().h);
//
////        app2.findElementByAutomationId("notifyArea_messages").click();
////        System.out.println(res);
//        UIElementRef app3 = app2.findElementByAutomationId("notifyArea_messages");
//        RECT ZECT = new RECT();
//        System.out.println(ZECT);
//        User32.INSTANCE.SendMessage(app3.getHWNDFromIAccessible(),WM_LBUTTONDOWN, new WPARAM(1), makeLParam(16, 66));
//        User32.INSTANCE.SendMessage(app3.getHWNDFromIAccessible(),WM_LBUTTONUP, new WPARAM(0), makeLParam(16, 66));



}



