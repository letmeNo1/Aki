package org.aki;

import com.sun.jna.platform.win32.WinNT.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentAppRefInfo {
    private static final ThreadLocal<Map<String, CurrentAppRefInfo>> instance = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public String bundleIdentifier;
    public HANDLE currentHandle;
    public int pid;
    public int defaultTimeout = 20000;


    public static CurrentAppRefInfo getInstance() {
        String threadName = String.format("%s_%s", "org.aki.CurrentAppRefInfo", Thread.currentThread().getName());
        if (null == instance.get().get(threadName)) {
            synchronized (CurrentAppRefInfo.class) {
                instance.get().put(threadName, new CurrentAppRefInfo(threadName));
            }
        }
        return instance.get().get(threadName);
    }

    public CurrentAppRefInfo(String threadName) {
    }

    public String getBundleIdentifier() {
        return this.bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier;
    }

    public int getDefaultTimeout() {
        return this.defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public HANDLE getCurrentHandle() {
        return this.currentHandle;
    }

    public void setCurrentHandle(HANDLE currentHandle) {
        this.currentHandle = currentHandle;
    }
}
