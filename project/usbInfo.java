/*
 *  USB information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class usbInfo 
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

    public static void showUSB()
    {
        usbInfo usb = new usbInfo();
        usb.read();
        System.out.println("\nThis machine has "+ usb.busCount()+" USB buses ");

            for (int i = 1; i <= usb.busCount(); i++) {
        int validDeviceCount = 0; // Counter for valid devices on this bus

        // Iterate through all devices on the bus
        for (int j = 1; j <= usb.deviceCount(i); j++) {
            int vendor = usb.vendorID(i, j);
            int product = usb.productID(i, j);

            if (vendor != 0 && product != 0) {
                pairs.add(new AbstractMap.SimpleEntry<>(vendor, product));
                validDeviceCount++;
                System.out.println("Bus " + i + " device " + validDeviceCount +
                        " has vendor " + String.format("0x%04X", vendor) +
                        " and product " + String.format("0x%04X", product));
            }
        }

      }
    for (Map.Entry<Integer, Integer> pair : pairs) {
            System.out.println(pair.getKey() + ", " + pair.getValue());
        }
    }
}
