import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

class Test {

    // Test.ont2 is hardcoded because this program doesn't have persistence.
    private static ONT2 ont2;

    static void start(){

        Test.initializeONT2();

        Test.showMenu();
    }

    private static void initializeONT2() {

        Test.ont2 = new ONT2();

        ONT2Initializer.initializeONT2Fields(Test.ont2);
    }

    private static void showMenu() {

        boolean continueInput = true;

        Scanner input = new Scanner(System.in);

        do {
            try {
                  System.out.println();
                  System.out.println("**************");
                  System.out.println("Test Mode Menu");
                  System.out.println("**************");
                  System.out.println();
                  System.out.println("-----------------------------------------------");
                  System.out.println("1. Print Number of Receptacles in ONT2");
                  System.out.println("-----------------------------------------------");
                  System.out.println("2. Print P1A-100s Location and Pick-Path ID");
                  System.out.println("3. Print P1A-200s Location and Pick-Path ID");
                  System.out.println("4. Print P1A-100s/200s Ordered by Pick-Path ID");
                  System.out.println("-----------------------------------------------");
                  System.out.println("5. Print P1A-300s Location and Pick-Path ID");
                  System.out.println("6. Print P1A-400s Location and Pick-Path ID");
                  System.out.println("7. Print P1A-300s/400s Ordered by Pick-Path ID");
                  System.out.println("-----------------------------------------------");
                  System.out.println("8. Print P1A-500s Location and Pick-Path ID");
                  System.out.println("9. Print P1A-600s Location and Pick-Path ID");
                  System.out.println("10. Print P1A-500s/600s Ordered by Pick-Path ID");
                  System.out.println("-----------------------------------------------");
                  System.out.println("11. Print P1A-700s Location and Pick-Path ID");
                  System.out.println("12. Print P1A-800s Location and Pick-Path ID");
                  System.out.println("13. Print P1A-700s/800s Ordered by Pick-Path ID");
                  System.out.println("-----------------------------------------------");
                  System.out.println("14. Print Key Pick-Path Values");
                  System.out.println("-----------------------------------------------");
                  System.out.println("15. Test Pick-Path ID Assignment");
                  System.out.println("-----------------------------------------------");
                  System.out.println("16. Return To Department/Process Selection Menu");
                  System.out.println("-----------------------------------------------");
                  System.out.println();
                  System.out.print("> Enter choice: ");

                  int userInput = input.nextInt();
                  input.nextLine();

                  if (userInput == 1) {
                      Test.printNumberOfReceptaclesInONT2();
                  }
                  else if (userInput == 2) {
                           Test.printP1A100sLocationAndPickID();
                  }
                  else if (userInput == 3) {
                           Test.printP1A200sLocationAndPickID();
                  }
                  else if (userInput == 4) {
                           Test.printP1A100s200sByPickPathID();
                  }
                  else if (userInput == 5) {
                           Test.printP1A300sLocationAndPickID();
                  }
                  else if (userInput == 6) {
                           Test.printP1A400sLocationAndPickID();
                  }
                  else if (userInput == 7) {
                           Test.printP1A300s400sByPickPathID();
                  }
                  else if (userInput == 8) {
                           Test.printP1A500sLocationAndPickID();
                  }
                  else if (userInput == 9) {
                           Test.printP1A600sLocationAndPickID();
                  }
                  else if (userInput == 10) {
                           Test.printP1A500s600sByPickPathID();
                  }
                  else if (userInput == 11) {
                           Test.printP1A700sLocationAndPickID();
                  }
                  else if (userInput == 12) {
                           Test.printP1A800sLocationAndPickID();
                  }
                  else if (userInput == 13) {
                           Test.printP1A700s800sByPickPathID();
                  }
                  else if (userInput == 14) {
                           Test.printKeyPickPathValuesP1A();
                  }
                  else if (userInput == 15) {
                           Test.testPickPathIDAssignment();
                  }
                  else if (userInput == 16) {
                           continueInput = false;
                  }
            } catch(InputMismatchException ex) {
                    input.nextLine();
                    System.out.println();
                    System.out.println("Invalid input. Try again.");
            }
        } while (continueInput) ;
    }

