import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class DevInfo {

    public native void read(); // Refreshes all current counters

     // 2000 ms interval between each while statement,esesntially creates an endless loop while running



         public void devInfo() throws InterruptedException { // Create a method called devInfo() which will raise an InterruptedException when certain conditions are met
        usbInfo usb = new usbInfo(); // Create an instance of the usb class, this should help when reafding the usb bus
        Set<String> previousDevices = new HashSet<>(); // Creates a hash set of the previous devices, needed to register what devices already exist in the bus

int scanInterval = 2000;

while (true) {
    usb.read();
int buses = usb.busCount();
for (int i = 1; i <= buses; i++) {
    int deviceCount = usb.deviceCount(i);
    System.out.println("Bus " + i + " device count: " + deviceCount);
    for (int j = 1; j <= deviceCount; j++) {
        System.out.printf("Device %d: vendor=0x%04X product=0x%04X%n",
            j, usb.vendorID(i, j), usb.productID(i, j));
    }
}


    Map<String, String> deviceInfo = new HashMap<>();
    for (int i = 1; i <= buses; i++) {
        int deviceCount = usb.deviceCount(i);
        for (int j = 1; j <= deviceCount; j++) {
            int vendor = usb.vendorID(i, j);
            int product = usb.productID(i, j);

            if (vendor == 0x0000 && product == 0x0000) continue;

            String deviceKey = String.format("bus%d-device%d", i, j);
            deviceInfo.put(deviceKey, String.format("Vendor 0x%04X | Product 0x%04X", vendor, product));
        }
    }

    Set<String> currentDeviceKeys = deviceInfo.keySet();

    // Insertions
    for (String key : currentDeviceKeys) {
        if (!previousDevices.contains(key)) {
            System.out.println("USB inserted: " + deviceInfo.get(key));
        }
    }

    // Removals
    for (String key : previousDevices) {
        if (!currentDeviceKeys.contains(key)) {
            System.out.println("USB removed: " + key);
        }
    }

    previousDevices = new HashSet<>(currentDeviceKeys);
    Thread.sleep(scanInterval);
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

