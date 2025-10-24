import java.util.Set;
import java.util.HashSet;

public class DevInfo {

    public native void read(); // Refreshes all current counters

    private static  int scanInterval = 2000; // 2000 ms interval between each while statement,esesntially creates an endless loop while running



         public void devInfo() throws InterruptedException { // Create a method called devInfo() which will raise an InterruptedException when certain conditions are met
        usbInfo usb = new usbInfo(); // Create an instance of the usb class, this should help when reafding the usb bus
        Set<String> previousDevices = new HashSet<>(); // Creates a hash set of the previous devices, needed to register what devices already exist in the bus

            while (true) {
                usb.read(); // refresh hardware info
                Set<String> currentDevices = getConnectedDevices(usb); // Creates a hash set of the current devices in the usb bus, 

                // Detect new devices
                for (String device : currentDevices) { // reads through all the current devices 
                if (!previousDevices.contains(device)) {
                    System.out.println("USB device inserted: " + device);
                } // what this is telling us; as the for statement cycles through eaach of the current devices, if it finds a device that has not been sorted intp the previous device hash map then it has to be a new device
                }

            // Detect removed devices
                for (String device : previousDevices) { // reads therough all of the previous devices
                if (!currentDevices.contains(device)) {
                    System.out.println("USB device removed: " + device);
                }  // what this is telling us; as the for statement cycles through eaach of the previous devices, if it cant find a device that was in the current devices hash map, then it must have been removed
                }
            // end of the while statement

            previousDevices = currentDevices;  // Statement is neccesary to ensure there is no confusion, makes it so all devices that were currently registered aare now in the current device H.M, i.e makes it so it doesnt read the same device as a new device
            Thread.sleep(scanInterval); // calls the scanInterval, takes a 2 second break then goes through the while statement again
        }
}


       public static Set<String> getConnectedDevices(usbInfo usb) {
        Set<String> devices = new HashSet<>();

        int buses = usb.busCount();

        for (int i = 1; i <= buses; i++) {
            System.out.println("Bus "+i+" has "+
                    usb.deviceCount(i)+" devices");

            // Iterate through all of the USB devices on the bus
            int deviceCount = usb.deviceCount(i);
            for (int j = 1; j <= deviceCount; j++) {
                System.out.println("Bus "+i+" device "+j+
                        " has vendor "+String.format("0x%04X", usb.vendorID(i,j))+
                        " and product "+String.format("0x%04X", usb.productID(i,j)));
            }
        }
        return devices;
    } 
}

/*  public static void main(String[] args) throws InterruptedException{
    usbInfo u1 = new usbInfo(); //calls upon the usbInfo class which will refresh the all
    Set<String> previousDevices = new HashSet<>(); // creates an empty hash set

    while(true) {
    usb.read();
    Set<String> currentDevices = getConnectedDevices(usb);

    for (String device : currentDevices) {
                if (!previousDevices.contains(device)) {
                    System.out.println("USB device added: " + device);
                }
            }
        
     for (String device : previousDevices) {
                if (!currentDevices.contains(device)) {
                    System.out.println("USB device removed: " + device);
                }
            }

            previousDevices = currentDevices;
            Thread.sleep(POLL_INTERVAL_MS);
        }
    }*/

