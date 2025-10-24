
import java.util.Set;
import java.util.HashSet;

public class DevInfo {

    public native void read(); // Refreshes all current counters

     // 2000 ms interval between each while statement,esesntially creates an endless loop while running



         public void devInfo() throws InterruptedException { // Create a method called devInfo() which will raise an InterruptedException when certain conditions are met
        usbInfo usb = new usbInfo(); // Create an instance of the usb class, this should help when reafding the usb bus
        Set<String> previousDevices = new HashSet<>(); // Creates a hash set of the previous devices, needed to register what devices already exist in the bus

while (true) {
    // Refresh USB info
usb.read();

// Build current devices set
Set<String> currentDevices = new HashSet<>();
int buses = usb.busCount();
for (int i = 1; i <= buses; i++) {
    int deviceCount = usb.deviceCount(i);
    for (int j = 1; j <= deviceCount; j++) {
        int vendor = usb.vendorID(i, j);
        int product = usb.productID(i, j);

        // Skip dead devices (vendor/product 0x0000)
        if (vendor == 0x0000 && product == 0x0000) continue;

        String deviceId = String.format("Bus %d | Vendor 0x%04X | Product 0x%04X", i, vendor, product);
        currentDevices.add(deviceId);
    }
}

// Remove dead devices from previousDevices
previousDevices.removeIf(d -> d.contains("0x0000"));

// Detect inserted devices
for (String device : currentDevices) {
    if (!previousDevices.contains(device)) {
        System.out.println("USB device inserted: " + device);
    }
}

// Detect removed devices
for (String device : previousDevices) {
    if (!currentDevices.contains(device)) {
        System.out.println("USB device removed: " + device);
    }
}

// Update previousDevices for the next iteration
previousDevices = new HashSet<>(currentDevices);

        }
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

