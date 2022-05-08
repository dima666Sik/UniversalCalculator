package calculator.logic.interfaces;

import java.util.ArrayList;

public interface I_Logic {
    default void remove(ArrayList<String> sBExample, int start, int end) {
        if (end > start) {
            sBExample.subList(start, end).clear();
        }
    }

    default void replacementPartExpression(ArrayList<String> sBExample, String tempValue, int indexFirstOperand, int indexSecondOperand){
        remove(sBExample, indexFirstOperand, ++indexSecondOperand);
        sBExample.add(indexFirstOperand, tempValue);
    }
}
