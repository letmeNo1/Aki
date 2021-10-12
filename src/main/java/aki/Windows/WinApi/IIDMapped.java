package aki.Windows.WinApi;

import com.sun.jna.platform.win32.Guid;

public class IIDMapped {
    public static Guid.IID IID_IRawElementProviderSimple = new Guid.IID("D6DD68D1-86FD-4332-8666-9ABEDEA2D24C");
    public static Guid.REFIID REFIID_IRawElementProviderSimple = new Guid.REFIID(IID_IRawElementProviderSimple);

    public static Guid.IID IID_IServiceProvider = new Guid.IID("6D5140C1-7436-11CE-8034-00AA006009FA");
    public static Guid.REFIID REFIID_IServiceProvider = new Guid.REFIID(IID_IServiceProvider);

    public static Guid.IID IID_IAccessibleEx = new Guid.IID("F8b80AdA-2C44-48D0-89BE-5FF23C9CD875");
    public static Guid.REFIID REFIID_IAccessibleEx= new Guid.REFIID(IID_IAccessibleEx);

    public static Guid.IID IID_IAccessible = new Guid.IID("618736E0-3C3D-11CF-810C-00AA00389B71");
    public static Guid.REFIID REFIID_IAccessible = new Guid.REFIID(IID_IAccessible);

}
