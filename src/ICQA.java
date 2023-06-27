import java.util.Scanner;
import java.util.ArrayList;

public class ICQA {

        ///// SHOW ICQA PROCESS SELECTION MENU, CHECK FOR APPROPRIATE PERMISSIONS, AND START PROCESS /////

    static void processSelection(String currentUserLogin) {

        boolean continueInput = true;

        do {
            ICQA.showProcessSelectionMenu();

            String userInput = ICQA.promptUserToChooseProcess();

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

            int userInputParsed = Integer.parseInt(userInput);

            // although "1" is a legal input, I do not make continueInput = false because this program gives the
            // employee the option to sign-out of the SBC process and return to this menu
            if (userInputParsed == 1) {
                if (ICQA.checkForSimpleBinCountPermission(currentUserLogin)) {
                    ICQA.simpleBinCount(currentUserLogin);
                }
                else {
                      System.out.println();
                      System.out.println("You don't have SBC permissions.");
                }
            }
            // return to the Department/Process Selection Menu
            else if (userInputParsed == 2) {
                     continueInput = false;
            }
            else {
                  System.out.println();
                  System.out.println("Invalid input. Try again.");
            }
        } while (continueInput);
    }

    private static void showProcessSelectionMenu() {

        System.out.println();
        System.out.println("**********************");
        System.out.println("ICQA Process Selection");
        System.out.println("**********************");
        System.out.println();
        System.out.println("1. SBC");
        System.out.println("2. Return to Department/Process Selection Menu");
    }

    private static String promptUserToChooseProcess() {

        String userInput = null;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    private static boolean checkForSimpleBinCountPermission(String currentUserLogin) {

        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);

        Boolean hasSimpleBinCountPermission = null;

        if (currentUser.isSimpleBinCountTrained()) {
            hasSimpleBinCountPermission = true;
        }
        else {
              hasSimpleBinCountPermission = false;
        }

        return hasSimpleBinCountPermission;
    }

        ///// IF USER CHOSE SBC AND THEY HAVE THE NECESSARY PROCESS PERMISSION, INITIATE SBC PROCESS /////

    private static void simpleBinCount(String currentUserLogin) {

        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);

        // get name of the FC that the current user selected after logging in
        String currentFCName = Main.getCurrentFCName();

        // use the currentFCName to get reference to appropriate instance of SortableFC
        SortableFC sortableFC = Main.getSortableFC(currentFCName);

        // display process name to let the user know process has initiated
        ICQA.showSBCOpeningScreen();

        // because creating individual SBC batches is beyond the scope of this program (for now), pendingSimpleBinCounts
        // will be one big batch accessible to only 1 counter at a time
        ArrayList<String> pendingSimpleBinCounts = SimulationTool.getPendingSimpleBinCounts(currentFCName);

        while (pendingSimpleBinCounts.size() > 0) {

               int pendingSBCListIndex = 0;

               String receptacleLocation = pendingSimpleBinCounts.get(0);

               InventoryReceptacle pendingReceptacle = sortableFC.getInventoryReceptacle(receptacleLocation);

               // [0] should hold the boolean var for whether the user entered "S" or "s" for (S)-ign-Out of process
               // a value of true = sign-out, a value of false = don't sign-out
               // [1] should hold the boolean var for whether the user scanned into the bin or not
               // a value of true = scanned-into bin, a value of false = created an andon before scanning the location,
               // skipped the location, or decided to sign-out
               boolean[] processControlBooleanVars = new boolean[3];

               ICQA.sbcScanIntoLocationScreen(currentUserLogin, pendingReceptacle, processControlBooleanVars);

               boolean userSignedOutOfProcess = processControlBooleanVars[0];
               boolean userSkippedTheLocation = processControlBooleanVars[1];
               boolean userScannedIntoLocation = processControlBooleanVars[2];

               if (userSignedOutOfProcess) {
                   break;
               }

               if (userSkippedTheLocation) {
                   pendingSimpleBinCounts.remove(0);
                   continue;
               }

               // var userScannedIntoLocation is important because it IS possible apart from deciding to sign-out, an user
               // might not have scanned into the location due to creating an andon or skipping the location
               if (userScannedIntoLocation) {
                   boolean continueCount = ICQA.sbcFirstCountScreen(currentUserLogin, pendingReceptacle);
                   if (continueCount) {
                       ICQA.sbcSecondCountScreen(currentUserLogin, pendingReceptacle);
                   }
               }
        }

