import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
public class DevReader
{
    // Refresh the current values and counters - call this before other methods
    public native void read ();

    // Return the number of USB buses
    public native int busCount ();

    // Return the number of devices on a USB bus
    public native int deviceCount (int bus);

    // Return the vendor ID of a USB device
    public native int vendorID (int bus, int device);

    // Return the product ID of a USB device
    public native int productID (int bus, int device);

 public Set<Map.Entry<Integer, Integer>> getVendorIDs() {
    Set<Map.Entry<Integer, Integer>> pairs = new HashSet<>();
    for (int i = 1; i <= busCount(); i++) {
        // Iterate through all devices on the bus
            for (int j = 1; j <= deviceCount(i); j++) {
                int vendor = vendorID(i, j);
                int product = productID(i, j);

                if (vendor != 0 && product != 0) {	//replaces key and value with vendor and product
                    pairs.add(new AbstractMap.SimpleEntry<>(vendor, product));            
    }
}
}
return pairs;
/*static ArrayList<Integer>  getVen()
{ ArrayList<Integer> ven = new ArrayList<Integer>();
 ven.add(vendor);
 return ven;
} */


/*       System.out.println("Bus " + i + " device " + validDeviceCount +
                        " has vendor " + String.format("0x%04X", vendor) +
                        " and product " + String.format("0x%04X", product)); */
}

    public ArrayList getDevices() {
        Set<Map.Entry<Integer, Integer>> pairs = getVendorIDs();
        ArrayList <String> devices = new ArrayList<String>();
            for(int i = 1; i <= busCount(); i++) {
                int validDeviceCount = 0;

                     for (int j = 1; j <= deviceCount(i); j++) {
                         int vendor = vendorID(i ,j);
                         int product = productID(i, j);

                            if (vendor != 0 && product != 0) {
                                validDeviceCount++;
                                devices.add((String.format("0x%04X", vendor) + String.format("0x%04X", product)));
                            }    

                              /*    System.out.println("Bus " + i + " device " + validDeviceCount +
                                    " has vendor " + String.format("0x%04X", vendor) +
                                    " and product " + String.format("0x%04X", product)); */



                            
                    }       



            }

    return devices;
    }
    
} // This shopuld be here

