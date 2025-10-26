public class DeviceInfo extends Thread{

    final String cpuModel;
    final int l1iCacheSize;
    final int l1dCacheSize;
    final int l2CacheSize;
    final int l3CacheSize;
    final int coresPerSocket;
    final int socketCount;
    private double cpuLoad = 0;


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
    }

    @Override
    public void run() {
        while (true) {
            cpu.read(500);
            cpuLoad = cpu.getCpuLoad();
        }
    }

    public double getCpuLoad() {
        return cpuLoad;
    }
}
