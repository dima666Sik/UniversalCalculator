package calculator.logic;

import calculator.exceptions.ExtraSymbolException;
import calculator.exceptions.InvalidSymbolException;
import calculator.logic.interfaces.I_Logic;
import calculator.logic.levels.IntermediateLevel;

import java.util.ArrayList;

public class ExampleProcessing extends IntermediateLevel implements I_Logic {
    public final static String[] ARRAY_PERMITTED_SYMBOLS_ACTIONS = {"+", "-", "*", "/", "%", "^", "(", ")"}; //root please add...
    public final static String[] ARRAY_PERMITTED_SYMBOLS_NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private ArrayList<String> sBExample = new ArrayList<>();

    public ArrayList<String> getsBExample() throws InvalidSymbolException, ExtraSymbolException {
        return searchTwoOperandsAndCalculate();
    }

    public void setsBExample(String sBExample) {
        for (int i = 0; i < sBExample.length(); i++) {
            this.sBExample.add(String.valueOf(sBExample.charAt(i)));
        }
    }

    private ArrayList<String> searchTwoOperandsAndCalculate() throws InvalidSymbolException, ExtraSymbolException {
        boolean flag = false;
        for (String s : sBExample) {
            if (checkOperands(s)) { //check must was once, not every time!
                flag = true;
            } else {
                flag = false;
                break;
            }
        }

        //check num is tens, hundreds, thousands...
        return processingOperands(flag, sBExample);
    }

    private ArrayList<String> checkNumberDozens(ArrayList<String> sBExample) throws InvalidSymbolException {
        for (int i = 0; i < sBExample.size(); i++) {
            if (i == sBExample.size() - 1) {
                break;
            }

            if (isArithmeticActionsSymbol(sBExample.get(0))) {
                if (sBExample.get(0).equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[0])) {
                    gluNumber(sBExample, 0, 1);
                } else if (sBExample.get(0).equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[1])) {
                    gluNumber(sBExample, 0, 1);
                } else if (sBExample.get(0).equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[2]) ||
                        sBExample.get(0).equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[3])) {
                    sBExample.clear();
                    throw new InvalidSymbolException("You enter invalid example!");
                }
                if (sBExample.size() == 1) return sBExample;
            }

            if (!isArithmeticActionsSymbol(sBExample.get(i)) && isNumberSymbol(sBExample.get(i + 1))) { //check must was once, not every time!
                gluNumber(sBExample, i, i + 1);
                i = -1;
            }
        }
        return sBExample;
    }

    private void gluNumber(ArrayList<String> sBExample, int firstIndexPartNumber, int secondIndexPartNumber) {
        String tempGlu = sBExample.get(firstIndexPartNumber) + sBExample.get(secondIndexPartNumber);
        remove(sBExample, firstIndexPartNumber, ++secondIndexPartNumber);
        sBExample.add(firstIndexPartNumber, tempGlu);
    }


    private ArrayList<String> processingOperands(boolean flag, ArrayList<String> sBExample) throws InvalidSymbolException, ExtraSymbolException {
        if (flag) {
            checkNumberDozens(sBExample);

            /** Main part program, branching on example without brackets and with them... **/
            if (calculateSolutionExampleWithBrackets(sBExample)) {
                calculateSolutionExampleConsiderBrackets(sBExample);
            } else {
                calculateSolutionExample(sBExample);
            }
        } else {
            sBExample.clear();
            throw new InvalidSymbolException("Invalid Symbol Exception");
        }
        return sBExample;
    }

    private boolean checkOperands(String charValue) {
        return isNumberSymbol(charValue) || isArithmeticActionsSymbol(charValue);
    }

    private boolean isNumberSymbol(String charValue) {
        return charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[0]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[1]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[2]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[3]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[4]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[5]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[6]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[7]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[8]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_NUMBERS[9]);
    }

    private static boolean isArithmeticActionsSymbol(String charValue) {
        return charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[0]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[1]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[2]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[3]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[4]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[5]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[6]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[7]);
    }

    private boolean checkBrackets(String charValue) {
        return charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[6]) ||
                charValue.equals(ARRAY_PERMITTED_SYMBOLS_ACTIONS[7]);
    }

    private boolean calculateSolutionExampleWithBrackets(ArrayList<String> sBExample) {
        boolean flag = false;
        for (String s : sBExample) {
            if (checkBrackets(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
