import java.util.ArrayList;

public final class ONT2 extends SortableFC {

    ///// P1A InventoryReceptacle RELATED FIELDS /////
    private ArrayList<InventoryReceptacle> p1A100sReceptacles;
    private ArrayList<InventoryReceptacle> p1A200sReceptacles;
    private ArrayList<InventoryReceptacle> p1A300sReceptacles;
    private ArrayList<InventoryReceptacle> p1A400sReceptacles;
    private ArrayList<InventoryReceptacle> p1A500sReceptacles;
    private ArrayList<InventoryReceptacle> p1A600sReceptacles;
    private ArrayList<InventoryReceptacle> p1A700sReceptacles;
    private ArrayList<InventoryReceptacle> p1A800sReceptacles;
    private ArrayList<InventoryReceptacle> p1AReceptacles;
    // index 0 = P1A-100s, index 1 = P1A-200s,...,index 7 = P1A-800s
    private ArrayList<ArrayList<InventoryReceptacle>> p1AReceptaclesIndexedByRows;
    // index 0 = P1A, index 1 = P2A, index 2 = P3A, index 3 = P1B, index 4 = P2B, index 5 = P3B

    ///// P2A InventoryReceptacles /////
    //TODO: Code additional floors!

    ///// All InventoryReceptacles /////
    private ArrayList<ArrayList<InventoryReceptacle>> inventoryReceptaclesIndexedByFloor;

    ONT2() {
            super("ONT2");
    }

    void setP1A100sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A100sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A200sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A200sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A300sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A300sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A400sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A400sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A500sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A500sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A600sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A600sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A700sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A700sReceptacles = listOfInventoryReceptacles;
    }

    void setP1A800sReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1A800sReceptacles = listOfInventoryReceptacles;
    }

    ArrayList<ArrayList<InventoryReceptacle>> getP1AReceptaclesIndexedByRows() {
        return this.p1AReceptaclesIndexedByRows;
    }

    void setP1AInventoryReceptaclesIndexedByRows(ArrayList<ArrayList<InventoryReceptacle>>
                                                 outerListOfInventoryReceptacles) {

        this.p1AReceptaclesIndexedByRows = outerListOfInventoryReceptacles;
    }

    ArrayList<InventoryReceptacle> getP1AReceptacles() {
        return this.p1AReceptacles;
    }

    void setP1AReceptacles(ArrayList<InventoryReceptacle> listOfInventoryReceptacles) {
        this.p1AReceptacles = listOfInventoryReceptacles;
    }

    void setInventoryReceptaclesIndexedByFloor(ArrayList<ArrayList<InventoryReceptacle>>
                                               outerListOfInventoryReceptacles) {

        this.inventoryReceptaclesIndexedByFloor = outerListOfInventoryReceptacles;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Perhaps put InventoryReceptacles in about 2-3 main lists, then call static methods that extract them by rows?

*/