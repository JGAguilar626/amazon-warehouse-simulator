import java.util.ArrayList;

public abstract class SortableFC {

    // NAME will be a value such as ONT2, AUS2, SAN3, PSP1, etc.
    private final String NAME;
    private ArrayList<Employee> listOfEmployees;
    private ArrayList<String> listOfLoginsWithFCAccess;
    private ArrayList<InventoryReceptacle> listOfAllInventoryReceptacles;

    //TODO: Perhaps only initialize the field NAME, not listOfEmployees...
    SortableFC(String NAME) {
        this.NAME = NAME;
        this.listOfEmployees = new ArrayList<>(5_000);
    }

    String getNAME() {
        return this.NAME;
    }

    ArrayList<Employee> getListOfEmployees() {
        return this.listOfEmployees;
    }

    void setListOfEmployees(ArrayList<Employee> listOfEmployees) {
         this.listOfEmployees = listOfEmployees;
    }

    ArrayList<String> getListOfLoginsWithFCAccess() {
        return this.listOfLoginsWithFCAccess;
    }

    void setListOfLoginsWithFCAccess(ArrayList<String> listOfLogins) {
         this.listOfLoginsWithFCAccess = listOfLogins;
    }

    ArrayList<InventoryReceptacle> getListOfAllInventoryReceptacles() {
        return this.listOfAllInventoryReceptacles;
    }

    void setListOfAllInventoryReceptacles(ArrayList<InventoryReceptacle> list) {
         this.listOfAllInventoryReceptacles = list;
    }

    InventoryReceptacle getInventoryReceptacle(String location) {

        InventoryReceptacle receptacle = null;

        for (int i = 0; i < this.listOfAllInventoryReceptacles.size(); i++) {
             if (listOfAllInventoryReceptacles.get(i).getLocation().equalsIgnoreCase(location)) {
                 receptacle = listOfAllInventoryReceptacles.get(i);
                 break;
             }
        }

        return receptacle;
    }

    int getNumberOfInventoryReceptaclesInFC() {
        return this.listOfAllInventoryReceptacles.size();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

-

*/