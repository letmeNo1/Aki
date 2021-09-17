package org.aki.Mac.Appkit.AppAndEnvironment;
import ca.weblite.objc.NSObject;
import ca.weblite.objc.Proxy;
/**
 * Todo
 * At present, there is no way to use jna to call NSWorkspace class, so Java-Objc-Bridge is temporarily borrowed
 * Need support in the future.
 */
public class NSWorkspace extends NSObject {
    /**
     * Reference to the Obj-C NSWorkspace class.
     */
        private final Proxy _NsWorkspaceObjectReference = getClient().sendProxy("NSWorkspace", "sharedWorkspace");


    /**
     * Launch App by BundleIdentifier
     */
    public void launchAppWithBundleIdentifier(String bundleIdentifier) {
            Object r = _NsWorkspaceObjectReference.send("launchAppWithBundleIdentifier:options:additionalEventParamDescriptor:launchIdentifier:", bundleIdentifier, null, null, null);
        if(r.toString().equals("0")){
            throw new IllegalArgumentException("Launch app Failed,  Please check the BundleIdentifier");
        }
    }

    /**
     * Get the app information at the front end of the page
     */
    public Object frontmostApplication(){
        return _NsWorkspaceObjectReference.send("frontmostApplication");
    }

    public Object runningApplications(){
        return _NsWorkspaceObjectReference.send("runningApplications");
    }
}