        if (pendingSimpleBinCounts.size() == 0) {
            System.out.println();
            System.out.println("There are no pending Simple Bin Counts.");
        }
    }

    private static void showSBCOpeningScreen() {

        System.out.println();
        System.out.println("****************");
        System.out.println("Simple Bin Count");
        System.out.println("****************");
    }

    private static void sbcScanIntoLocationScreen(String currentUserLogin, InventoryReceptacle receptacle,
                                                  boolean[] processControlBooleanVars) {

        String receptacleLocation = receptacle.getLocation();

        boolean scannedIntoLocation = false;
        boolean skippedTheLocation = false;
        boolean signOutOfProcess = false;

        boolean continuePromptingToScanIntoLocation = true;

            do {
                String userInput = ICQA.promptUserToScanIntoLocation(receptacleLocation);

                boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength
                        (userInput, 1, 13);

                if (!validUserInputLength) {
                    System.out.println();
                    System.out.println("Invalid input.");
                    continue;
                }

                // invoke method in SBCSimUtils that checks for numeric user input, and parse if input is all numeric
                boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

                if (userInputIsNumeric && userInput.length() == 1) {
                    int userInputParsed = Integer.parseInt(userInput);

                    if (userInputParsed == 1) {
                        scannedIntoLocation = true;
                        continuePromptingToScanIntoLocation = false;
                    }
                    else {
                          System.out.println();
                          System.out.println("Invalid input. Try again.");
                    }
                }
                else if (userInput.equalsIgnoreCase(receptacleLocation)) {
                         scannedIntoLocation = true;
                         continuePromptingToScanIntoLocation = false;
                }
                else if (userInput.equalsIgnoreCase("P")) {
                         Boolean andonCreated = null;
                         andonCreated = ICQA.sbcProblemHandling(currentUserLogin, receptacle, 1);

                         if (andonCreated) {
                             continuePromptingToScanIntoLocation = false;
                         }
                }
                else if (userInput.equalsIgnoreCase("N")) {
                         skippedTheLocation = true;
                         continuePromptingToScanIntoLocation = false;
                         ICQA.updateFieldsAfterLocationSkip(currentUserLogin, receptacle);
                         System.out.println();
                         System.out.println("Location skipped.");
                }
                // this option takes the user back to the ICQA Process Selection screen
                else if (userInput.equalsIgnoreCase("S")) {
                         signOutOfProcess = true;
                         continuePromptingToScanIntoLocation = false;
                         ICQA.updateFieldsAfterProcessExit(currentUserLogin, receptacle);
                }
                else {
                      System.out.println();
                      System.out.println("Invalid input. Try again.");
                }
            } while (continuePromptingToScanIntoLocation);

            // add boolean values to the array arg, so that the calling method can know whether to sign-out of process
            // and whether the user scanned into the location
            processControlBooleanVars[0] = signOutOfProcess;
            processControlBooleanVars[1] = skippedTheLocation;
            processControlBooleanVars[2] = scannedIntoLocation;
    }

    private static boolean sbcFirstCountScreen(String currentUserLogin, InventoryReceptacle receptacle) {

        int virtualAmountOfItemsInReceptacle = receptacle.getNumberOfItems();

        // display items as asterisks on the console (only done for simulation purposes)
        SimulationTool.displayPhysicalInventoryAsAsterisks(virtualAmountOfItemsInReceptacle);

        Integer firstCount = null;
        Boolean continueCount = null;
        boolean continueInput = true;

        do {
            // prompt user to enter number of items physically present in receptacle
            String userInput = ICQA.promptUserToEnterCount(1);

            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput,1, 4);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (userInputIsNumeric) {
                firstCount = Integer.parseInt(userInput);
                continueInput = false;
            }
            else if (userInput.equalsIgnoreCase("P")) {
                     boolean andonCreated = ICQA.sbcProblemHandling(currentUserLogin, receptacle, 2);
                     if (andonCreated) {
                         continueInput = false;
                         continueCount = false;
                     }
            }
            else {
                   System.out.println();
                   System.out.println("Invalid input. Try again.");
            }
        } while (continueInput);

        // checks that firstCount != null because if the counter pulls an andon, firstCount will be null
        if (firstCount != null) {
            if (firstCount == virtualAmountOfItemsInReceptacle) {
                ICQA.updateFieldsAfterCountCompleted(currentUserLogin, receptacle);
                continueCount = false;
                System.out.println();
                System.out.println("Count completed.");
            }
            else {
                  continueCount = true;
            }
        }
        return continueCount;
    }

    private static void sbcSecondCountScreen(String currentUserLogin, InventoryReceptacle receptacle) {

        int virtualAmountOfItemsInReceptacle = receptacle.getNumberOfItems();

        Integer secondCount = null;
        String userInput;
        boolean continueInput = true;

        do {
            userInput = ICQA.promptUserToEnterCount(2);

            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput, 1, 4);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input. Try again.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (userInputIsNumeric) {
                secondCount = Integer.parseInt(userInput);
                continueInput = false;
            }
            else if (userInput.equalsIgnoreCase("P")) {
                     boolean andonCreated = ICQA.sbcProblemHandling(currentUserLogin, receptacle, 3);
                     if (andonCreated) {
                         continueInput = false;
                     }
            }
            else {
                System.out.println();
                System.out.println("Invalid input. Try again.");
            }
        } while (continueInput);

        if (secondCount != null) {
            if (secondCount != virtualAmountOfItemsInReceptacle) {
                receptacle.setNeedsCycleCount(true);
            }
            ICQA.updateFieldsAfterCountCompleted(currentUserLogin, receptacle);
            System.out.println();
            System.out.println("Count completed.");
        }
    }

    private static String promptUserToScanIntoLocation(String location) {

        Scanner input = new Scanner(System.in);

        String userInput = null;

        System.out.println();
        System.out.println("-------------");
        System.out.println(location);
        System.out.println("-------------");
        System.out.println();
        System.out.print("> Scan location or enter 1: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    private static String promptUserToEnterCount(int attemptNumber) {

        String message;
        message = attemptNumber == 1 ? "Count items." : "Re-count items.";

        String userInput;

        boolean continueInput = true;

        Scanner input = new Scanner(System.in);

        do {
             System.out.println();
             System.out.println(message);
             System.out.println();
             System.out.print("> Enter count: ");

             userInput = input.nextLine().trim();

             if (userInput.isEmpty()) {
                 System.out.println();
                 System.out.println("Invalid input. Try again.");
             }
             else {
                   continueInput = false;
             }
        } while (continueInput);

        return userInput;
    }

    private static boolean sbcProblemHandling
                           (String currentUserLogin, InventoryReceptacle receptacle, int sbcProcessScreen) {

        String userInput;

        Integer userInputParsed = null;

        boolean andonConfirmed = false;

        boolean continueInput = true;

        do {
            if (sbcProcessScreen == 1) {
                userInput = ICQA.showSBCProblemMenu1();
            }
            else {
                  userInput = ICQA.showSBCProblemMenu2();
            }

            boolean validUserInputLength = SBCSimUtils.checkForValidUserInputLength(userInput,1,1);

            if (!validUserInputLength) {
                System.out.println();
                System.out.println("Invalid input. Try again.");
                continue;
            }

            boolean userInputIsNumeric = SBCSimUtils.checkForNumericUserInput(userInput);

            if (!userInputIsNumeric) {
                System.out.println();
                System.out.println("Invalid input. Try again.");
                continue;
            }

            userInputParsed = Integer.parseInt(userInput);

            if (userInputParsed == 1 || userInputParsed == 2 || userInputParsed == 3 || userInputParsed == 4) {
                continueInput = false;
            }
        } while (continueInput);

        if (userInputParsed == 1 || userInputParsed == 2 || userInputParsed == 3) {
            andonConfirmed = ICQA.promptUserToConfirmAndonSBC();
            if (andonConfirmed) {
                Andon andon = ICQA.createAndon(currentUserLogin, receptacle, sbcProcessScreen, userInputParsed);
                ICQA.updateFieldsAfterAndonConfirmed(currentUserLogin, receptacle, andon);
            }
        }
        return andonConfirmed;
    }

    private static String showSBCProblemMenu1() {

        String userInput;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("****************");
        System.out.println("SBC Problem Menu");
        System.out.println("****************");
        System.out.println();
        System.out.println("Make a selection.");
        System.out.println();
        System.out.println("1. Bin does Not Exist");
        System.out.println("2. No Scannable Bin Label");
        System.out.println("3. Unsafe to Count");
        System.out.println("4. Go Back");
        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    // more than one Problem Menu because when a counter is scanned into a receptacle, andons like "Bin does Not Exist"
    // and "No Scannable Bin Label" are no longer relevant
    private static String showSBCProblemMenu2() {

        String userInput;
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("****************");
        System.out.println("SBC Problem Menu");
        System.out.println("****************");
        System.out.println();
        System.out.println("Make a selection.");
        System.out.println();
        System.out.println("1. Damaged Item");
        System.out.println("2. Broken Set");
        System.out.println("3. Suspected Theft");
        System.out.println("4. Go Back");
        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }

    private static boolean promptUserToConfirmAndonSBC() {

        Boolean andonConfirmed = null;

        boolean continueInput = true;

        Scanner input = new Scanner(System.in);

        do {
            System.out.println();
            System.out.println("Confirm andon?");
            System.out.println("Enter 'C' to confirm, or 'B' to go back.");
            System.out.println();
            System.out.print("> Enter choice: ");

            String userInput = input.nextLine();
            userInput = userInput.trim();

            if (userInput.equalsIgnoreCase("C")) {
                andonConfirmed = true;
                continueInput = false;
            }
            else if (userInput.equalsIgnoreCase("B")) {
                     andonConfirmed = false;
                     continueInput = false;
            }
            else {
                  System.out.println();
                  System.out.println("Invalid input. Try again.");
            }
        } while (continueInput);

        return andonConfirmed;
    }

    public static Andon createAndon
                        (String currentUserLogin, InventoryReceptacle receptacle, int sbcProcessScreen, int andonType) {

        String receptacleLocation = receptacle.getLocation();

        Andon andon = null;

        if (sbcProcessScreen == 1) {
            if (andonType == 1) {
                andon = new BinDoesNotExistAndon(receptacleLocation, currentUserLogin);
            }
            else if (andonType == 2) {
                     andon = new NoScannableBinLabelAndon(receptacleLocation, currentUserLogin);
            }
            else if (andonType == 3) {
                     andon = new UnsafeToCountAndon(receptacleLocation, currentUserLogin);
            }
        }
        else if (sbcProcessScreen == 2) {
                 if (andonType == 1) {
                     andon = new DamageAndon(receptacleLocation, currentUserLogin);
                 }
                 else if (andonType == 2) {
                          andon = new BrokenSetAndon(receptacleLocation, currentUserLogin);
                 }
                 else if (andonType == 3) {
                          andon = new SuspectedTheftAndon(receptacleLocation, currentUserLogin);
                 }
        }

        System.out.println("Andon successfully created.");

        return andon;
    }

    // method below removes the completed count location from the list of pending SBC counts, flags the receptacle as
    // NOT needing a SBC count, and updates the counter's completed counts and last location
    private static void updateFieldsAfterCountCompleted(String currentUserLogin, InventoryReceptacle receptacle) {

        // get name of the FC that the current user selected after logging in
        String currentFCName = Main.getCurrentFCName();

        ArrayList<String> pendingSimpleBinCounts = SimulationTool.getPendingSimpleBinCounts(currentFCName);
        pendingSimpleBinCounts.remove(receptacle.getLocation());

        receptacle.setNeedsSimpleBinCount(false);

        // use current user's login to get reference to Employee object and update its fields
        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);
        currentUser.increaseCompletedSBC_Counts();
        currentUser.setLastCompletedLocation(receptacle.getLocation());
    }

    private static void updateFieldsAfterAndonConfirmed(String currentUserLogin,InventoryReceptacle receptacle,
                                                        Andon andon) {

        // now that we have a reference to the receptacle, update its fields
        receptacle.setAndon(andon);
        receptacle.setNeedsSimpleBinCount(false);

        // if a counter creates an andon while counting a receptacle's inventory, remove that location from the list
        // of pending SBC counts...after a problem solver fixes the issue, the location should then be added to a list
        // of locations that need to be cycle counted...clearing andons and cycle counts are beyond the program's scope
        ArrayList<String> pendingSimpleBinCounts = SimulationTool.getPendingSimpleBinCounts(Main.getCurrentFCName());
        pendingSimpleBinCounts.remove(receptacle.getLocation());

        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);
        currentUser.increaseCompletedSBC_Counts();
        currentUser.setLastCompletedLocation(receptacle.getLocation());
        currentUser.addToListOfAndonsCreated(receptacle.getLocation());
    }

    private static void updateFieldsAfterLocationSkip(String currentUserLogin, InventoryReceptacle receptacle) {

        Employee currentUser = SBCSimUtils.getEmployeeFromListOfAllSortableFCEmployees(currentUserLogin);
        currentUser.addToListOfSkippedLocations(receptacle.getLocation());
    }

    private static void updateFieldsAfterProcessExit(String currentUserLogin, InventoryReceptacle receptacle) {
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Perhaps add another part (screen) to the SBC process, the "Scan Nearest Location" screen.

- Perhaps get rid of the "not prompting the counter to scan into a location that they have already skipped" feature.

- Ideally, and in real life, SBC counts probably shouldn't be added "live" in real time. Instead, batches of counts
  should be readily available and assigned to various counters as requested. Imagine 50+ SBC counters querying the
  database at once for SBC counts! This has the potential to cause lots of issues. Perhaps create and "drop" new batches
  every 15 minutes or so? Also, batches should perhaps MOSTLY be created based on inbound/outbound process results, and
  currently assigned batches should be freed if a counter has been inactive for 15 minutes+. I might add this feature to
  the program later.

- Below, I have the sbcProblemHandling method and the showSBCProblemMenu1 method coded in an alternate way. I have
  my loop that prompts the counter to enter 1,2,3, or 4 in the showSBCProblemMenu1 method. Also, I make use of Exception
  handling. Furthermore, because userInput is declared as a Integer var and the value is read-in using token-based input
  []4[]111 is valid, and will be read-in and the value of 4 will be assigned to userInput.

- Two additional alternate ways of coding the two methods below are:
  I can use a String userInput var, and then (perhaps after trimming whitespace chars) use Exception handling in case
  the counter entered non-numeric chars. Or I can assign the input to a String var and check it for the correct length,
  and check it to make sure that all chars are digits IN THE showSBCProblemMenu methods themselves.

    private static boolean sbcProblemHandling(String currentUserLogin, InventoryReceptacle receptacle,
                                              int sbcProcessScreen) {

        Integer userInput = null;
        boolean andonConfirmed = false;

        if (sbcProcessScreen == 1) {
            userInput = ICQA.showSBCProblemMenu1();
        }
        else {
              userInput = ICQA.showSbcProblemMenu2();
        }

        if (userInput == 1 || userInput == 2 || userInput == 3) {
            andonConfirmed = ICQA.promptUserToConfirmAndonSBC();
            if (andonConfirmed) {
                Andon andon = ICQA.createAndon(currentUserLogin, receptacle, sbcProcessScreen, userInput);
                ICQA.updateFieldsAfterAndonConfirmed(currentUserLogin, andon);
            }
        }
        return andonConfirmed;
    }

    private static int showSBCProblemMenu1() {

        Integer userInput = null;
        boolean continueInput = true;

        Scanner input = new Scanner(System.in);

        do {
            try {
                System.out.println();
                System.out.println("****************");
                System.out.println("SBC Problem Menu");
                System.out.println("****************");
                System.out.println();
                System.out.println("Make a selection.");
                System.out.println();
                System.out.println("1. Bin does Not Exist");
                System.out.println("2. No Scannable Bin Label");
                System.out.println("3. Unsafe to Count");
                System.out.println("4. Go Back");
                System.out.println();
                System.out.print("Enter choice: ");

                userInput = input.nextInt();
                input.nextLine();

                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4) {
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

        return userInput;
    }

 */