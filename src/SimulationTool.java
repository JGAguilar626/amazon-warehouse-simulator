import java.util.ArrayList;

public abstract class SimulationTool {

    // The fields below are a skeleton version of what a SBC creation/assignment module might look like.
    private static ArrayList<String> ont2PendingSimpleBinCounts;
    private static ArrayList<String> aus2PendingSimpleBinCounts;
    private static ArrayList<String> san3PendingSimpleBinCounts;

    static void createSimulationEnvironment() {

        SimulationTool.createONT2SimulationEnvironment();
    }

    private static void createONT2SimulationEnvironment() {

        SimulationTool.ont2PendingSimpleBinCounts = new ArrayList<>();

        SortableFC ont2 = Main.getSortableFC("ONT2");

        // invoke static method to set a value for an InventoryReceptacle's numberOfItems field
        SimulationTool.addItemsToReceptacles(ont2.getListOfAllInventoryReceptacles());

        SimulationTool.createSimpleBinCounts(ont2.getListOfAllInventoryReceptacles(), ont2PendingSimpleBinCounts);
    }

    private static void addItemsToReceptacles(ArrayList<InventoryReceptacle> listOfReceptacles) {

        for (int i = 0; i < listOfReceptacles.size(); i++) {
             // traverse list of receptacles, generate a random int value (range: 0 <--> 50, inclusive both end #s),
             // set as current receptacle's numberOfItems field
             int randomNumber = (int)(Math.random() * 51);
             listOfReceptacles.get(i).setNumberOfItems(randomNumber);
        }
    }

    private static void createSimpleBinCounts(ArrayList<InventoryReceptacle> listOfReceptacles,
                                              ArrayList<String> listOfInventoryReceptacleLocations){

        // Traverse a list of receptacles and tag a hard-coded percentage as needing a Simple Bin Count. Percentage
        // value is hardcoded because a program that manually creates counts (CountMeister) with customizable
        // percentages is beyond the scope of this program.
        SimulationTool.randomlyFlagReceptaclesAsNeedingSBC(listOfReceptacles);

        ArrayList<InventoryReceptacle> receptaclesThatNeedSBC =
                                       SimulationTool.addReceptaclesThatNeedSBCToList(listOfReceptacles);

        SBCSimUtils.orderReceptaclesByPickID(receptaclesThatNeedSBC);

        SimulationTool.addLocationsThatNeedSBCToList(receptaclesThatNeedSBC, listOfInventoryReceptacleLocations);
    }

    // The code that I have commented-out in the method below is useful for quick testing purposes. It's basically the
    // same as the non-commented code, except that it tags very few bins (like 3-9 bins) as needing SBC, meaning that
    // the batch of Simple Bin Counts is going to only have like 3-9 bins.
    private static void randomlyFlagReceptaclesAsNeedingSBC(ArrayList<InventoryReceptacle> listOfReceptacles) {

        // generate a random int value (range: 1 <--> 100, inclusive both end #s) and if the int is <= 20 (20% chance of
        // the random number meeting this condition), mark the current receptacle as needing a Simple Bin Count
        for (int i = 0; i < listOfReceptacles.size(); i++) {
             int randomNumber = 1 + (int)(Math.random() * 100);

             if (randomNumber <= 20) {
                 listOfReceptacles.get(i).setNeedsSimpleBinCount(true);
             }
        }

        /*
        // generate a random int value (range: 1 <--> 100, inclusive both end #s) and if the int is <= 20 (20% chance of
        // the random number meeting this condition), mark the current receptacle as needing a Simple Bin Count
        for (int i = 0; i < listOfReceptacles.size(); i++) {
             int randomNumber = 1 + (int)(Math.random() * 15_000);

             if (randomNumber == 7_000) {
                 listOfReceptacles.get(i).setNeedsSimpleBinCount(true);
             }
        }
        */
    }

    private static ArrayList<InventoryReceptacle> addReceptaclesThatNeedSBCToList
                                                  (ArrayList<InventoryReceptacle> listOfReceptacles) {

        ArrayList<InventoryReceptacle> receptaclesThatNeedSBC = new ArrayList<>(listOfReceptacles.size());

        for (int i = 0; i < listOfReceptacles.size(); i++) {
            if (listOfReceptacles.get(i).getNeedsSimpleBinCount()) {
                receptaclesThatNeedSBC.add(listOfReceptacles.get(i));
            }
        }

        receptaclesThatNeedSBC.trimToSize();

        return receptaclesThatNeedSBC;
    }

    private static void addLocationsThatNeedSBCToList (ArrayList<InventoryReceptacle> receptaclesThatNeedSBC,
                                                       ArrayList<String> listOfInventoryReceptacleLocations) {

        for (int i = 0; i < receptaclesThatNeedSBC.size(); i++) {
             listOfInventoryReceptacleLocations.add(receptaclesThatNeedSBC.get(i).getLocation());
        }

        listOfInventoryReceptacleLocations.trimToSize();
    }

    static ArrayList<String> getPendingSimpleBinCounts(String fcName) {

        ArrayList<String> pendingSimpleBinCounts = null;

        if (fcName.equalsIgnoreCase("ONT2")) {
            pendingSimpleBinCounts = SimulationTool.ont2PendingSimpleBinCounts;
        }
        else if (fcName.equalsIgnoreCase("AUS2")){
                 pendingSimpleBinCounts = SimulationTool.aus2PendingSimpleBinCounts;
        }
        else if (fcName.equalsIgnoreCase("SAN3")) {
                 pendingSimpleBinCounts = SimulationTool.san3PendingSimpleBinCounts;
        }

        return pendingSimpleBinCounts;
    }

    static void displayPhysicalInventoryAsAsterisks(int numberOfItems) {

        System.out.println();
        System.out.println("-----");
        System.out.println(numberOfItems);
        System.out.println();

        for (int i = 0; i < numberOfItems; i++) {
             if ((i + 1) % 5 == 0) {
                 System.out.println('*');
             }
             else {
                   System.out.print('*');
             }
        }

        if (numberOfItems % 5 != 0) {
            System.out.println();
            System.out.println("-----");
        }
        else {
              System.out.println("-----");
        }
    }
}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

   - Save the code below as an example of casting syntax:

     ArrayList<ArrayList<InventoryReceptacle>> p1AReceptaclesIndexedByRows =
                                              ((ONT2)ont2).getP1AReceptaclesIndexedByRows();

*/