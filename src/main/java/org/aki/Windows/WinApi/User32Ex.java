package org.aki.Windows.WinApi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.win32.StdCallLibrary;

public interface User32Ex extends StdCallLibrary {
    User32Ex INSTANCE = Native.load("User32",User32Ex.class);
    int GetWindowRect(HWND hwnd, RECT rect);

    boolean SetCursorPos(int x,int y);

    void mouse_event(int dwFlags,int dx,int dy,int dwData,int dwExtraInfo);

    void keybd_event(int bVk, int bScan, int dwFlags, int dwExtralnfo);

    /**
     * <p>
     * Opens the clipboard for examination and prSignal other applications from modifying the
     * clipboard content.
     * </p>
     *
     * @param hWnd A handle to the window to be associated with the open clipboard. If this
     *            parameter is NULL, the open clipboard is associated with the current task.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the
     *         return value is zero. To get extended error information, call GetLastError.
     */
    boolean OpenClipboard(HWND hWnd);

    /**
     * <p>
     * Closes the clipboard.
     * </p>
     *
     * @return If the function succeeds, the return value is nonzero. If the function fails, the
     *         return value is zero. To get extended error information, call GetLastError.
     */
    boolean CloseClipboard (HWND hWnd);
    /**
     * <p>
     * Retrieves data from the clipboard in a specified format. The clipboard must have been
     * opened previously.
     * </p>
     *
     * @param format A clipboard format. For a description of the standard clipboard formats,
     *            see Standard Clipboard Formats.
     * @return If the function succeeds, the return value is the handle to a clipboard object in
     *         the specified format. If the function fails, the return value is NULL. To get
     *         extended error information, call GetLastError.
     */
    Pointer GetClipboardData(int format);

    /**
     * <p>
     * Places the given window in the system-maintained clipboard format listener list.
     * </p>
     *
     * @param hWnd A handle to the window to be placed in the clipboard format listener list.
     * @return Returns TRUE if successful, FALSE otherwise. Call GetLastError for additional
     *         details.
     */
    boolean AddClipboardFormatListener(HWND hWnd);

    /**
     * <p>
     * Removes the given window from the system-maintained clipboard format listener list.
     * </p>
     *
     * @param hWnd A handle to the window to remove from the clipboard format listener list.
     * @return Returns TRUE if successful, FALSE otherwise. Call GetLastError for additional
     *         details.
     */
    boolean RemoveClipboardFormatListener(HWND hWnd);

    /**
     * <p>
     * Determines whether the clipboard contains data in the specified format.
     * </p>
     *
     * @param format A standard or registered clipboard format. For a description of the
     *            standard clipboard formats, see Standard Clipboard Formats .
     * @return If the clipboard format is available, the return value is nonzero. If the
     *         clipboard format is not available, the return value is zero. To get extended
     *         error information, call GetLastError.
     */
    boolean IsClipboardFormatAvailable(int format);

    boolean EmptyClipboard ();

    Pointer SetClipboardData (int format, Pointer hMem);


}
