import java.util.Comparator;

public class BinSortByPickID implements Comparator<InventoryReceptacle> {

    @Override
    public int compare(InventoryReceptacle receptacle1, InventoryReceptacle receptacle2) {

        if (receptacle1.getPickPathID() < receptacle2.getPickPathID())
            return -1;
        else if (receptacle1.getPickPathID() > receptacle2.getPickPathID())
            return 1;
        else
            return 0;
    }
}