package org.aki.Mac.Appkit.AppAndEnvironment;

import ca.weblite.objc.NSObject;
import ca.weblite.objc.Proxy;

public class NSPasteboard extends NSObject {
    /**
     * Create a pasteboard
     */
    private final Proxy pasteboard = getClient().sendProxy("NSPasteboard", "generalPasteboard");


    /**
     * Clear the contents of the pasteboard
     */
    public void clearContents(){
        pasteboard.send("clearContents");
    }

    /**
     * Write data in the pasteboard
     */
    public void writeObjects(String text){
        Proxy array = getClient().sendProxy("NSArray", "array");
        array = array.sendProxy("arrayByAddingObject:",text);
        clearContents();
        boolean wasSuccessful= pasteboard.sendBoolean("writeObjects:", array);
        assert wasSuccessful;
    }

}