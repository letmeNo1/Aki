package org.aki.Windows.WinApi;

public class WinUser {
    public static final int
            WM_RBUTTONDOWN                                            = 517,
            WM_RBUTTONUP                                              = 516,
            WM_LBUTTONDOWN                                            = 515,
            WM_LBUTTONUP                                              = 514,
            MK_LBUTTON                                                = 1,
            MK_RBUTTON                                                = 2;
    public static final long
            //dwFlags
            MOUSEEVENTF_ABSOLUTE                                      = 0x8000,
            MOUSEEVENTF_LEFTDOWN                                      = 0x0002,
            MOUSEEVENTF_LEFTUP                                        = 0x0004,
            MOUSEEVENTF_MIDDLEDOWN                                    = 0x0020,
            MOUSEEVENTF_MIDDLEUP                                      = 0x0040,
            MOUSEEVENTF_MOVE                                          = 0x0001,
            MOUSEEVENTF_RIGHTDOWN                                     = 0x0008,
            MOUSEEVENTF_RIGHTUP                                       = 0x0010,
            MOUSEEVENTF_WHEEL                                         = 0x0800,
            MOUSEEVENTF_XDOWN                                         = 0x0080,
            MOUSEEVENTF_XUP                                           = 0x0100,
            MOUSEEVENTF_HWHEEL                                        = 0x01000;


}
