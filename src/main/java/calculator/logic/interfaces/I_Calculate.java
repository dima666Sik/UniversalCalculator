package calculator.logic.interfaces;

import java.util.ArrayList;

public interface I_Calculate extends I_Logic {
    ArrayList<String> calculate(ArrayList<String> sBExample, int indexFirstOperand, int indexSecondOperand);
}
