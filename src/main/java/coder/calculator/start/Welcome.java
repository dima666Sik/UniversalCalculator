package coder.calculator.start;

import coder.calculator.logic.Logic;

import java.util.Scanner;

public class Welcome {

    public void start(){
        Logic logic = new Logic();

        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.print("You want to calculate? (Yes/No) ");
            String choose = in.nextLine();
            if (choose.equalsIgnoreCase("Yes")) {
                System.out.print("Enter your exercise: ");
                String calc = in.nextLine();
                //start logic
                logic.setExercise(calc);
                logic.searchInMassString();
                logic.printResult();
                //end
            } else if (choose.equalsIgnoreCase("No")) {
                System.out.print("Exit program.");
                break;
            } else {
                System.out.println("Enter No or Yes. Wrong symbol!");
            }
        }
    }

}
