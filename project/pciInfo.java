/*
 *  PCI information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
//import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;
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
        HashMap<Integer, String> vendorIDs = new HashMap<>();
        vendorIDs.put(1002,"AMD");
        vendorIDs.put(8086,"Intel");
        vendorIDs.put(6900,"RedHat");
        vendorIDs.put(4318,"Nvidia");
        vendorIDs.put(4332,"Realtek");
        vendorIDs.put(6635,"Qualcomm");
        vendorIDs.put(1044,"Dell");
        vendorIDs.put(2605,"Matrox");
        vendorIDs.put(58340,"Broadcom");
        vendorIDs.put(4155,"Apple");
        vendorIDs.put(7196,"Corsair");

        HashMap<Integer, String> productIDs = new HashMap<>();




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

                            busDeviceFunction.add(pci.vendorID(i,j,k));
                            if (vendorIDs.containsKey(pci.vendorID(i,j,k))) {
                                System.out.println(vendorIDs.get(pci.vendorID(i, j, k)) + " is the vendor for this bus");
                            }
                            else{
                                System.out.println("Vendor is unknown");
                            }
                            busDeviceFunction.add(pci.productID(i,j,k));
                            System.out.println(busDeviceFunction);

                                   /*
                                    file.put("Bus",i);
                                    file.put("Device", j);
                                    file.put("Function",k);

                                    */

                        }
                    }
                }
            }
        }
    }
}

