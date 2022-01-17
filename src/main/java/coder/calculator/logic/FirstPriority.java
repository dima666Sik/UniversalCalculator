package coder.calculator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstPriority extends Logic {
    private ArrayList<String> exercise = new ArrayList<String>();
    private double sum;

    public void setValues(ArrayList<String> exercise, double sum) {
        this.exercise = exercise;
        this.sum = sum;
    }

    public ArrayList<String> getExercise() {
        calculateResultAllExpression();
        while (exercise.size() > 1) {
            calculateResultAllExpression();
        }
        return exercise;
    }

    private ArrayList<String> calcEasyExpression(ArrayList<String> exercise, double sum) {
        SecondPriority secondPriority = new SecondPriority();
        secondPriority.setValues(exercise, sum);
        exercise = secondPriority.getExercise();

        ThirdPriority thirdPriority = new ThirdPriority();
        thirdPriority.setValues(exercise, sum);
        exercise = thirdPriority.getExercise();
        return exercise;
    }

    private void removeExpressionAndAddAnswer(int iLeftBkt, int iRightBkt, ArrayList<String> exercise) {
        double sum = 0;
        ArrayList<String> tempExercise = new ArrayList<String>();

        int inc = iLeftBkt - 1;
        int saveInc = inc;

        for (; iLeftBkt < iRightBkt; iLeftBkt++) {
            tempExercise.add(exercise.get(iLeftBkt));
        }

        tempExercise = calcEasyExpression(tempExercise, sum);

        for (; inc != iRightBkt + 1; inc++) {
            exercise.remove(saveInc);
        }

        exercise.add(saveInc, tempExercise.get(0));
    }

    private void calcMainSerialExpression(int countLeftBkt,
                                          Map<Integer, Integer> hashMapLeftBkt,
                                          Map<Integer, Integer> hashMapRightBkt) {
        int tempCounter = countLeftBkt;

        int index = 0, key = tempCounter - 1;

        while (index < tempCounter) {

            countLeftBkt = 0;
            int countRightBkt = 0;

            for (int i = 0; i < exercise.size(); i++) {
                if (exercise.get(i).charAt(0) == '(') {
                    hashMapLeftBkt.put(countLeftBkt, i);
                    countLeftBkt++;
                } else if (exercise.get(i).charAt(0) == ')') {
                    hashMapRightBkt.put(countRightBkt, i);
                    countRightBkt++;
                }
            }

            int iLeftBkt = hashMapLeftBkt.get(key) + 1,
                    iRightBkt = hashMapRightBkt.get(0);

            removeExpressionAndAddAnswer(iLeftBkt, iRightBkt, exercise);

            index++;
            key--;

            hashMapLeftBkt.clear();
            hashMapRightBkt.clear();
        }
    }

    private void calculateResultAllExpression() {
        int countLeftBkt = 0,
                countRightBkt = 0;

        Map<Integer, Integer> hashMapLeftBkt = new HashMap<Integer, Integer>();
        Map<Integer, Integer> hashMapRightBkt = new HashMap<Integer, Integer>();
        Map<Integer, String> hashMapTypeBkt = new HashMap<Integer, String>();

        for (int i = 0, iterator = 0; i < exercise.size(); i++) {
            if (exercise.get(i).charAt(0) == '(') {
                countLeftBkt++;
            } else if (exercise.get(i).charAt(0) == ')') {
                countRightBkt++;
            }

            if (exercise.get(i).charAt(0) == '(' || exercise.get(i).charAt(0) == ')') {
                hashMapTypeBkt.put(iterator, exercise.get(i).charAt(0) + "");
                iterator++;
            }
        }

        if (countLeftBkt == countRightBkt && countLeftBkt == 0) {
            exercise = calcEasyExpression(exercise, sum);
            if (exercise.size() == 1) return;
        }

        if (countLeftBkt == countRightBkt) {

            boolean flag = false;

            if (hashMapTypeBkt.size() / 2 > 1) {
                for (int i = 0; i < hashMapTypeBkt.size() / 2 - 1; i++) {
                    if (hashMapTypeBkt.get(i).equals(hashMapTypeBkt.get(i + 1))) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag) calcMainSerialExpression(countLeftBkt, hashMapLeftBkt, hashMapRightBkt);
            else {
                int counterL = 0, counterR = 0;
                ArrayList<String> tempListExc = new ArrayList<String>();

                for (int i = 0; i < exercise.size(); i++) {
                    if (exercise.get(i).charAt(0) == '(') {
                        counterL++;
                    } else if (exercise.get(i).charAt(0) == ')') {
                        counterR++;
                    }
                    if (counterL == 0 && counterR == 0) {
//                        i++;
                        continue;
                    }
                    if (counterL == counterR) {

                        int count = 0;
                        for (int j = i + 1; j < exercise.size(); j++) {
                            tempListExc.add(exercise.get(j));
                            count++;
                        }

                        for (int g = 0; g < count; g++) {
                            exercise.remove(i + 1);
                        }

                        countLeftBkt = 0;
                        countRightBkt = 0;
                        for (int k = 0; k < exercise.size(); k++) {
                            if (exercise.get(k).charAt(0) == '(') {
                                hashMapLeftBkt.put(countLeftBkt, k);
                                countLeftBkt++;
                            } else if (exercise.get(k).charAt(0) == ')') {
                                hashMapRightBkt.put(countRightBkt, k);
                                countRightBkt++;
                            }
                        }

                        calcMainSerialExpression(countLeftBkt, hashMapLeftBkt, hashMapRightBkt);
                        if (tempListExc.size() != 0) exercise.addAll(tempListExc);

                        counterL = 0;
                        counterR = 0;
                        tempListExc.clear();
                        if (exercise.size() != 1) i = 0;
                    }

                }
            }
        } else {
            System.out.println("Add or delete dkt!!! \n" +
                    "Count dkt not equal.");
            exercise.clear();
        }
    }
}
