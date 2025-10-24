import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

 public class DevInfo {

    private static final int SCAN_INTERVAL_MS = 2000; // 2 seconds

    public static void main(String[] args) throws InterruptedException {

        System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        System.out.println("In bus branch");
        usbInfo usb = new usbInfo(); // Create the USB info instance
        Set<String> previousDevices = new HashSet<>();

        System.out.println("Starting USB monitoring...");

        while (true) {
            usb.read(); // Refresh the USB info

            Set<String> currentDevices = new HashSet<>();

            int buses = usb.busCount();
            for (int i = 1; i <= buses; i++) {
                int deviceCount = usb.deviceCount(i);
                for (int j = 1; j <= deviceCount; j++) {
                    int vendor = usb.vendorID(i, j);
                    int product = usb.productID(i, j);

                    // Skip dead/placeholder devices
                    if (vendor == 0x0000 && product == 0x0000) continue;

                    String deviceId = String.format("Bus %d | Vendor 0x%04X | Product 0x%04X", i, vendor, product);
                    currentDevices.add(deviceId);

                    // Detect newly inserted devices
                    if (!previousDevices.contains(deviceId)) {
                        System.out.println("🔌 USB device inserted: " + deviceId);
                    }
                }
            }

            // Remove dead devices from previousDevices (optional cleanup)
            removeDeadDevices(previousDevices);

            // Update previousDevices for next iteration
            previousDevices = new HashSet<>(currentDevices);

            Thread.sleep(SCAN_INTERVAL_MS);
        }
    }

    /**
     * Remove dead devices (vendorID=0x0000 and productID=0x0000)
     * from a set of device strings.
     */
    private static void removeDeadDevices(Set<String> devices) {
        Iterator<String> it = devices.iterator();
        while (it.hasNext()) {
            String device = it.next();
            if (device.contains("0x0000")) {
                it.remove();
            }
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

