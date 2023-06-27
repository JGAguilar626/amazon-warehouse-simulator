import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class SBCSimUtils {

    static boolean checkForValidUserInputLength(String userInput, int minLength, int maxLength) {

        Boolean validUserInputLength = null;

        if (userInput.length() >= minLength && userInput.length() <= maxLength) {
            validUserInputLength = true;
        }
        else {
              validUserInputLength = false;
        }

        return validUserInputLength;
    }

    static boolean checkForNumericUserInput(String userInput) {

        Boolean userInputIsNumeric = null;

        for (int i = 0; i < userInput.length(); i++) {
             if (!Character.isDigit(userInput.charAt(i))) {
                 userInputIsNumeric = false;
                 break;
             }
             if (i == userInput.length() - 1) {
                 userInputIsNumeric = true;
             }
        }
        // shouldn't produce NullPointerException because if we reach the last index of String userInput and the break;
        // statement is not executed, we make userInputIsNumeric = true because if it wasn't, it would have broken-out
        return userInputIsNumeric;
    }

    static Employee getEmployeeFromListOfAllSortableFCEmployees(String login) {

        ArrayList<Employee> allSortableFCEmployees = new ArrayList<>();

        // get all ONT2 employees, add them to a list of all SortableFC employees...do same for AUS2 and SAN3
        ArrayList<Employee> ont2Employees = Main.getSortableFC("ONT2").getListOfEmployees();
        allSortableFCEmployees.addAll(ont2Employees);

        /*
        ArrayList<Integer> aus2EmployeeIDs = Main.aus2.getListOfEmployeeIDs();
        allSortableFCEmployeeIDs.addAll(aus2EmployeeIDs);

        ArrayList<Integer> san3EmployeeIDs = Main.san3.getListOfEmployeeIDs();
        allSortableFCEmployeeIDs.addAll(san3EmployeeIDs);
        */

        allSortableFCEmployees.trimToSize();

        Employee employee = null;

        for (int i = 0; i < allSortableFCEmployees.size(); i++) {
             if (allSortableFCEmployees.get(i).getLogin().equalsIgnoreCase(login)) {
                 employee = allSortableFCEmployees.get(i);
                 break;
             }
        }

        return employee;
    }

    static void orderReceptaclesByPickID(ArrayList<InventoryReceptacle> listOfReceptacles) {
        Collections.sort(listOfReceptacles, new BinSortByPickID());
    }

    static void showEnter1ToGoBackScreen() {

        boolean continueInput = true;

        Scanner input = new Scanner(System.in);

        do {
            System.out.println();
            System.out.print("> Enter 1 to go back: ");
            try {
                 int userInput = input.nextInt();
                 input.nextLine();

                if (userInput == 1) {
                    continueInput = false;
                }
                else {
                      System.out.println();
                      System.out.println("Invalid input. Try again.");
                }
            } catch (InputMismatchException ex) {
                input.nextLine();
                System.out.println();
                System.out.println("Invalid input. Try again.");
            }
        } while (continueInput);
    }
}