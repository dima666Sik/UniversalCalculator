package calculator.logic.levels;

import calculator.logic.ExampleProcessing;
import calculator.logic.interfaces.I_Calculate;

import java.util.ArrayList;

public class ThirdLevel implements I_Calculate {

    @Override
    public ArrayList<String> calculate(ArrayList<String> sBExample, int indexFirstOperand, int indexSecondOperand) {
        Double tempValue = 0.0;
        if (sBExample.get(indexFirstOperand + 1).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[0])) {
            tempValue = Double.parseDouble(sBExample.get(indexFirstOperand)) + Double.parseDouble(sBExample.get(indexSecondOperand));
            replacementPartExpression(sBExample,String.valueOf(tempValue), indexFirstOperand, indexSecondOperand);
        } else if (sBExample.get(indexFirstOperand + 1).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[1])) {
            tempValue = Double.parseDouble(sBExample.get(indexFirstOperand)) - Double.parseDouble(sBExample.get(indexSecondOperand));
            replacementPartExpression(sBExample,String.valueOf(tempValue), indexFirstOperand, indexSecondOperand);
        }
        return sBExample;
    }
}
