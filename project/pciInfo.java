/*
 *  PCI information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
//import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class pciInfo 
{
    // Refresh the current values and counters - call this before other methods
    public native void read ();

    // Return the number of PCI buses
    public native int busCount ();

    // Return the number of devices on a PCI bus
    public native int deviceCount (int bus);

    // Return the number of functions in a PCI device
    public native int functionCount (int bus, int device);

    // Return the number of functions in a PCI device
    public native int functionPresent (int bus, int device, int function);

    // Return the vendor ID of a PCI device
    public native int vendorID (int bus, int device, int function);

    // Return the product ID of a PCI device
    public native int productID (int bus, int device, int function);

    public static void showPCI()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, HashMap<String, HashMap<String, String>>>>() {}.getType();
        Type type2 = new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType();

        try(FileReader reader = new FileReader("pciDevices.json")) {
            HashMap<String, HashMap<String, HashMap<String, String>>> bigMap = gson.fromJson(reader, type);

            //System.out.println(map);
            //System.out.println(bigMap.get("0x046D"));
            String vendorString = gson.toJson(bigMap.get("0x046D"), type2);
            //System.out.println(vendorString);
            HashMap<String, HashMap<String, String>> vendorMap = gson.fromJson(vendorString, type2);
            //System.out.println("\n\n\n\n"+vendorMap);
            System.out.println(vendorMap.get("0x0808"));

        }
        catch (IOException e) {
            System.out.println("Uh oh");
        }



        pciInfo pci = new pciInfo();
        //JSONObject file = new JSONObject();
        ArrayList<Integer> busDeviceFunction = new ArrayList<>();
        pci.read();

        System.out.println("\nThis machine has "+
                pci.busCount()+" PCI buses ");

        // Iterate through each bus
        for (int i = 0; i < pci.busCount(); i++) {
            System.out.println("Bus "+i+" has "+
                    pci.deviceCount(i)+" devices");

            // Iterate for up to 32 devices.  Not every device slot may be populated
            // so ensure at least one function before printing device information
            for (int j = 0; j < 32; j++) {
                if (pci.functionCount (i, j) > 0) {
                    System.out.println("Bus "+i+" device "+j+" has "+
                            pci.functionCount(i, j)+" functions");

                    // Iterate through up to 8 functions per device.
                    for (int k = 0; k < 8; k++) {
                        if (pci.functionPresent (i, j, k) > 0) {
                            System.out.println("Bus "+i+" device "+j+" function "+k+
                                    " has vendor "+String.format("0x%04X", pci.vendorID(i,j,k))+
                                    " and product "+String.format("0x%04X", pci.productID(i,j,k)));
/*
                            busDeviceFunction.add(pci.vendorID(i,j,k));
                            if (vendorIDs.containsKey(pci.vendorID(i,j,k))) {
                                System.out.println(vendorIDs.get(pci.vendorID(i, j, k)) + " is the vendor for this bus");
                            }
                            else{
                                System.out.println("Vendor is unknown");
                            }
                            busDeviceFunction.add(pci.productID(i,j,k));
                            System.out.println(busDeviceFunction);



                                    */


                        }
                    }
                }
            }
        }
    }
}

