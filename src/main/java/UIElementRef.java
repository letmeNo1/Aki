public class UIElementRef {
    aki.Windows.UIElementRef  winUIElementRef;
    aki.Mac.UIElementRef  macUIElementRef;

    public UIElementRef(aki.Windows.UIElementRef  winUIElementRef) {
        this.winUIElementRef  =winUIElementRef;
    }

    public UIElementRef(aki.Mac.UIElementRef macUIElementRef) {
        this.macUIElementRef= macUIElementRef;
    }
}
