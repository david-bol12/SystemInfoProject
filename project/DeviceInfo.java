public class DeviceInfo extends Thread{

    enum storageUnit {
        GIB,
        GB,
    }

    // CPU
    final String cpuModel;
    final int l1iCacheSize;
    final int l1dCacheSize;
    final int l2CacheSize;
    final int l3CacheSize;
    final int coresPerSocket;
    final int socketCount;
    private double cpuLoad = 0;

    // Memory
    private storageUnit memoryUnit = storageUnit.GIB;
    final double memoryTotal;
    private double memoryUsed;
    private double memoryFree;
    private double memoryPercentUsed;
    private double memoryPercentFree;
    private String memoryStatus;

    //USB
    private int prevUSBDeviceCount;
    private boolean usbDeviceRemoved = false;
    private boolean usbDeviceAdded = false;

    //Disk
    private double totalDiskSpace;
    private double totalUsedDiskSpace;
    private double totalUsedDiskSpacePercentage;

    //PCI



    private cpuInfo cpu = new cpuInfo();
    private memInfo memory = new memInfo();
    private usbInfo usb = new usbInfo();
    private Disk[] disks = Disk.getDisks();

    public DeviceInfo() {
        cpu.read(0);
        memory.read();
        usb.read();
        cpuModel = cpu.getModel();
        l1iCacheSize = cpu.l1iCacheSize();
        l1dCacheSize = cpu.l1dCacheSize();
        l2CacheSize = cpu.l2CacheSize();
        l3CacheSize = cpu.l3CacheSize();
        coresPerSocket = cpu.coresPerSocket();
        socketCount = cpu.socketCount();

        memoryTotal = memoryUnit == storageUnit.GB ? memory.getTotalGB() : memory.getTotalGiB();

        prevUSBDeviceCount = usb.getDevices().size();
        totalDiskSpace = Disk.totalDiskSpace(disks);
        totalUsedDiskSpace = Disk.totalDiskUsed(disks);
        totalUsedDiskSpacePercentage = Disk.totalDiskUsedPercentage(disks);
    }

    @Override
    public void run() {

        while (true) {
            cpu.read(500);
            cpuLoad = cpu.getCpuLoad();
            memory.read();
            usb.read();
            System.out.println(usb.getDevices());
            if (memoryUnit == storageUnit.GB) {
                memoryUsed = memory.getUsedGB();
                memoryFree = memory.getFreeGB();
            } else {
                memoryUsed = memory.getUsedGiB();
                memoryFree = memory.getFreeGiB();
            }
            memoryPercentUsed = memory.getPercentUsed();
            memoryPercentFree = memory.getPercentFree();
            memoryStatus = memory.getMemoryStatus();
            if (prevUSBDeviceCount > usb.getDevices().size()) {
                usbDeviceRemoved = true;
            } else if (prevUSBDeviceCount < usb.getDevices().size()) {
                usbDeviceAdded = true;
            } else {
                usbDeviceAdded = false;
                usbDeviceRemoved = false;
            }
            prevUSBDeviceCount = usb.getDevices().size();
        }
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public double getMemoryFree() {
        return memoryFree;
    }

    public double getMemoryPercentUsed() {
        return memoryPercentUsed;
    }

    public double getMemoryPercentFree() {
        return memoryPercentFree;
    }

    public String getMemoryStatus() {
        return memoryStatus;
    }

    public boolean isUsbDeviceAdded() {
        return usbDeviceAdded;
    }

    public boolean isUsbDeviceRemoved() {
        return usbDeviceRemoved;
    }

    public double getTotalDiskSpace() {
        return totalDiskSpace;
    }

    public double getTotalUsedDiskSpace() {
        return totalUsedDiskSpace;
    }

    public double getTotalUsedDiskSpacePercentage() {
        return totalUsedDiskSpacePercentage;
    }

    public Disk[] getDisks() {
        return disks;
    }
}
