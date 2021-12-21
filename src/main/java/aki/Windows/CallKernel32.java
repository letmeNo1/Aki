package aki.Windows;

import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CallKernel32 {
    public static int getProcessesIdByName(String executeName){
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
        return pid.intValue();
    }


    public static int getProcessesIdByTasKList(String appLaunchPath) throws IOException {
            String executeName = appLaunchPath.split("\\\\")[appLaunchPath.split("\\\\").length-1].replace(".exe","");
            Process process = Runtime.getRuntime().exec("tasklist");
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            int res = 0;
            String message;
            while((message = br.readLine()) != null) {
                if(message.toLowerCase().contains(executeName.toLowerCase())){
                    res= Integer.parseInt(message.split("\\s+")[1]);
                }
            }
            return res;
        }


    public static int launchApp(String appLaunchPath) {
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

    public static int launchAppForElectron(String appLaunchPath) {
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
