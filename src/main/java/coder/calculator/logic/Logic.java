package coder.calculator.logic;

import java.util.ArrayList;

public class Logic {
    private ArrayList<String> exercise = new ArrayList<>();

    public void setExercise(String exercise) {
        if(searchUnnecessarySymbolsInArray(exercise)) return;
        String temp = "";
        int count = 0;
        for (int i = 0; i != exercise.length() + 1; i++) {
            if (i != exercise.length() &&
                    exercise.charAt(i) == '-' &&
                    exercise.charAt(i + 1) != '+' &&
                    exercise.charAt(i + 1) != '%' &&
                    exercise.charAt(i + 1) != '*' &&
                    exercise.charAt(i + 1) != '/' &&
                    exercise.charAt(i + 1) != '(' &&
                    exercise.charAt(i + 1) != ')') {
                if (i == 0) {
                    temp += exercise.charAt(i) + "" + exercise.charAt(i + 1) + "";
                    i++;
                    continue;
                }
                if (i != 0 && (
                        exercise.charAt(i - 1) == '+' ||
                                exercise.charAt(i - 1) == '*' ||
                                exercise.charAt(i - 1) == '/' ||
                                exercise.charAt(i - 1) == '%' ||
                                exercise.charAt(i - 1) == '(' ||
                                exercise.charAt(i - 1) == ')')) {
                    temp += exercise.charAt(i) + "" + exercise.charAt(i + 1) + "";
                    i++;
                    continue;
                }

            }
            if (i != exercise.length() &&
                    exercise.charAt(i) != '+' &&
                    exercise.charAt(i) != '-' &&
                    exercise.charAt(i) != '*' &&
                    exercise.charAt(i) != '/' &&
                    exercise.charAt(i) != '%' &&
                    exercise.charAt(i) != '(' &&
                    exercise.charAt(i) != ')') {
                temp += exercise.charAt(i);
            } else {
                if (temp != "") {
                    this.exercise.add(temp);
                }
                if (i != exercise.length()) {
                    this.exercise.add(exercise.charAt(i) + "");
                    temp = "";
                }
            }
        }
    }

    private boolean searchUnnecessarySymbolsInArray(String exercise) {
        for (int i = 0; i < exercise.length() + 1; i++) {
            if (i != exercise.length() &&
                    (exercise.charAt(i) != '+' &&
                            exercise.charAt(i) != '-' &&
                            exercise.charAt(i) != '%' &&
                            exercise.charAt(i) != '*' &&
                            exercise.charAt(i) != '/' &&
                            exercise.charAt(i) != '(' &&
                            exercise.charAt(i) != ')' &&
                            exercise.charAt(i) != '1' &&
                            exercise.charAt(i) != '2' &&
                            exercise.charAt(i) != '3' &&
                            exercise.charAt(i) != '4' &&
                            exercise.charAt(i) != '5' &&
                            exercise.charAt(i) != '6' &&
                            exercise.charAt(i) != '7' &&
                            exercise.charAt(i) != '8' &&
                            exercise.charAt(i) != '9' &&
                            exercise.charAt(i) != '0')) {
                System.out.println("You enter unnecessary symbols in array");
                return true;
            }
        }
        return false;
    }

    public void searchInMassString() {
        double sum = 0;
        FirstPriority firstPriority = new FirstPriority();
        firstPriority.setValues(exercise, sum);
        exercise = firstPriority.getExercise();
    }

    void removePartExpression(int i, double sum, ArrayList<String> exercise) {
        exercise.remove(i);
        exercise.remove(i - 1);
        exercise.remove(i - 1);
        exercise.add(i - 1, sum + "");
    }

    public void printResult() {
        for (int i = 0; i < exercise.size(); i++) {
            System.out.println(exercise.get(i));
        }
        exercise.clear();
    }

}
