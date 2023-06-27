import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// Make this class accessible only via password. In real life, this is a WEBAPP that displays info, data, and metrics.
// It is accessed almost exclusively by Problems Solvers, Process Assistants, and Leadership via web-browser.
// I don't recall if it allows the user to update anything. However, the information accessible via Mastermind is very
// detailed, which is why, like in real life, this information should require a password to access. Scroll down to the
// bottom of this class for a little more information on Mastermind, and how it relates to FCResearch (another webapp)
// and BinTool (a powerful, console-based desktop app used to manage a bin's inventory).
public class Mastermind {

    static void mainMenu(String currentUserLogin) {

        boolean continueInput = true;

        do {
            Mastermind.showMainMenu();

            String userInput = Mastermind.promptUserToEnterChoice();

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
            // employee the option to come back to this menu once they are done viewing whatever data they selected
            if (userInputParsed == 1) {
                Mastermind.icqaMetrics();
            }
            else if (userInputParsed == 2) {
                     Mastermind.andonMetrics();
            }
            else if (userInputParsed == 3) {
                     continueInput = false;
            }
        } while (continueInput);
    }

    private static void showMainMenu() {

        System.out.println();
        System.out.println("**********");
        System.out.println("Mastermind");
        System.out.println("**********");
        System.out.println();
        System.out.println("1. ICQA Metrics");
        System.out.println("2. Andon Metrics");
        System.out.println("3. Return to Department/Process Selection Menu");
    }

    private static void icqaMetrics() {

        boolean continueInput = true;

        do {
            Mastermind.showICQAMetricsMenu();

            String userInput = Mastermind.promptUserToEnterChoice();

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
            // employee the option to come back to this menu once they are done viewing whatever data they selected
            if (userInputParsed == 1) {
                Mastermind.showSBCMetrics();
            }
            else if (userInputParsed == 2) {
                     Mastermind.showCCMetrics();
            }
            else if (userInputParsed == 3) {
                     Mastermind.generateBinSkipsReport();
            }
            else if (userInputParsed == 4) {
                     continueInput = false;
            }
            else {
                  System.out.println();
                  System.out.println("Invalid input.");
            }
        } while (continueInput);
    }

    private static void showICQAMetricsMenu() {

        System.out.println();
        System.out.println("*****************");
        System.out.println("ICQA Metrics Menu");
        System.out.println("*****************");
        System.out.println();
        System.out.println("1. View SBC Metrics");
        System.out.println("2. View CC Metrics");
        System.out.println("3. Generate Bin Skips Report");
        System.out.println("4. Go Back");
    }

    private static void showSBCMetrics() {

        String currentFCName = Main.getCurrentFCName();

        if (currentFCName.equalsIgnoreCase("ONT2")) {
            Mastermind.ont2SBCMetrics();
        }
    }

