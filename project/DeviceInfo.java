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



    cpuInfo cpu = new cpuInfo();
    memInfo memory = new memInfo();

    public DeviceInfo() {
        cpu.read(0);
        memory.read();
        cpuModel = cpu.getModel();
        l1iCacheSize = cpu.l1iCacheSize();
        l1dCacheSize = cpu.l1dCacheSize();
        l2CacheSize = cpu.l2CacheSize();
        l3CacheSize = cpu.l3CacheSize();
        coresPerSocket = cpu.coresPerSocket();
        socketCount = cpu.socketCount();
        memoryTotal = memoryUnit == storageUnit.GB ? memory.getTotalGB() : memory.getTotalGiB();
    }

    @Override
    public void run() {
        while (true) {
            cpu.read(500);
            cpuLoad = cpu.getCpuLoad();
            memory.read();
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
}
