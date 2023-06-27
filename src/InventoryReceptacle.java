public abstract class InventoryReceptacle {

    private final int id;
    private final String location;
    private int pickPathID;
    private int numberOfItems;
    private Andon andon;
    private boolean needsSimpleBinCount;
    private boolean needsCycleCount;
    //private boolean isLocked;
    //private ArrayList<String> history;

    InventoryReceptacle(int id, String location) {
        this.id = id;
        this.location = location;
    }

    int getId() {
        return this.id;
    }

    void setPickPathID(int pickPathID) {
        this.pickPathID = pickPathID;
    }

    int getPickPathID() {
        return this.pickPathID;
    }

    String getLocation() {
           return this.location;
    }

    int getNumberOfItems() {
        return this.numberOfItems;
    }

    void setNumberOfItems(int number) {
         this.numberOfItems = number;
    }

    boolean getNeedsSimpleBinCount() {
            return this.needsSimpleBinCount;
    }

    void setNeedsSimpleBinCount(boolean boolVar) {
         this.needsSimpleBinCount = boolVar;
    }

    boolean getNeedsCycleCount() {
        return this.needsCycleCount;
    }

    void setNeedsCycleCount(boolean var) {
         this.needsCycleCount = var;
    }

    void setAndon(Andon andon) {
         this.andon = andon;
    }

    boolean hasAndon() {
        return this.andon != null;
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Might make the field isLocked more specific or add a variation. For example, isStowable or isPickable. Think about how
  allowing a stower/picker to work in a bin in need of a CC might give counters an undeserved error. Fancy code
  will consider factors such as current bin capacity, general FC capacity, Peak season, etc.

*/