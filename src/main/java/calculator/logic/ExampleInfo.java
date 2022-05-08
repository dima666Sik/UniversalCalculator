package calculator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExampleInfo {
    private ArrayList<String> sBExample = new ArrayList<>();

    public void setsBExample(ArrayList<String> sBExample) {
        this.sBExample = sBExample;
    }

    public void printResultProcessingExample(){
        System.out.print("Result processing example: ");
        sBExample.forEach((s)-> System.out.print(s));
        System.out.println();
        sBExample.clear();
    }

    public void printObjects(Object o) {
        if (o instanceof ArrayList) {
            System.out.println("arrayList");
            ((ArrayList) o).forEach((i) -> System.out.println(i));
        } else if (o instanceof Map) {
            System.out.println("map");
            ((HashMap) o).forEach((k, v) -> System.out.println(k + ": " + v));
        }
        System.out.println("----------------------------------------");
    }
}
