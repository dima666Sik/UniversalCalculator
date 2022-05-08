package calculator.logic.levels;

import calculator.exceptions.ExtraSymbolException;
import calculator.logic.ExampleProcessing;
import calculator.logic.interfaces.I_Logic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntermediateLevel implements I_Logic {
    private static int tempCountBrackets = 0;
    private static int countLeftBrackets = 0;
    private static String strNest = "1";
    private static Integer[] indexMax = new Integer[2];
    private ArrayList<Integer> arrayListIndexBrackets = new ArrayList<>();
    private Map<Integer, String> mapNestingBrackets = new HashMap<>();
    private Map<Integer, String> mapIndexNestingBracketsValue = new HashMap<>();
    private ArrayList<String> tempPartExample = new ArrayList<>();

    // ((9*9)+4)-(5/2) => 0:1, 1:11, 5:11, 8:1, 10:1, 14:1
    // method for processing an example
    protected void calculateSolutionExampleConsiderBrackets(ArrayList<String> sBExample) throws ExtraSymbolException {
        fillInMapBrackets(sBExample);
        if (mapNestingBrackets.size() % 2 != 0) {
            clearAllCollectionsInThisClass();
            throw new ExtraSymbolException("Brackets not closed!");
        }

        initializeMapNestingBrackets();

        for (int i = 0; i < countLeftBrackets; i++) {
            if (!checkBrackets(sBExample) && sBExample.size() != 0) {
                calculateSolutionExample(sBExample);
                break;
            }
            if (sBExample.size() == 3 &&
                    sBExample.get(0).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[6]) &&
                    sBExample.get(2).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[7])) {
                replacementPartExpression(sBExample, sBExample.get(1), 0, 2);
                break;
            }

            searchNestBrackets(); // initialize indexMax
            int indexTempValues = indexMax[0] + 1;
            for (int j = 0; j < (indexMax[1] - 1) - indexMax[0]; j++) {
                tempPartExample.add(sBExample.get(indexTempValues++));
            }
            calculateSolutionExample(tempPartExample); // calc part example
            replacementPartExpression(sBExample, tempPartExample.get(0), indexMax[0], indexMax[1]);
            //clear arr with index & map with v:k
            refreshData(sBExample);
        }

        if (!checkBrackets(sBExample) && sBExample.size() != 0) {
            calculateSolutionExample(sBExample);
        }

        clearAllCollectionsInThisClass();
    }

    private void refreshData(ArrayList<String> sBExample) {
        arrayListIndexBrackets.clear();
        mapNestingBrackets.clear();
        mapIndexNestingBracketsValue.clear();
        tempPartExample.clear();
        indexMax = new Integer[2];
        fillInMapBrackets(sBExample);
        initializeMapNestingBrackets();
    }

    private void initializeMapNestingBrackets() {
        for (int k = 0; k < mapNestingBrackets.size(); k++) {
            mapIndexNestingBracketsValue.put(arrayListIndexBrackets.get(k), insertKIndexVNest(k));
        }
    }

    private void searchNestBrackets() {
        boolean flag = false;
        for (int i = 0; i < mapNestingBrackets.size() - 1; i++) {
            for (int j = i + 1; j < mapNestingBrackets.size(); j++) {
                if (new BigInteger(mapIndexNestingBracketsValue.get(arrayListIndexBrackets.get(i))).
                        compareTo(new BigInteger(mapIndexNestingBracketsValue.get(arrayListIndexBrackets.get(j)))
                )==-1) {
                    flag = true;
                    indexMax[0] = arrayListIndexBrackets.get(j - 1);
                    indexMax[1] = arrayListIndexBrackets.get(j);
                }
            }
        }
        if (!flag) {
            indexMax[0] = arrayListIndexBrackets.get(0);
            indexMax[1] = arrayListIndexBrackets.get(1);
        }
    }

    private void fillInMapBrackets(ArrayList<String> sBExample) {
        int count = 0;
        for (int i = 0; i < sBExample.size(); i++) {
            if (sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[6]) ||
                    sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[7])) {
                arrayListIndexBrackets.add(i);
                mapNestingBrackets.put(count++, sBExample.get(i));
            }
        }
    }

    private boolean checkBrackets(ArrayList<String> sBExample) {
        boolean flag = false;
        for (String s : sBExample) {
            if (s.equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[6]) ||
                    s.equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[7])) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void clearAllCollectionsInThisClass() {
        arrayListIndexBrackets.clear();
        mapNestingBrackets.clear();
        mapIndexNestingBracketsValue.clear();
        countLeftBrackets = 0;
    }

    private String insertKIndexVNest(int i) {
        if (i == 0 || tempCountBrackets == 0) {
            tempCountBrackets++;
            countLeftBrackets++;
            return strNest;
        }
        if (mapNestingBrackets.get(i - 1).equals(mapNestingBrackets.get(i))) {
            if (mapNestingBrackets.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[6])) {
                tempCountBrackets++;
                countLeftBrackets++;
                return strNest = strNest.concat("1");
            } else if (mapNestingBrackets.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[7])) {
                tempCountBrackets--;
                return strNest = strNest.substring(0, strNest.length() - 1);
            } else return "";
        } else if (!mapNestingBrackets.get(i - 1).equals(mapNestingBrackets.get(i)) && tempCountBrackets > 0) {
            tempCountBrackets--;
            return mapIndexNestingBracketsValue.get(arrayListIndexBrackets.get(i - 1));
        } else {
            tempCountBrackets--;
            return strNest = strNest.substring(0, strNest.length() - 1);
        }
    }

    protected void calculateSolutionExample(ArrayList<String> sBExample) throws ExtraSymbolException {
        // processing *, /, %, ^
        for (int i = 0; i < sBExample.size(); i++) {
            if (sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[2]) ||
                    sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[3]) ||
                    sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[4]) ||
                    sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[5])) {
                if (checkExtraSymbols(sBExample, i, i + 1)) throw new ExtraSymbolException("Extra Symbol Exception");
                new SecondLevel().calculate(sBExample, i - 1, i + 1);
                i -= 1;
            }
        }

        // processing +, -
        for (int i = 0; i < sBExample.size(); i++) {
            if (sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[0]) ||
                    sBExample.get(i).equals(ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[1])) {
                if (checkExtraSymbols(sBExample, i, i + 1)) throw new ExtraSymbolException("Extra Symbol Exception");
                new ThirdLevel().calculate(sBExample, i - 1, i + 1);
                i -= 1;
            }
        }
    }

    private boolean checkExtraSymbols(ArrayList<String> sBExample, int currentSymbol, int futureSymbol) {
        boolean flag1 = false, flag2 = false;
        String[] tempArrPSA = new String[ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS.length - 2];

        for (int i = 0; i < ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS.length - 2; i++) {
            tempArrPSA[i] = ExampleProcessing.ARRAY_PERMITTED_SYMBOLS_ACTIONS[i];
        }

        for (String s : tempArrPSA) {
            if (sBExample.get(currentSymbol).equals(s)) flag1 = true;
            if (sBExample.get(futureSymbol).equals(s)) flag2 = true;
        }
        return flag1 && flag2;
    }
}
