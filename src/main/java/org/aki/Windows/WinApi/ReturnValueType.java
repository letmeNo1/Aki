package org.aki.Windows.WinApi;

public enum ReturnValueType {
    E_INVALIDARG("An invalid argument was specified in the method call",-2147024809),
    E_NO_INTERFACE("No such interface supported",-2147467262);
    private final String description;
    private final int errorCode;


    ReturnValueType(String description, int errorCode) {
        this.description = description;
        this.errorCode = errorCode;
    }

    public String getDescription(){
        return description;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public static String getDescription(int errorCode){
        for(ReturnValueType e: ReturnValueType.values()){
            if(e.errorCode == errorCode){
                return e.getDescription();
            }
        }
        return null;
    }
}
