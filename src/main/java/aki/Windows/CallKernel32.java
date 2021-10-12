package aki.Windows;

import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

public class CallKernel32 {
    public static DWORD getProcessesIdByName(String executeName){
        HANDLE snapshot;
        DWORD pid = new DWORD();
        try {
            snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
                    Tlhelp32.TH32CS_SNAPPROCESS,  new DWORD( 0 ) );
            final Tlhelp32.PROCESSENTRY32 entry = new Tlhelp32.PROCESSENTRY32();
            Kernel32.INSTANCE.Process32First( snapshot,  entry );
            do {
                final String szExeFileName = String.valueOf(entry.szExeFile);

                if(szExeFileName.toLowerCase().contains(executeName.toLowerCase())){
                    pid = entry.th32ProcessID;
                    break;
                }

            } while ( Kernel32.INSTANCE.Process32Next( snapshot, entry ) );

        }catch (Exception ignored){

        }
        return pid;
    }

    public static DWORD launchApp(String appLaunchPath) {
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        WinBase.PROCESS_INFORMATION.ByReference processInformation = new WinBase.PROCESS_INFORMATION.ByReference();
        boolean status = Kernel32.INSTANCE.CreateProcess(
                null,
                appLaunchPath,
                null,
                null,
                true,
                null,
                null,
                null,
                startupInfo,
                processInformation);
        if (!status) {
            throw new RuntimeException("launch app failed");
        }
        return getProcessesIdByName(appLaunchPath.split("\\\\")[appLaunchPath.split("\\\\").length-1]);
    }
}
