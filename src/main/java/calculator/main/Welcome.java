package calculator.main;

import calculator.exceptions.ExtraSymbolException;
import calculator.exceptions.InvalidSymbolException;
import calculator.logic.ExampleInfo;
import calculator.logic.ExampleProcessing;

import java.util.Scanner;

public class Welcome {

    public void start(){
        ExampleProcessing example = new ExampleProcessing();
        ExampleInfo exampleInfo = new ExampleInfo();

        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.print("You want to calculate? (Yes/No) ");
            String choose = in.nextLine();
            if (choose.equalsIgnoreCase("Yes")) {
                System.out.print("Enter your exercise: ");
                String calc = in.nextLine();
                //start of arithmetic action
                    example.setsBExample(calc);
                try {
                    exampleInfo.setsBExample(example.getsBExample());
                    exampleInfo.printResultProcessingExample();
                } catch (InvalidSymbolException | ExtraSymbolException e) {
                    System.out.println(e.getMessage());
                }
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
