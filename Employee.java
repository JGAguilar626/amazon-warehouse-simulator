import java.util.ArrayList;

public class Employee {

    private String name;
    private int id;
    private String login;
    private String homeFC;
    private String internalNetworkPassword;
    private String lastCompletedLocation;
    private boolean simpleBinCountPermissions;
    private int numberOfCompletedSBC_Counts;
    private ArrayList<String> listOfAndonsCreated;
    private ArrayList<String> listOfSkippedLocations;

    Employee(String name, String login, int id) {
             this.name = name;
             this.id = id;
             this.login = login;
             this.listOfAndonsCreated = new ArrayList<>(1_000);
             this.listOfSkippedLocations = new ArrayList<>(500);
    }

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getID() {
        return this.id;
    }

    void setID(int number) {
        this.id = number;
    }

    String getLogin() {
        return this.login;
    }

    void setLogin(String login) {
        this.login = login;
    }

    String getHomeFC() {
        return this.homeFC;
    }

    void setHomeFC(String fcName) {
        this.homeFC = fcName;
    }

    String getInternalNetworkPassword() {
        return this.internalNetworkPassword;
    }

    void setInternalNetworkPassword(String newPassword) {
         this.internalNetworkPassword = newPassword;
    }

    boolean isSimpleBinCountTrained() {
        return this.simpleBinCountPermissions;
    }

    void setSimpleBinCountPermissions(boolean boolVar) {
         this.simpleBinCountPermissions = boolVar;
    }

    String getLastCompletedLocation() {
           return this.lastCompletedLocation;
    }

    void setLastCompletedLocation(String location) {
        this.lastCompletedLocation = location;
    }

    void increaseCompletedSBC_Counts() {
         this.numberOfCompletedSBC_Counts++;
    }

    ArrayList<String> getListOfAndonsCreated() {
        return this.listOfAndonsCreated;
    }

    void setListOfAndonsCreated(ArrayList<String> listOfReceptacleLocations) {
        this.listOfAndonsCreated = listOfReceptacleLocations;
    }

    void addToListOfAndonsCreated(String location) {
         this.listOfAndonsCreated.add(location);
    }

    ArrayList<String> getListOfSkippedLocations() {
        return this.listOfSkippedLocations;
    }

    void setListOfSkippedLocations(ArrayList<String> listOfReceptacleLocations) {
        this.listOfSkippedLocations = listOfReceptacleLocations;
    }

    void addToListOfSkippedLocations(String location) {
         this.listOfSkippedLocations.add(location);
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*

- Some setter methods, such as setName(), should only be accessed by HR. Is best practice to not include unnecessary
  getters/setters?

- Ramifications of initializing the listOfAndonsCreated/listOfSkippedLocations fields after field declaration vs in
  constructor vs inside ONT2Initializer? How would this affect the ability for me to use the code in a Spring web
  MVC app?

- Think about better ways to implement the fields listOfAndonsCreated and listOfSkippedBins. Might not
  have a getter method that returns the actual fields, but simply copies them or displays the relevant
  info on the console.

- Perhaps eventually get rid of lastCompletedLocation, numberOfCompletedSBC_Counts, listOfAndonsCreated,
  listOfSkippedLocations. Perhaps I should extract them from lists and get them only when requested, as opposed to
  making them fields.

- Regarding the addToListOfSkippedLocations(String location) method, perhaps make it more than a location, add the
  time too? And the process? Perhaps the best way is to create a BinSkip object with fields login, location, time,
  and process?

*/