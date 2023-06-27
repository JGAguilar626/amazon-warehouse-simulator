import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public final class ONT2Initializer {

    static void initializeONT2Fields(ONT2 ont2) {

        ///// CREATE LOCAL LIST OF EMPLOYEES, PASS ITS REFERENCE TO ont2 FIELD /////

        ArrayList<Employee> listOfEmployees = new ArrayList<>(5_000);

        // pass the local list to a method that creates 4 employees and adds them to the list and trims the list
        ONT2Initializer.createAndAddEmployeesToList(listOfEmployees);

        ont2.setListOfEmployees(listOfEmployees);

        ///// CREATE LOCAL LIST OF LOGINS, ADD THE LOGINS OF ALL THE EMPLOYEES IN THE LIST CREATED ABOVE, PASS /////
        ///// REFERENCE TO ont2's listOfLogins FIELD /////

        ArrayList<String> listOfLogins = new ArrayList<>(listOfEmployees.size());

        for (int i = 0; i < listOfEmployees.size(); i++) {
             listOfLogins.add(listOfEmployees.get(i).getLogin());
        }

        ont2.setListOfLoginsWithFCAccess(listOfLogins);

            ///// CREATE LOCAL LIST OF FLOOR P1A INVENTORY RECEPTACLES, PASS ITS REFERENCE TO ont2 FIELD /////

        // In this version of my program, I first create the list of ALL P1A receptacles as opposed to creating P1A-100s
        // P1A-200s, etc. AND THEN a list(s) with all receptacles. I do this because this version of my program DOES NOT
        // include some important algos (for intellectual property reasons).
        ArrayList<InventoryReceptacle> p1AInventoryReceptacles = ONT2Initializer.createListOfP1AInventoryReceptacles();

        ont2.setP1AReceptacles(p1AInventoryReceptacles);

            ///// EXTRACT INDIVIDUAL FLOOR P1A ROWS (P1A-100s, 200s, etc.) FROM LIST ABOVE AND /////
            ///// PASS THE LISTS' REFERENCE TO CORRESPONDING ONT2 FIELDS /////

        // P1A 100s //
        ArrayList<InventoryReceptacle> p1A100sReceptacles = ONT2Initializer.extractP1A100sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A100sReceptacles(p1A100sReceptacles);

        // P1A 200s //
        ArrayList<InventoryReceptacle> p1A200sReceptacles = ONT2Initializer.extractP1A200sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A200sReceptacles(p1A200sReceptacles);

        // P1A 300s //
        ArrayList<InventoryReceptacle> p1A300sReceptacles = ONT2Initializer.extractP1A300sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A300sReceptacles(p1A300sReceptacles);

        // P1A 400s //
        ArrayList<InventoryReceptacle> p1A400sReceptacles = ONT2Initializer.extractP1A400sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A400sReceptacles(p1A400sReceptacles);

        // P1A 500s //
        ArrayList<InventoryReceptacle> p1A500sReceptacles = ONT2Initializer.extractP1A500sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A500sReceptacles(p1A500sReceptacles);

        // P1A 600s //
        ArrayList<InventoryReceptacle> p1A600sReceptacles = ONT2Initializer.extractP1A600sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A600sReceptacles(p1A600sReceptacles);

        // P1A 700s //
        ArrayList<InventoryReceptacle> p1A700sReceptacles = ONT2Initializer.extractP1A700sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A700sReceptacles(p1A100sReceptacles);

        // P1A 800s //
        ArrayList<InventoryReceptacle> p1A800sReceptacles = ONT2Initializer.extractP1A800sReceptacles
                                                                            (p1AInventoryReceptacles);
        ont2.setP1A800sReceptacles(p1A800sReceptacles);

            ///// ADD THE 8 LISTS ABOVE TO AN OUTER ARRAYLIST, MEANING INNER LISTS CONTAIN 1 ROW EACH /////
            ///// SET THE LIST OF LISTS AS ont2's p1AInventoryReceptaclesIndexedByRow /////

        // ALL P1A RECEPTACLES INDEXED BY ROW //
        ArrayList<ArrayList<InventoryReceptacle>> p1AInventoryReceptaclesIndexedByRow = new ArrayList<>(8);

        // add 8 inner lists to the outer list
        p1AInventoryReceptaclesIndexedByRow.add(p1A100sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A200sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A300sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A400sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A500sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A600sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A700sReceptacles);
        p1AInventoryReceptaclesIndexedByRow.add(p1A800sReceptacles);

        ont2.setP1AInventoryReceptaclesIndexedByRows(p1AInventoryReceptaclesIndexedByRow);

        ///// CREATE LIST TO HOLD ALL FC INVENTORY RECEPTACLES, ADD ALL P1A RECEPTACLES CREATED ABOVE TO LIST /////
                                            ///// SET AS ont2's FIELD /////

        ArrayList<InventoryReceptacle> listOfAllInventoryReceptacles = new ArrayList<>(749_999);

        for(int i = 0; i < p1AInventoryReceptaclesIndexedByRow.size(); i++) {
            listOfAllInventoryReceptacles.addAll(p1AInventoryReceptaclesIndexedByRow.get(i));
        }

        listOfAllInventoryReceptacles.trimToSize();

        ont2.setListOfAllInventoryReceptacles(listOfAllInventoryReceptacles);

        ///// CREATE LIST OF LISTS TO HOLD ont2 RECEPTACLES INDEXED BY FLOOR (P1A, P2A, P3A, P1B, P2B, P3B) /////

        ArrayList<ArrayList<InventoryReceptacle>> ont2InventoryReceptaclesIndexedByFloor =
                                                                                         new ArrayList<>(6);

        // add list of floor P1A receptacles to index 0
        ont2InventoryReceptaclesIndexedByFloor.add(p1AInventoryReceptacles);

        // for now, I only create 1 floor for ont2 (P1A), so after adding the list above, I can call setter method
        ont2.setInventoryReceptaclesIndexedByFloor(ont2InventoryReceptaclesIndexedByFloor);
    }

    private static void createAndAddEmployeesToList(ArrayList<Employee> list) {

        Employee joseag = new Employee("Jose Aguilar", "joseag", 10404);
        Employee griffiz = new Employee("Zachary Griffith", "griffiz", 100);
        Employee judanie = new Employee("Justin Daniels", "judanie", 101);
        Employee jontorre = new Employee("Jonathan Torres", "jontorre", 102);

        joseag.setSimpleBinCountPermissions(true);
        joseag.setHomeFC("ONT2");

        griffiz.setSimpleBinCountPermissions(true);
        griffiz.setHomeFC("ONT2");

        judanie.setSimpleBinCountPermissions(true);
        judanie.setInternalNetworkPassword("123");
        judanie.setHomeFC("ONT2");

        jontorre.setSimpleBinCountPermissions(true);
        jontorre.setInternalNetworkPassword("123");
        jontorre.setHomeFC("ONT2");

        list.add(joseag);
        list.add(griffiz);
        list.add(judanie);
        list.add(jontorre);

        list.trimToSize();
    }

   private static ArrayList<InventoryReceptacle> createListOfP1AInventoryReceptacles() {

        // capacity of 76_384 receptacles for floor P1A (9_548 receptacles/row)
        ArrayList<InventoryReceptacle> list = new ArrayList<InventoryReceptacle>(76_348);

        // I don't want to include the algos that I used to instantiate my inventory receptacle's locations. Instead, I
        // will read in the locations from a text file and store them in this array.
        String[] p1AReceptaclesLocations = new String[76_384];

        // read location values from text file and store them in an array
        try {
             Scanner input = new Scanner(new File("p1a-receptacles-locations.txt"));

             int i = 0;
             while (input.hasNext()) {
                    p1AReceptaclesLocations[i] = input.nextLine();
                    i++;
             }
        } catch (FileNotFoundException ex) {
                 System.out.println("FileNotFoundException, caused by attempt to read from" +
                                    " p1a-receptacles-locations.txt");
        }

       // Same as above, but w/ receptacles' pickPathID.
       int[] p1AReceptaclesPickPathIds = new int[76_384];

        // read pickPathID values from text file and store them in an array
        try {
             Scanner input = new Scanner(new File("p1a-receptacles-pickpath-ids.txt"));

             int i = 0;
             while (input.hasNext()) {
                    p1AReceptaclesPickPathIds[i] = input.nextInt();
                    i++;
             }
        }
        catch (FileNotFoundException ex) {
               System.out.println("FileNotFound Exception, caused by attempt to read from" +
                                  " p1a-receptacles-pickpath-ids.txt");
        }

        // Note that a receptacle's "id" field is different that its pickPathID field. An int value for id, as well as
        // a String value for location, is needed to invoke the InventoryReceptacle constructor.
        for (int i = 0, binId = 1; i < 76_384; i++, binId++) {
             list.add(new Bin(binId, p1AReceptaclesLocations[i]));
        }

        for (int i = 0; i < list.size(); i++) {
             list.get(i).setPickPathID(p1AReceptaclesPickPathIds[i]);
        }

        return list;
    }

   private static ArrayList<InventoryReceptacle> extractP1A100sReceptacles
                                                 (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

          ArrayList<InventoryReceptacle> p1A100sReceptacles = new ArrayList<InventoryReceptacle>(9_548);

          p1A100sReceptacles.addAll(p1AInventoryReceptacles.subList(0, 9_548));

          return p1A100sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A200sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A200sReceptacles = new ArrayList<>(9_548);

        p1A200sReceptacles.addAll(p1AInventoryReceptacles.subList(9_548, 19_096));

        return p1A200sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A300sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A300sReceptacles = new ArrayList<>(9_548);

        p1A300sReceptacles.addAll(p1AInventoryReceptacles.subList(19_096, 28_644));

        return p1A300sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A400sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A400sReceptacles = new ArrayList<>(9_548);

        p1A400sReceptacles.addAll(p1AInventoryReceptacles.subList(28_644, 38_192));

        return p1A400sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A500sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A500sReceptacles = new ArrayList<>(9_548);

        p1A500sReceptacles.addAll(p1AInventoryReceptacles.subList(38_192, 47_740));

        return p1A500sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A600sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A600sReceptacles = new ArrayList<InventoryReceptacle>(9_548);

        p1A600sReceptacles.addAll(p1AInventoryReceptacles.subList(47_740, 57_288));

        return p1A600sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A700sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A700sReceptacles = new ArrayList<InventoryReceptacle>(9_548);

        p1A700sReceptacles.addAll(p1AInventoryReceptacles.subList(57_288, 66_836));

        return p1A700sReceptacles;
    }

    private static ArrayList<InventoryReceptacle> extractP1A800sReceptacles
                                                  (ArrayList<InventoryReceptacle> p1AInventoryReceptacles) {

        ArrayList<InventoryReceptacle> p1A800sReceptacles = new ArrayList<>(9_548);

        p1A800sReceptacles.addAll(p1AInventoryReceptacles.subList(66_836, 76_384));

        return p1A800sReceptacles;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- ONT2 will eventually have 6 main floors, named P1A, P2A, P3A, P1B, P2B, P3B.
  Each sortable FC should have a max of 749,999 inventory receptacles.
  Each floor should have a max of 99,999 inventory receptacles.
  Each row (e.g. P1A-100s) should have a max of 9,999 inventory receptacles.

  In my program, floor P1A has 76,384 inventory receptacles.
  P1A has 8 rows, and every row has 9,548 inventory receptacles.
  Every row has 124 aisles, numbered from 102 --> 225, inclusive both end numbers.
  Every aisle in every row has 77 inventory receptacles (e.g. 77 receptacles in P1A-102-100s, 77 receptacles in
                                                         P1A-103-100s, etc.).

- QUESTION: Does real life ONT2 start with aisle number 102 in order to use even/remainder operator calculations in
  their algos?

- Should Exception handling be used to catch a FileNotFoundException, or should Exceptions be reserved only for
  unpreventable issues? Also, I definitely shouldn't display a message on the console. This particular error should be
  encapsulated from users.

*/