import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public abstract class Main {

    // aus2 and san3 are "empty" warehouses rn, haven't assigned them employees or inventory receptacles
    private static ONT2 ont2;
    private static AUS2 aus2;
    private static SAN3 san3;
    // this program will eventually allow the user to choose their FC, giving them 3 options (ONT2/AUS2/SAN3), similar
    // to how in real life we were able to select ONT2 and some other nearby ONT FCs (ONT3/ONT4/ONT5, I think)
    private static String currentFCName;
    // The only way that a user can stop the program is by testing program termination at the Department/Process
    // Selection Menu screen. Later, I might add a "force terminate" (FCT+CTRL+T) feature.
    private static boolean runProgram;

    public static void main(String[] args) {

        Main.initializeSortableFCs();

        SimulationTool.createSimulationEnvironment();

        Main.userLogin();
    }

    private static void initializeSortableFCs() {

        Main.ont2 = new ONT2();
        Main.aus2 = new AUS2();
        Main.san3 = new SAN3();

        // send Main.ont2 to a static method that initializes most of its fields
        ONT2Initializer.initializeONT2Fields(Main.ont2);

        //TODO: add code to initialize AUS2 and SAN3 fields
    }

                ///// PROMPT USER TO ENTER ID, CHECK FOR VALIDITY AND USE IT TO RETRIEVE USER LOGIN /////

    private static void userLogin() {

        Main.runProgram = true;

        do {
            Main.showLoginScreen();

            String userInput = Main.promptUserToEnterID();

            // 1 <= userInput.length() <= 9 for validUserInputLength to be true
            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput, 1, 9);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (!userInputIsNumeric) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            // should NOT throw Exception, for I have already checked that userInput fits into an int variable
            // and contains numeric chars only
            int userInputParsed = Integer.parseInt(userInput);

            // compare userInputParsed to ALL SortableFC employee IDs, checking for a match...recall that in real-life
            // Amazon, I could have entered a random ID and the system allowed me to log-in as that employee, even if
            // that employee worked in a different state
            boolean validEmployeeID = Main.checkForValidEmployeeID(userInputParsed);

            if (!validEmployeeID) {
                System.out.println();
                System.out.println("Invalid employee ID.");
                continue;
            }

            // check all SortableFCs to see which one has userInputParsed as an employee ID...if match, get login
            // note that there SHOULD be a match at this point, and that in the future I might check a text-file instead
            String currentUserLogin = Main.getCurrentUserLogin(userInputParsed);

            Main.sortableFCSelection(currentUserLogin);

        } while (Main.runProgram);
    }

    private static void showLoginScreen() {

        System.out.println();
        System.out.println("****************************");
        System.out.println("Amazon Sortable FC Main Menu");
        System.out.println(new Date());
        System.out.println("****************************");
    }

    private static String promptUserToEnterID() {

        Scanner input = new Scanner(System.in);

        String userInput = null;

        System.out.println();
        System.out.print("> Enter your employee ID: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    // perhaps modify this code to read-in values from a text file
    private static boolean checkForValidEmployeeID(int userInput) {

        Boolean validEmployeeID = null;

        ArrayList<Integer> listOfAllSortableFCEmployeeIds = new ArrayList<>(15_000);

        // get ONT2, AUS2, and SAN3 lists of employees in order to add all employee ids to a list, then trim to size
        ArrayList<Employee> listOfEmployees = Main.ont2.getListOfEmployees();

        for (int i = 0; i < listOfEmployees.size(); i++)
             listOfAllSortableFCEmployeeIds.add(listOfEmployees.get(i).getID());

        listOfEmployees = Main.aus2.getListOfEmployees();

        for (int i = 0; i < listOfEmployees.size(); i++)
             listOfAllSortableFCEmployeeIds.add(listOfEmployees.get(i).getID());

        listOfEmployees = Main.san3.getListOfEmployees();

        for (int i = 0; i < listOfEmployees.size(); i++)
             listOfAllSortableFCEmployeeIds.add(listOfEmployees.get(i).getID());

        listOfAllSortableFCEmployeeIds.trimToSize();

        // declare and initialize array to store all employee ids, use for loop to add values
        int[] arrayOfAllSortableFCEmployeeIds = new int[listOfAllSortableFCEmployeeIds.size()];

        for (int i = 0; i < listOfAllSortableFCEmployeeIds.size(); i++) {
             arrayOfAllSortableFCEmployeeIds[i] = listOfAllSortableFCEmployeeIds.get(i);
        }

        // don't need the ArrayList anymore, so assign its value to null
        listOfAllSortableFCEmployeeIds = null;

        // use do-while loop to check the array of employee ids to see if userInput matches one of them
        boolean matchFound = false;

        // check for off-by-one error
        // TODO: experiment with the algo below to find the details of pre/post-fix operator workings
        int i = 0;
        do {
            if (i == arrayOfAllSortableFCEmployeeIds.length){
                break;
            }
            if (userInput == arrayOfAllSortableFCEmployeeIds[i]) {
                matchFound = true;
            }
            i++;
        } while (!matchFound || i < arrayOfAllSortableFCEmployeeIds.length);

        validEmployeeID = matchFound;

        return validEmployeeID;
    }

    //TODO: probably best to get this value from a text file or perhaps data structure like 2-dimensional array
    private static String getCurrentUserLogin(int employeeID) {

        String currentUserLogin = null;

        ArrayList<Employee> allSortableFCEmployees = new ArrayList<>(15_000);

        // get ONT2, AUS2, and SAN3 employees, add them to the list of allSortableFCEmployees created above
        // note that all SortableFC's have their listOfEmployees field initialized in the SortableFC constructor,
        // thus even though AUS2 and SAN3 have 0 employees at the moment, no exception should be thrown

        // ONT2
        ArrayList<Employee> listOfEmployees = Main.ont2.getListOfEmployees();
        allSortableFCEmployees.addAll(listOfEmployees);

        // AUS2
        listOfEmployees = Main.aus2.getListOfEmployees();
        allSortableFCEmployees.addAll(listOfEmployees);

        // SAN3
        listOfEmployees = Main.san3.getListOfEmployees();
        allSortableFCEmployees.addAll(listOfEmployees);

        // Traverse list allSortableFCEmployees to match userInput to an actual employee ID, and when the match is found
        // get the login of the corresponding employee. Note that prior to invoking this method, the program has already
        // verified that userInput corresponds to a legal employeeID, thus this method should NOT return null.
        for (int i = 0; i < allSortableFCEmployees.size(); i++) {
             if (allSortableFCEmployees.get(i).getID() == employeeID) {
                 currentUserLogin = allSortableFCEmployees.get(i).getLogin();
                 break;
             }
        }

        return currentUserLogin;
    }

    //// AT THIS POINT, USER HAS ENTERED A VALID ID AND WE HAVE THEIR LOGIN, NOW HAVE THEM SELECT AN FC /////

    private static void sortableFCSelection(String currentUserLogin) {

        boolean continueInput = true;

        do {
            Main.showSortableFCSelectionMenu(currentUserLogin);

            String userInput = Main.promptUserToSelectSortableFC();

            // // 1 <= userInput.length() <= 1 for validUserInputLength to be true
            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput, 1, 1);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (!userInputIsNumeric) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            // should NOT throw an exception, for userInput has been checked to be numeric in the method call above
            int userInputParsed = Integer.parseInt(userInput);

            String fcSelected;

            if (userInputParsed == 1) {
                fcSelected = "ONT2";
                continueInput = false;
            }
            else if (userInputParsed == 2) {
                     System.out.println();
                     System.out.println("AUS2 is still under construction! Select another FC.");
                     continue;
            }
            else if (userInputParsed == 3) {
                     System.out.println();
                     System.out.println("SAN3 is still under construction! Select another FC.");
                     continue;
            }
            else {
                  System.out.println();
                  System.out.println("Invalid input.");
                  continue;
            }

            boolean userHasAccessToSelectedFC = Main.checkForFCAccessPermission(currentUserLogin, fcSelected);

            if (!userHasAccessToSelectedFC) {
                System.out.println();
                System.out.println("You don't have access to the selected FC.");
                continue;
            }

            Main.currentFCName = fcSelected;

        } while (continueInput);

        Main.departmentAndProcessSelection(currentUserLogin);
    }

    private static void showSortableFCSelectionMenu(String currentUserLogin) {

        System.out.println();
        System.out.println("**************************");
        System.out.println("Sortable FC Selection Menu");
        System.out.println("**************************");
        System.out.println();
        System.out.println("Welcome, " + currentUserLogin + "!");
        System.out.println();
        System.out.println("Select your FC: ");
        System.out.println();
        System.out.println("1. ONT2");
        System.out.println("2. AUS2");
        System.out.println("3. SAN3");
    }

    private static String promptUserToSelectSortableFC() {

        String userInput = null;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    private static boolean checkForFCAccessPermission(String currentUserLogin, String fcName){

        Boolean hasFCAccess = null;

        ArrayList<String> listOfLoginsWithFCAccess = (Main.getSortableFC(fcName)).getListOfLoginsWithFCAccess();

        // create var to update in case currentUserLogin is found in a list of all logins with access to the FC
        boolean matchFound = false;

        for (int i = 0; i < listOfLoginsWithFCAccess.size(); i++) {
             if (listOfLoginsWithFCAccess.get(i).equalsIgnoreCase(currentUserLogin)) {
                 matchFound = true;
                 break;
             }
        }

        hasFCAccess = matchFound;

        return hasFCAccess;
    }

    ///// WE HAVE A VALID EMPLOYEE AND THEY HAVE SELECTED THEIR FC, NOW HAVE THEM SELECT A DEPT OR PROCESS /////

    private static void departmentAndProcessSelection(String currentUserLogin) {

        boolean continueInput = true;

        do {
            Main.showDepartmentAndProcessSelectionMenu();

            String userInput = Main.promptUserToSelectDepartmentOrProcess();

            // 1 <= userInput.length() <= 3 for validUserInputLength to be true
            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput, 1, 3);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (!userInputIsNumeric) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            int userInputParsed = Integer.parseInt(userInput);

            if (userInputParsed == 1) {
                System.out.println();
                System.out.println("Sorry, this department is not available yet.");
            }
            else if (userInputParsed == 2) {
                     System.out.println();
                     System.out.println("Sorry, this department is not available yet.");
            }
            else if (userInputParsed == 3) {
                     ICQA.processSelection(currentUserLogin);
            }
            else if (userInputParsed == 4) {
                     boolean hasCreatedInternalNetworkPassword =
                             Main.checkForInitializedInternalNetworkPassword(currentUserLogin);

                     if (hasCreatedInternalNetworkPassword) {
                         userInput = Main.promptUserToEnterPassword();

                         // use String currentUserLogin to get employee's internalNetworkPassword
                         String currentUsersInternalNetworkPassword =
                                          SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin)
                                          .getInternalNetworkPassword();

                         // check that userInput matches actual created password
                         if (userInput.equals(currentUsersInternalNetworkPassword)) {
                             Mastermind.mainMenu(currentUserLogin);
                         }
                         else {
                               System.out.println();
                               System.out.println("Invalid password.");
                         }
                     }
                     // if the currentUser never created a password, they don't have access to Mastermind
                     else {
                           System.out.println();
                           System.out.println("You don't have permission to access Mastermind.");
                     }
            }
            else if (userInputParsed == 5) {
                     continueInput = false;
                     System.out.println();
                     System.out.println("Log out successful.");
            }
            else if (userInputParsed == 6) {
                     continueInput = false;
                     Main.runProgram = false;
                     System.out.println();
                     System.out.println("*******************");
                     System.out.println("TERMINATING PROGRAM");
                     System.out.println("*******************");
                     System.out.println();
                     System.out.println("...");
            }
            else if (userInputParsed == 100) {
                     Test.start();
            }
            else {
                  System.out.println();
                  System.out.println("Invalid input.");
            }
        } while (continueInput);
    }

    private static void showDepartmentAndProcessSelectionMenu() {

        System.out.println();
        System.out.println("*********************************");
        System.out.println("Department/Process Selection Menu");
        System.out.println("*********************************");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("1. Outbound");
        System.out.println("2. Inbound");
        System.out.println("3. ICQA");
        System.out.println("-----------------------------");
        System.out.println("4. Mastermind");
        System.out.println("-----------------------------");
        System.out.println("5. Log Out");
        System.out.println("-----------------------------");
        System.out.println("6. Terminate Program");
        System.out.println("-----------------------------");
        System.out.println("100. Test Mode");
        System.out.println("-----------------------------");
    }

    private static String promptUserToSelectDepartmentOrProcess() {

        String userInput = null;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    private static boolean checkForInitializedInternalNetworkPassword(String currentUserLogin) {

        Boolean hasCreatedInternalNetworkPassword = null;

        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);

        if (currentUser.getInternalNetworkPassword() == null) {
            hasCreatedInternalNetworkPassword = false;
        }
        else {
              hasCreatedInternalNetworkPassword = true;
        }

        return hasCreatedInternalNetworkPassword;
    }

    private static String promptUserToEnterPassword() {

        String userInput = null;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.print("> Enter your password: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }
                                        ///// CLASS GETTERS/SETTERS BELOW /////

    static String getCurrentFCName() {
        return Main.currentFCName;
    }

    private static void setCurrentFCName(String sortableFCName) {
        Main.currentFCName = sortableFCName;
    }

    static SortableFC getSortableFC(String sortableFCName) {

        SortableFC sortableFC = null;

        if (sortableFCName.equalsIgnoreCase("ONT2")) {
            sortableFC = Main.ont2;
        }
        else if (sortableFCName.equalsIgnoreCase("AUS2")) {
                 sortableFC = Main.aus2;
        }
        else if (sortableFCName.equalsIgnoreCase("SAN3")) {
                 sortableFC = Main.san3;
        }

        return sortableFC;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Perhaps String currentUserLogin shouuld be a field in class Main, which is null at program start but initialized to
  a String value once the user enters a valid employee id. OR, I could create a SessionObject and have
  String currentUserLogin as one of its fields.

- Perhaps the code for logging-in and selecting an FC should be in a separate class, and the code for selecting a
  department/process in another class? In other words, this class should be 3 separate classes? OR, I can have 4
  separate classes, Main --> UserLogin --> SortableFCSelection --> DepartmentSelection

- In real-life, the software in the scanners was, in fact, customized to some degree. For example, it only gave us a
  few choices when it came to selecting our FC, such as ONT2/ONT3/ONT4/ONT5. So there's nothing wrong with hard-coding
  a few values if it makes my code overall better. This means that when doing tasks such trying to get the reference
  to the employee object via login, it's fine to hardcode the software to only look within a few specific FCs, in this
  case ONT2, AUS2, and SAN3.

- Perhaps it's better design to declare ont2, aus2, and san3 in SimulationTool? Declare them as fields, initialize them
  with method calls directly after their declaration (I believe this code would get executed automatically at program
  start).

- Perhaps the only field in this class should be runProgram? currentFCName can be a field of some kind of Session
  object? Hard for me to implement these changes, because I don't have experience with Session object conventions.

- Perhaps it's best to check for a valid employee ID in a text file, as opposed to in a method in SBCSimUtils?

*/