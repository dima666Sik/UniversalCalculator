package coder.calculator.logic;

import java.util.ArrayList;

public class Logic {
    private ArrayList<String> exercise = new ArrayList<>();

    public void setExercise(String exercise) {
        String temp = "";
        int count = 0;
        for (int i = 0; i != exercise.length() + 1; i++) {
            if (i != exercise.length() &&
                    exercise.charAt(i) == '-' &&
                    exercise.charAt(i + 1) != '+' &&
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
