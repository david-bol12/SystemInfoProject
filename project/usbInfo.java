/*
 *  USB information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

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

    public static void showUSB()
    {
        usbInfo usb = new usbInfo();
        usb.read();
        System.out.println("\nThis machine has "+
                usb.busCount()+" USB buses ");

        // Iterate through all of the USB buses
        for (int i = 1; i <= usb.busCount(); i++) {
            System.out.println("Bus "+i+" has "+
                    usb.deviceCount(i)+" devices");

            // Iterate through all of the USB devices on the bus
            for (int j = 1; j <= usb.deviceCount(i); j++) {
                System.out.println("Bus "+i+" device "+j+
                        " has vendor "+String.format("0x%04X", usb.vendorID(i,j))+
                        " and product "+String.format("0x%04X", usb.productID(i,j)));
                        if(usb.vendorID(j, i)== 0x0000) {
                            continue;
                        }
            }
        }
    }
}

