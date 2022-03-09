package aki.Common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentAppRefInfo {
    private static final ThreadLocal<Map<String, CurrentAppRefInfo>> instance = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public String bundleIdentifier;

    public int pid;
    public long peerIdOfHWND;


    public static CurrentAppRefInfo getInstance() {
        String threadName = String.format("%s_%s", "aki.Common.CurrentAppRefInfo", Thread.currentThread().getName());
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


    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPeerIdOfHWND(long peerIdOfHWND) {
        this.peerIdOfHWND = peerIdOfHWND;
    }

    public long getPeerIdOfHWND() {
        return this.peerIdOfHWND;
    }
}