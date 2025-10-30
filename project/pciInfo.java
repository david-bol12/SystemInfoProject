/*
 *  PCI information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
//import org.json.simple.JSONObject;
import java.util.HashMap;
//import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class pciInfo {
    // Refresh the current values and counters - call this before other methods
    public native void read();

    // Return the number of PCI buses
    public native int busCount();

    // Return the number of devices on a PCI bus
    public native int deviceCount(int bus);

    // Return the number of functions in a PCI device
    public native int functionCount(int bus, int device);

    // Return the number of functions in a PCI device
    public native int functionPresent(int bus, int device, int function);

    // Return the vendor ID of a PCI device
    public native int vendorID(int bus, int device, int function);

    // Return the product ID of a PCI device
    public native int productID(int bus, int device, int function);

    public ArrayList<PciDevice> getPciDevices() {

        ArrayList<PciDevice> pciDevices = new ArrayList<PciDevice>();

        for (int bus = 0; bus < busCount(); bus++) {
            // Iterate for up to 32 devices.  Not every device slot may be populated
            // so ensure at least one function before printing device information
            for (int device = 0; device < 32; device++) {
                if (functionCount(bus, device) > 0) {

                    // Iterate through up to 8 functions per device.
                    for (int func = 0; func < 8; func++) {
                        if (functionPresent(bus, device, func) > 0) {
                            pciDevices.add(new PciDevice(vendorID(bus, device, func), productID(bus, device, func)));
                        }
                    }
                }
            }
        }
        return pciDevices;
    }


}

class PciDevice {

    private String vendorName;
    private String productName;

    public PciDevice (int vendorID, int productID) {
        try (FileReader reader = new FileReader("pciDevices.json")) {
            String vendor = String.format("0x%04X", vendorID);
            String product = String.format("0x%04X", productID);

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, HashMap<String, HashMap<String, String>>>>() {
            }.getType();
            HashMap<String, HashMap<String, HashMap<String, String>>> bigMap = gson.fromJson(reader, type);
            if (bigMap.containsKey(vendor)){
                Type type2 = new TypeToken<HashMap<String, HashMap<String, String>>>() {
                }.getType();
                String vendorString = gson.toJson(bigMap.get(vendor), type2);
                HashMap<String, HashMap<String, String>> vendorMap = gson.fromJson(vendorString, type2);
                if (vendorMap.containsKey(product)) {
                    vendorName = vendorMap.get(product).get("Vendor Name");
                    productName = vendorMap.get(product).get("Device Name");
                }
            }
            else{
                vendorName = "Unknown Vendor";
                productName = "Unknown Product";
            }
        } catch (IOException e) {
            vendorName = "Failed to Vendor Name";
            productName = "Failed to Vendor Name";
        }
    }

    public String getProductName() {
        return productName;
    }

    public String getVendorName() {
        return vendorName;
    }
}
