package coder.calculator.logic;

import java.util.ArrayList;

public class ThirdPriority extends Logic {

    private ArrayList<String> exercise = new ArrayList<String>();
    private double sum;

    public void setValues(ArrayList<String> exercise, double sum) {
        this.exercise = exercise;
        this.sum = sum;
    }

    public ArrayList<String> getExercise() {
        calculateResultAllExpression();
        return exercise;
    }

    private void calculateResultAllExpression() {
        if(exercise.size()==1 && exercise.get(0).charAt(0) == '-') return;

        for (int i = 0; i < exercise.size(); i++) {
            if (exercise.get(i).charAt(0) == '+') {
                sum = Double.parseDouble(exercise.get(i - 1)) + Double.parseDouble(exercise.get(i + 1));
                removePartExpression(i, sum, exercise);
                i = 0;
            } else if (exercise.get(i).charAt(0) == '-') {
                sum = Double.parseDouble(exercise.get(i - 1)) - Double.parseDouble(exercise.get(i + 1));
                removePartExpression(i, sum, exercise);
                i = 0;
            }
        }
    }
}