    private static void printNumberOfReceptaclesInONT2() {

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("Test that prints the total number of InventoryReceptacles in ONT2.");
        System.out.println("The number should be 76,384 because I have only created 1 floor (P1A).");
        System.out.println("(458,304 total receptacles in FC divided by 6 floors = 76,384 per floor)");
        System.out.println("************************************************************************");
        System.out.println();

        System.out.println(Test.ont2.getNumberOfInventoryReceptaclesInFC());

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A100sLocationAndPickID() {

        System.out.println();
        System.out.println("**********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-100s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-100 should have a pickPathID = 1");
        System.out.println("P1A-225-G110 should have a pickPathID = 18,957");
        System.out.println("**********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(0), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A200sLocationAndPickID() {

        System.out.println();
        System.out.println("**********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-200s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-200 should have a pickPathID = 155");
        System.out.println("P1A-225-G210 should have a pickPathID = 18,803");
        System.out.println("**********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(1), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A100s200sByPickPathID() {

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Test that displays ONT2's P1A-100s and");
        System.out.println("P1A-200s receptacles ordered by their");
        System.out.println("pickPathID (lowest -> highest).");
        System.out.println("**************************************");

        // capacity of 20,000 because every row, such as P1A-100s, P1A-200s, etc.
        // should contain a maximum of 10_000 receptacles
        ArrayList<InventoryReceptacle> p1A100s200sReceptacles = new ArrayList<>(20_000);

        p1A100s200sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(0));
        p1A100s200sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(1));

        SBCSimUtils.orderReceptaclesByPickID(p1A100s200sReceptacles);

        Test.printLocationAndPickPathID(p1A100s200sReceptacles, false);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A300sLocationAndPickID() {

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ont2's P1A-300s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-300 should have a pickPathID = 38,192");
        System.out.println("P1A-225-G-310 should have a pickPathID = 19,236");
        System.out.println("***********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(2), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A400sLocationAndPickID() {

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-400s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-400 should have a pickPathID = 38,038");
        System.out.println("P1A-225-G-410 should have a pickPathID = 19,390");
        System.out.println("***********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(3), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A300s400sByPickPathID() {

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Test that displays ONT2's P1A-300s and");
        System.out.println("P1A-400s receptacles ordered by their");
        System.out.println("pickPathID (lowest -> highest).");
        System.out.println("**************************************");

        ArrayList<InventoryReceptacle> p1A300s400sReceptacles = new ArrayList<>(20_000);

        p1A300s400sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(2));
        p1A300s400sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(3));

        SBCSimUtils.orderReceptaclesByPickID(p1A300s400sReceptacles);

        Test.printLocationAndPickPathID(p1A300s400sReceptacles, false);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A500sLocationAndPickID() {

        System.out.println();
        System.out.println("*************************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-500s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-500 should have a pickPathID = 38,193");
        System.out.println("P1A-225-G-510 should have a pickPathID = 57,149");
        System.out.println("*************************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(4), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A600sLocationAndPickID() {

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-600s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-600 should have a pickPathID = 38,347");
        System.out.println("P1A-225-G-610 should have a pickPathID = 56,995");
        System.out.println("***********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(5), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A500s600sByPickPathID() {

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Test that displays ONT2's P1A-500s and");
        System.out.println("P1A-600s receptacles ordered by their");
        System.out.println("pickPathID (lowest -> highest).");
        System.out.println("**************************************");

        ArrayList<InventoryReceptacle> p1A500s600sReceptacles = new ArrayList<>(20_000);

        p1A500s600sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(4));
        p1A500s600sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(5));

        SBCSimUtils.orderReceptaclesByPickID(p1A500s600sReceptacles);

        Test.printLocationAndPickPathID(p1A500s600sReceptacles, false);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A700sLocationAndPickID() {

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-700s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-700 should have a pickPathID = 76,384");
        System.out.println("P1A-225-G-710 should have a pickPathID = 57,428");
        System.out.println("***********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(6), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A800sLocationAndPickID() {

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("Test that displays the location and pickPathID");
        System.out.println("of ONT2's P1A-800s receptacles.");
        System.out.println();
        System.out.println("P1A-102-A-800 should have a pickPathID = 76,230");
        System.out.println("P1A-225-G-810 should have a pickPathID = 57,582");
        System.out.println("***********************************************");

        Test.printLocationAndPickPathID(Test.ont2.getP1AReceptaclesIndexedByRows().get(7), true);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printP1A700s800sByPickPathID() {

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Test that displays ONT2's P1A-700s and");
        System.out.println("P1A-800s receptacles ordered by their");
        System.out.println("pickPathID (lowest -> highest)");
        System.out.println("**************************************");

        ArrayList<InventoryReceptacle> p1A700s800sReceptacles = new ArrayList<>(20_000);

        p1A700s800sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(6));
        p1A700s800sReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(7));

        SBCSimUtils.orderReceptaclesByPickID(p1A700s800sReceptacles);

        Test.printLocationAndPickPathID(p1A700s800sReceptacles, false);

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void printLocationAndPickPathID(ArrayList<InventoryReceptacle> listOfReceptacles,
                                                   boolean separateByAisle) {

        System.out.println();

        for (int i = 0; i < listOfReceptacles.size(); i++) {

            if ((i + 1) % 11 == 0) {
                System.out.println(listOfReceptacles.get(i).getLocation() + " pickPathID: " + listOfReceptacles.get(i).getPickPathID());
            }
            else {
                  System.out.print(listOfReceptacles.get(i).getLocation() + " pickPathID: " + listOfReceptacles.get(i).getPickPathID() + " | ");
            }

            if (separateByAisle && (i + 1) % 77 == 0 && i + 1 != listOfReceptacles.size()) {
                System.out.println();
            }
        }
    }

    private static void printKeyPickPathValuesP1A() {

        System.out.println();
        System.out.println("**************************************************************");
        System.out.println("Test that displays the pickPathID of key ONT2 P1A receptacles.");
        System.out.println("**************************************************************");
        System.out.println();


        // declare local list to hold P1A receptacles because I don't want Test.ont2's list of P1A receptacles to be
        // ordered by pick-path ID
        ArrayList<InventoryReceptacle> p1AReceptacles = new ArrayList<>(80_000);

        for (int i = 0; i < Test.ont2.getP1AReceptaclesIndexedByRows().size(); i++) {
             p1AReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(i));
        }

        // It's necessary to order the list of P1A receptacles by pickPathID because ONT2Initializer doesn't do it.
        SBCSimUtils.orderReceptaclesByPickID(p1AReceptacles);

        System.out.println("P1A-102-A-100 should have a pickPathID of: 1");
        System.out.println(p1AReceptacles.get(0).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(0).getPickPathID());

        System.out.println();

        System.out.println("P1A-102-A-200 should have a pickPathID of: 155");
        System.out.println(p1AReceptacles.get(154).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(154).getPickPathID());

        System.out.println();

        System.out.println("P1A-104-A-210 should have a pickPathID of: 309");
        System.out.println(p1AReceptacles.get(308).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(308).getPickPathID());

        System.out.println();

        System.out.println("P1A-105-A-100 should have a pickPathID of: 616");
        System.out.println(p1AReceptacles.get(615).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(615).getPickPathID());

        System.out.println();

        System.out.println("P1A-106-A-100 should have a pickPathID of: 617");
        System.out.println(p1AReceptacles.get(616).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(616).getPickPathID());

        System.out.println();

        System.out.println("P1A-106-A-200 should have a pickPathID of: 771");
        System.out.println(p1AReceptacles.get(770).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(770).getPickPathID());

        System.out.println();

        System.out.println("P1A-108-A-210 should have a pickPathID of: 925");
        System.out.println(p1AReceptacles.get(924).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(924).getPickPathID());

        System.out.println();

        System.out.println("P1A-109-A-100 should have a pickPathID of: 1,232");
        System.out.println(p1AReceptacles.get(1231).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(1231).getPickPathID());

        System.out.println();

        System.out.println("P1A-110-A-100 should have a pickPathID of: 1,233");
        System.out.println(p1AReceptacles.get(1232).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(1232).getPickPathID());

        System.out.println();

        System.out.println("P1A-110-A-200 should have a pickPathID of: 1,387");
        System.out.println(p1AReceptacles.get(1386).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(1386).getPickPathID());

        System.out.println();

        System.out.println("P1A-112-A-210 should have a pickPathID of: 1,541");
        System.out.println(p1AReceptacles.get(1540).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(1540).getPickPathID());

        System.out.println();

        System.out.println("P1A-113-A-100 should have a pickPathID of: 1,848");
        System.out.println(p1AReceptacles.get(1847).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(1847).getPickPathID());

        System.out.println();

        System.out.println("P1A-225-G-210 should have a pickPathID of: 18,803");
        System.out.println(p1AReceptacles.get(18802).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(18802).getPickPathID());

        System.out.println();

        System.out.println("P1A-225-A-100 should have a pickPathID of: 19,096");
        System.out.println(p1AReceptacles.get(19095).getLocation() + " actual pickPathID: " +
                           p1AReceptacles.get(19095).getPickPathID());

        SBCSimUtils.showEnter1ToGoBackScreen();
    }

    private static void testPickPathIDAssignment() {

        System.out.println();
        System.out.println("*****************************************************************");
        System.out.println("Test that checks an ArrayList of P1A receptacles to ensure that");
        System.out.println("every pickPathID value from 1 <--> 76,384 is present in the list,");
        System.out.println("in order from 1 --> 76,384.");
        System.out.println("*****************************************************************");

        // Again, I declare a local list of p1AReceptacles and then add Test.ont2's p1A receptacles to it because I
        // don't want Test.ont2's list to be ordered by pick-path ID.
        ArrayList<InventoryReceptacle> p1AReceptacles = new ArrayList<>(80_000);

        for(int i = 0; i < Test.ont2.getP1AReceptaclesIndexedByRows().size(); i++) {
            p1AReceptacles.addAll(Test.ont2.getP1AReceptaclesIndexedByRows().get(i));
        }

        SBCSimUtils.orderReceptaclesByPickID(p1AReceptacles);

        // create array with length that matches the # of P1A receptacles (76,384 total receptacles in floor P1A)
        int[] numberArray = new int[76_384];

        // add int values to the array, from 1 --> 76,384 in order
        // numberArray[0] = 1 and numberArray[76,383] = 76,384
        for (int i = 0, j = 1; i < numberArray.length; i++, j++) {
             numberArray[i] = j;
        }

        int numberOfMismatches = 0;

        // Because local list p1AReceptacles is ordered by pick-path ID and there are 76,384 receptacles in floor P1A,
        // the pick-path ids of the receptacles should be in order and basically equal the values in the array above.
        for (int i = 0; i < numberArray.length; i++) {
             if (p1AReceptacles.get(i).getPickPathID() != numberArray[i]) {
                 numberOfMismatches++;
                 System.out.println();
                 System.out.println("ERROR!");
                 System.out.println("------");
                 System.out.println("Inventory Receptacle: " + p1AReceptacles.get(i).getLocation());
                 System.out.println("Expected pickPathID: " + numberArray[i]);
                 System.out.println("Actual pickPathID: " + p1AReceptacles.get(i).getPickPathID());
             }
        }
        if (numberOfMismatches == 0) {
            System.out.println();
            System.out.println("No pickPathID mismatches found.");
        }

        SBCSimUtils.showEnter1ToGoBackScreen();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- finish adding key bins to method printKeyPickPathValuesP1A

- organize the following information:

  P1A-102-A-100 should have a pickPathID of: 1
  This receptacle is the start of floor P1A's pick-path.
  The pick-path moves east -> west.

  P1A-102-A-200 should have a pickPathID of: 155
  This receptacle comes after all P1A-102-100s and P1A-103-100s receptacles.
  Each wall, such as the P1A-102-100s wall, has 77 receptacles,
  thus 77 + 77 = 154 receptacles BEFORE P1A-102-A-200,
  so P1A-102-A-100 should be the 155th receptacle in the floor's pick-path.

  P1A-104-A-210 should have a pickPathID of: 309
  (155 + 154 = 309)

  P1A-105-A-100 should have a pickPathID of: 616
  This is the LAST receptacle before N changes to (N + 616).
  Because there are 616 receptacles/iteration, and this is the last
  receptacle in the iteration, we know that it must have a pickPathID = 616

  P1A-106-A-100 should have a pickPathID of: 617

  P1A-106-A-200 should have a pickPathID of: 771

  P1A-108-A-210 should have a pickpathID of: 925

  P1A-109-A-100 should have a pickPathID of: 1,232

  P1A-110-A-100 should have a pickPathID of: 1,233

  P1A-110-A-200 should have a pickPathID of: 1,387

  P1A-112-A-210 should have a pickPathID of: 1,541

  P1A-113-A-100 should have a pickPathID of: 1,848

  P1A-114-A-100 should have a pickPathID of: 1,849

  P1A-225-G-210 should have a pickPathID of: 18,803

  P1A-225-A-200 should have a pickPathID of: 18,942

  P1A-225-A-100 should have a pickPathID of: 19,096
  The pick path stops moving east -> west after this receptacle.

  P1A-225-A-300 should have a pickPathID of: 19,097
  The pick path starts moving west -> east at this receptacle.

  P1A-102-A-300 should have a pickPathID of: 38,192
  This is the last receptacle in the 300s and 400s pick-path.
  The next receptacle is P1A-102-A-500, which should have a pickPathID of 38,193.

*/