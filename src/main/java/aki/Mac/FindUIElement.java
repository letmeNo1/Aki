package aki.Mac;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public interface FindUIElement {
    static MacUIElementRef findElementByIdentifier(MacUIElementRef rootElement, String identifier){
        MacUIElementRef resultElementRef = null;
        Stack<MacUIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            MacUIElementRef ele = allNode.pop();
            if(ele.get_AXIdentifier().equals(identifier)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (MacUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<MacUIElementRef, String, MacUIElementRef> findElementByIdentifier = FindUIElement::findElementByIdentifier;

    static List<MacUIElementRef> findElementsByRole(MacUIElementRef rootElement, String role){
        List<MacUIElementRef> resElementRefList = new ArrayList<>();
        Stack<MacUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            MacUIElementRef ele = allNode.pop();
            if(ele.get_Role().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (MacUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<MacUIElementRef, String, List<MacUIElementRef>> findElementsByRole = FindUIElement::findElementsByRole;

    static List<MacUIElementRef> findElementsBySubRole(MacUIElementRef rootElement, String role){
        List<MacUIElementRef> resElementRefList = new ArrayList<>();
        Stack<MacUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            MacUIElementRef ele = allNode.pop();
            if(ele.get_SubRole().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (MacUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<MacUIElementRef, String, List<MacUIElementRef>> findElementsBySubRole = FindUIElement::findElementsBySubRole;

    static List<MacUIElementRef> findElementsByText(MacUIElementRef rootElement, String text){
       List<MacUIElementRef> resElementRefList = new ArrayList<>();
       Stack<MacUIElementRef> allNode= new Stack<>() ;
       allNode.push(rootElement);
       while (!allNode.isEmpty()){
           MacUIElementRef ele = allNode.pop();
           if(ele.get_Value().equals(text)||ele.get_Title().equals(text)||ele.get_Help().equals(text)){
               resElementRefList.add(ele);
           }
           if (!ele.get_ChildrenElements().isEmpty()) {
               for (MacUIElementRef childEle : ele.get_ChildrenElements())
                   allNode.push(childEle);
           }
       }
       Collections.reverse(resElementRefList);
       return resElementRefList;
    }

    BiFunction<MacUIElementRef, String, List<MacUIElementRef>> findElementsByText = FindUIElement::findElementsByText;


    static List<MacUIElementRef> findElementsByPartialText(MacUIElementRef rootElement, String text){
        List<MacUIElementRef> childElementRefList = new ArrayList<>();
        Stack<MacUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            MacUIElementRef ele = allNode.pop();
            if(ele.get_Value().contains(text)||ele.get_Title().contains(text)||ele.get_Help().contains(text)){
                childElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (MacUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRefList;
    }

    BiFunction<MacUIElementRef, String, List<MacUIElementRef>> findElementsByPartialText = FindUIElement::findElementsByPartialText;


    static MacUIElementRef findElementByXpath(MacUIElementRef element, String xpath){
        String[] nodes = xpath.split("/");
        MacUIElementRef currentElement =element;
        for(String node:nodes){
            if(node.equals("**")){
                   /*
                    Get index =0 to locate the main window;
                    AXApplication -> [AXWindow,AXMenu]
                     */
                currentElement =element.get_ChildrenElements().get(0);
                while (true){
                    assert currentElement != null;
                    if(currentElement.get_ChildrenElements().size()>1){
                        break;
                    }else {
                        currentElement = currentElement.get_ChildrenElements().get(0);
                    }
                }
            }else{
                assert currentElement != null;
                if(node.contains("[")){
                    int index = Integer.parseInt(node.replaceAll("\\D", ""));
                    String role = node.split("\\[")[0];
                    currentElement = currentElement.get_ChildrenElements().stream().filter(e -> e.get_Role().equals(role)).collect(Collectors.toList()).get(index-1);
                }
                else{
                    currentElement = currentElement.get_ChildrenElements().stream().filter(e -> e.get_Role().equals(node)).findFirst().orElse(null);
                }
            }
        }
        return currentElement;
    }

    BiFunction<MacUIElementRef, String, MacUIElementRef> findElementByXpath = FindUIElement::findElementByXpath;
}
