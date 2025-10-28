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

   static Set<Map.Entry<Integer, Integer>> pairs = new HashSet<>();

    ArrayList<Integer> ven = new ArrayList<Integer>();

    public static void devReader()
    {
          for (int i = 1; i <= usb.busCount(); i++) {
        int validDeviceCount = 0; // Counter for valid devices on this bus

        // Iterate through all devices on the bus
        for (int j = 1; j <= usb.deviceCount(i); j++) {
            int vendor = usb.vendorID(i, j);
            int product = usb.productID(i, j);

            if (vendor != 0 && product != 0) {		//replaces key and value with vendor and product
                pairs.add(new AbstractMap.SimpleEntry<>(vendor, product));
                validDeviceCount++;
		ven.add(vendor);
		System.out.println(vendor);


        /*       System.out.println("Bus " + i + " device " + validDeviceCount +
                        " has vendor " + String.format("0x%04X", vendor) +
                        " and product " + String.format("0x%04X", product)); */
            }
	}
	}

	 System.out.println();
    for (Map.Entry<Integer, Integer> pair : pairs) {
            System.out.println(pair.getKey() + ", " + pair.getValue());
	}
 }

 public ArrayList<Integer> getVendorIDs() {
        return new ArrayList<>(ven);
    }
/*static ArrayList<Integer>  getVen()
{ ArrayList<Integer> ven = new ArrayList<Integer>();
 ven.add(vendor);
 return ven;
} */
}