    // IMPORTANT: numOfPendingSimpleBinCountsP1A != SimulationTool.ont2PendingSimpleBinCounts.size()
    //
    // The former value is obtained by traversing every P1A bin and adding-up the total number of bins that have their
    // boolean needsSimpleBinCount == true. The latter value is the size of the batch of receptacles that is ready to be
    // counted. For example, imagine a scenario where ont2PendingSimpleBinCounts.size() is 5 at the start of the
    // program. The counter decides to skip all 5 bins in the batch. This causes the receptacles to be removed from the
    // batch, BUT they are still flagged as needing a SBC count. I must admit that the identifier
    // numOfPendingSimpleBinCountsP1A should probably be changed then, because it leads one to believe that they are
    // already batched and ready to count, which is not the case. However, such distinctions are beyond the scope of
    // this program for now.
    private static void ont2SBCMetrics() {

        SortableFC ont2 = Main.getSortableFC("ONT2");

        // index 0 = P1A-100s, index 1 = P1A-200s,...,index 7 = P1A-800s
        ArrayList<ArrayList<InventoryReceptacle>> p1AReceptaclesIndexedByRows =
                                                  ((ONT2) ont2).getP1AReceptaclesIndexedByRows();

        int numOfPendingSimpleBinCountsP1A100sAnd200s = 0;
        int numOfPendingSimpleBinCountsP1A300sAnd400s = 0;
        int numOfPendingSimpleBinCountsP1A500sAnd600s = 0;
        int numOfPendingSimpleBinCountsP1A700sAnd800s = 0;
        int numOfPendingSimpleBinCountsP1A = 0;

        for (int i = 0; i < p1AReceptaclesIndexedByRows.size(); i++) {
             ArrayList<InventoryReceptacle> rowOfReceptacles = p1AReceptaclesIndexedByRows.get(i);
             for (int j = 0; j < rowOfReceptacles.size(); j++) {
                  if (rowOfReceptacles.get(j).getNeedsSimpleBinCount()) {
                      numOfPendingSimpleBinCountsP1A++;
                      if (i == 0 || i == 1) {
                          numOfPendingSimpleBinCountsP1A100sAnd200s++;
                      }
                      else if (i == 2 || i == 3) {
                               numOfPendingSimpleBinCountsP1A300sAnd400s++;
                      }
                      else if (i == 4 || i == 5) {
                          numOfPendingSimpleBinCountsP1A500sAnd600s++;
                      }
                      else if (i == 6 || i == 7) {
                          numOfPendingSimpleBinCountsP1A700sAnd800s++;
                      }
                  }
             }
        }

        System.out.println();
        System.out.println("*******************");
        System.out.println("ONT2 SBC Overview");
        System.out.println("*******************");
        System.out.println();
        System.out.println("Pending Counts");
        System.out.println("--------------");
        System.out.println();
        System.out.println("P1A");
        System.out.println("---");
        System.out.println("100s & 200s: " + numOfPendingSimpleBinCountsP1A100sAnd200s);
        System.out.println("300s & 400s: " + numOfPendingSimpleBinCountsP1A300sAnd400s);
        System.out.println("500s & 600s: " + numOfPendingSimpleBinCountsP1A500sAnd600s);
        System.out.println("700s & 800s: " + numOfPendingSimpleBinCountsP1A700sAnd800s);
        System.out.println();
        System.out.println("Total Pending: " + numOfPendingSimpleBinCountsP1A);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void showCCMetrics() {

        String currentFCName = Main.getCurrentFCName();

        if (currentFCName.equalsIgnoreCase("ONT2")) {
            Mastermind.ont2CCMetrics();
        }
    }

    private static void ont2CCMetrics() {

        SortableFC ont2 = Main.getSortableFC("ONT2");

        // index 0 = P1A-100s, index 1 = P1A-200s,...,index 7 = P1A-800s
        ArrayList<ArrayList<InventoryReceptacle>> p1AReceptaclesIndexedByRows =
                ((ONT2) ont2).getP1AReceptaclesIndexedByRows();

        int numOfPendingCycleCountsP1A100sAnd200s = 0;
        int numOfPendingCycleCountsP1A300sAnd400s = 0;
        int numOfPendingCycleCountsP1A500sAnd600s = 0;
        int numOfPendingCycleCountsP1A700sAnd800s = 0;
        int numOfPendingCycleCountsP1A = 0;

        for (int i = 0; i < p1AReceptaclesIndexedByRows.size(); i++) {
            ArrayList<InventoryReceptacle> rowOfReceptacles = p1AReceptaclesIndexedByRows.get(i);
            for (int j = 0; j < rowOfReceptacles.size(); j++) {
                if (rowOfReceptacles.get(j).getNeedsCycleCount()) {
                    numOfPendingCycleCountsP1A++;
                    if (i == 0 || i == 1) {
                        numOfPendingCycleCountsP1A100sAnd200s++;
                    }
                    else if (i == 2 || i == 3) {
                        numOfPendingCycleCountsP1A300sAnd400s++;
                    }
                    else if (i == 4 || i == 5) {
                        numOfPendingCycleCountsP1A500sAnd600s++;
                    }
                    else if (i == 6 || i == 7) {
                        numOfPendingCycleCountsP1A700sAnd800s++;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("*************************");
        System.out.println("ONT2 Cycle Count Overview");
        System.out.println("*************************");
        System.out.println();
        System.out.println("Pending Counts");
        System.out.println("--------------");
        System.out.println();
        System.out.println("P1A");
        System.out.println("---");
        System.out.println("100s & 200s: " + numOfPendingCycleCountsP1A100sAnd200s);
        System.out.println("300s & 400s: " + numOfPendingCycleCountsP1A300sAnd400s);
        System.out.println("500s & 600s: " + numOfPendingCycleCountsP1A500sAnd600s);
        System.out.println("700s & 800s: " + numOfPendingCycleCountsP1A700sAnd800s);
        System.out.println();
        System.out.println("Total Pending: " + numOfPendingCycleCountsP1A);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    static void generateBinSkipsReport() {

        String currentFCName = Main.getCurrentFCName();
        SortableFC sortableFC = Main.getSortableFC(currentFCName);
        ArrayList<Employee> employees = sortableFC.getListOfEmployees();

        System.out.println();
        System.out.println("****************");
        System.out.println("Bin Skips Report");
        System.out.println("****************");
        System.out.println();
        System.out.println("Generated on: " + new Date());
        System.out.println();
        System.out.println("Employees with More than 5 Bin Skips");
        System.out.println("------------------------------------");

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getListOfSkippedLocations().size() > 5) {
                System.out.println(employees.get(i).getLogin());
            }
        }

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void andonMetrics() {

        boolean continueInput = true;

        do {
            Mastermind.showAndonMetricsMenu();

            String userInput = Mastermind.promptUserToEnterChoice();

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
            // employee the option to come back to this menu once they are done viewing whatever data they selected
            if (userInputParsed == 1) {
                Mastermind.showAndonOverview();
            }
            else if (userInputParsed == 2) {
                continueInput = false;
            } else {
                System.out.println();
                System.out.println("Invalid input.");
            }
        } while (continueInput);
    }

    private static void showAndonMetricsMenu() {

        System.out.println();
        System.out.println("******************");
        System.out.println("Andon Metrics Menu");
        System.out.println("******************");
        System.out.println();
        System.out.println("1. Andon Overview");
        System.out.println("2. Go Back");
    }

    static void showAndonOverview() {

        String currentFCName = Main.getCurrentFCName();

        if (currentFCName.equalsIgnoreCase("ONT2")) {
            Mastermind.ont2AndonOverview();
        }
    }

    private static void ont2AndonOverview() {

        SortableFC ont2 = Main.getSortableFC("ONT2");

        // index 0 = P1A-100s, index 1 = P1A-200s,...,index 7 = P1A-800s
        ArrayList<ArrayList<InventoryReceptacle>> p1AReceptaclesIndexedByRows =
                ((ONT2) ont2).getP1AReceptaclesIndexedByRows();

        ///// GET P1A-100S AND 200S RECEPTACLES, ADD THEM TO LIST AND CHECK FOR ANDON TO UPDATE COUNT /////

        int numOfPendingAndonsP1A100sAnd200s = 0;

        ArrayList<InventoryReceptacle> p1A100sAnd200s = new ArrayList<>(20_000);
        p1A100sAnd200s.addAll(p1AReceptaclesIndexedByRows.get(0));
        p1A100sAnd200s.addAll(p1AReceptaclesIndexedByRows.get(1));
        p1A100sAnd200s.trimToSize();

        for (int i = 0; i < p1A100sAnd200s.size(); i++) {
            if (p1A100sAnd200s.get(i).hasAndon()) {
                numOfPendingAndonsP1A100sAnd200s++;
            }
        }

        p1A100sAnd200s = null;

        ///// GET P1A-300S AND 400S RECEPTACLES, ADD THEM TO LIST AND CHECK FOR ANDON TO UPDATE COUNT /////

        int numOfPendingAndonsP1A300sAnd400s = 0;

        ArrayList<InventoryReceptacle> p1A300sAnd400s = new ArrayList<>(20_000);
        p1A300sAnd400s.addAll(p1AReceptaclesIndexedByRows.get(2));
        p1A300sAnd400s.addAll(p1AReceptaclesIndexedByRows.get(3));
        p1A300sAnd400s.trimToSize();

        for (int i = 0; i < p1A300sAnd400s.size(); i++) {
            if (p1A300sAnd400s.get(i).hasAndon()) {
                numOfPendingAndonsP1A300sAnd400s++;
            }
        }

        p1A300sAnd400s = null;

        ///// GET P1A-500S AND 600S RECEPTACLES, ADD THEM TO LIST AND CHECK FOR ANDON TO UPDATE COUNT /////

        int numOfPendingAndonsP1A500sAnd600s = 0;

        ArrayList<InventoryReceptacle> p1A500sAnd600s = new ArrayList<>(20_000);
        p1A500sAnd600s.addAll(p1AReceptaclesIndexedByRows.get(4));
        p1A500sAnd600s.addAll(p1AReceptaclesIndexedByRows.get(5));
        p1A500sAnd600s.trimToSize();

        for (int i = 0; i < p1A500sAnd600s.size(); i++) {
            if (p1A500sAnd600s.get(i).hasAndon()) {
                numOfPendingAndonsP1A500sAnd600s++;
            }
        }

        p1A500sAnd600s = null;

        ///// GET P1A-700S AND 800S RECEPTACLES, ADD THEM TO LIST AND CHECK FOR ANDON TO UPDATE COUNT /////

        int numOfPendingAndonsP1A700sAnd800s = 0;

        ArrayList<InventoryReceptacle> p1A700sAnd800s = new ArrayList<>(20_000);
        p1A700sAnd800s.addAll(p1AReceptaclesIndexedByRows.get(6));
        p1A700sAnd800s.addAll(p1AReceptaclesIndexedByRows.get(7));
        p1A700sAnd800s.trimToSize();

        for (int i = 0; i < p1A700sAnd800s.size(); i++) {
            if (p1A700sAnd800s.get(i).hasAndon()) {
                numOfPendingAndonsP1A700sAnd800s++;
            }
        }

        p1A700sAnd800s = null;

        ///// ADD UP TOTAL NUMBER OF ANDONS IN ROWS, ASSIGN VALUE TO VAR, DISPLAY COUNT INFORMATION /////

        int totalNumOfAndonsP1A = numOfPendingAndonsP1A100sAnd200s + numOfPendingAndonsP1A300sAnd400s +
                numOfPendingAndonsP1A500sAnd600s + numOfPendingAndonsP1A700sAnd800s;

        System.out.println();
        System.out.println("*******************");
        System.out.println("ONT2 Andon Overview");
        System.out.println("*******************");
        System.out.println();
        System.out.println("P1A");
        System.out.println("---");
        System.out.println("Total Pending: " + totalNumOfAndonsP1A);
        System.out.println();
        System.out.println("100s & 200s: " + numOfPendingAndonsP1A100sAnd200s);
        System.out.println("300s & 400s: " + numOfPendingAndonsP1A300sAnd400s);
        System.out.println("500s & 600s: " + numOfPendingAndonsP1A500sAnd600s);
        System.out.println("700s & 800s: " + numOfPendingAndonsP1A700sAnd800s);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static String promptUserToEnterChoice() {

        String userInput;

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.print("> Enter choice: ");

        userInput = input.nextLine();
        userInput = userInput.trim();

        return userInput;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Related to Mastermind:
  One could see any employee's work-process data, e.g., enter a picker's login and see what bin they are currently
  scanned into. Because Mastermind is web-based, I believe that I could even look-up other FC's data simply by selecting
  the FC from a drop-down list.

- Related to FC Research:
  FCResearch was another webapp, and if I remember correctly, it focused more on inventory locations, their inventory,
  and their history. For example, I could enter "Harry Potter and the Sorcerer's Stone" into a search bar and
  FCResearch would then give me a page with all inventory locations that contain the book, as well as other info.
  I could also enter an inventory location and FCResearch would then give me its current virtual inventory information and
  history. I could see who last counted the bin, who last stowed into the bin (as well as what ASIN(s)
  they stowed), and who last picked from the bin (as well as what ASIN(s) they picked).

*/