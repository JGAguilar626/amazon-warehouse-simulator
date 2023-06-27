import java.util.Date;

public abstract class Andon {

    private final String location;
    private final String creator;
    private final Date dateCreated;

    Andon (String location, String creator) {
          this.location = location;
          this.creator = creator;
          this.dateCreated = new Date();
    }

    String getLocation() {
           return this.location;
    }

    String getCreator() {
           return this.creator;
    }

    Date getDateCreated() {
         return this.dateCreated;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////     NOTES, IDEAS, PLANS, ETC.     ////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*


*/