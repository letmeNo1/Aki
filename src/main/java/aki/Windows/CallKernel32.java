package aki.Windows;

import aki.Common.LaunchOption;
import aki.Common.TraceLog;
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
    static TraceLog log = new TraceLog();
    public static int getProcessesIdByName(String executeName,LaunchOption launchOption){
        HANDLE snapshot;
        DWORD pid = new DWORD();
        try {
            snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
                    Tlhelp32.TH32CS_SNAPPROCESS,  new DWORD( 0 ) );
            final Tlhelp32.PROCESSENTRY32 entry = new Tlhelp32.PROCESSENTRY32();
            Kernel32.INSTANCE.Process32First( snapshot,  entry );
            log.logInfo("looking for pid...");
            do {
                final String szExeFileName = String.valueOf(entry.szExeFile);
                if(launchOption.getIsUWPApp()){
                    if(szExeFileName.contains("ApplicationFrameHost.exe")){
                        pid = entry.th32ProcessID;
                        log.logInfo("Found it! pid of current app is " + pid);
                        return pid.intValue();
                    }
                }else {
                    if(szExeFileName.toLowerCase().contains(executeName.toLowerCase())){
                        pid = entry.th32ProcessID;
                        log.logInfo("Found it! pid of current app is " + pid);
                        return pid.intValue();
                    }
                }
            } while ( Kernel32.INSTANCE.Process32Next( snapshot, entry ) );
            log.logErr("pid not found!");


        }catch (Exception ignored){

        }
        return 0;
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


    public static int launchApp(String appLaunchPath, LaunchOption launchOption) {
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        WinBase.PROCESS_INFORMATION.ByReference processInformation = new WinBase.PROCESS_INFORMATION.ByReference();
        if(!launchOption.getAlreadyLaunch()){
            log.logInfo(String.format("Launch app by %s...",appLaunchPath));
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
                log.logErr("launch app failed!");
                throw new RuntimeException("launch app failed");
            }
        }else{
            log.logInfo("App already launched.");
        }

        return getProcessesIdByName(appLaunchPath.split("\\\\")[appLaunchPath.split("\\\\").length-1],launchOption);
    }

    public static void launchAppForElectron(String appLaunchPath) {
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        WinBase.PROCESS_INFORMATION.ByReference processInformation = new WinBase.PROCESS_INFORMATION.ByReference();
        log.logInfo("Launch app...");
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
            log.logErr("launch app failed!");
            throw new RuntimeException("launch app failed");
        }
    }
}
