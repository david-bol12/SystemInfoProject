import java.util.HashSet;
import java.util.Set;

public class DevInfo {

    private static final int SCAN_INTERVAL_MS = 2000; // 2 seconds

    public  void devInfo() throws InterruptedException {
        usbInfo usb = new usbInfo(); // create usbInfo instance
        Set<String> previousDevices = new HashSet<>();

        System.out.println("Starting USB monitoring...");

        while (true) {
            usb.read(); // refresh USB info

            Set<String> currentDevices = new HashSet<>();

            int buses = usb.busCount();
            for (int i = 1; i <= buses; i++) {
                int deviceCount = usb.deviceCount(i);
                System.out.println("Bus " + i + " has " + deviceCount + " devices");

                for (int j = 1; j <= deviceCount; j++) {
                    int vendor = usb.vendorID(i, j);
                    int product = usb.productID(i, j);

                    // Skip dead/placeholder devices
                    if (vendor == 0x0000 && product == 0x0000) continue;

                    String deviceId = String.format("Bus %d | Vendor 0x%04X | Product 0x%04X", i, vendor, product);
                    currentDevices.add(deviceId);

                    // Detect inserted devices
                    if (!previousDevices.contains(deviceId)) {
                        System.out.println("🔌 USB device inserted: " + deviceId);
                    }
                }
            }

            // Removal detection is NOT possible due to library limitations
            // Any unplugged device will remain in the library's internal list
            // Explain this in your report if required

            previousDevices = new HashSet<>(currentDevices); // copy for next iteration
            Thread.sleep(SCAN_INTERVAL_MS);
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

