/*
 *  CPU information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

public class cpuInfo
{

    // Refresh the current values and counters - call this before other methods
    public native void read (int seconds);
    public native void read ();

    // Return the number of cores per CPU socket
    public native int coresPerSocket ();

    // Return the number of CPUs in this computer
    public native int socketCount ();

    // Return the model number of the CPU
    public native String getModel ();

    // Return the size in bytes of the L1 data cache
    public native int l1dCacheSize ();

    // Return the size in bytes of the L1 instruction cache
    public native int l1iCacheSize ();

    // Return the size in bytes of the L2 cache
    public native int l2CacheSize ();

    // Return the size in bytes of the L3 cache
    public native int l3CacheSize ();

    // Return the time in "jiffies" or 1/100ths of a second 
    // that the specified core has been in user mode
    public native int getUserTime (int core);

    // Return the time in "jiffies" or 1/100ths of a second 
    // that the specified core has been idle
    public native int getIdleTime (int core);

    // Return the time in "jiffies" or 1/100ths of a second 
    // that the specified core has been in system mode
    public native int getSystemTime (int core);

    public double getCpuLoad () {
        read(500);
        int idleTime = 0;
        int busyTime = 0;
        for (int i = 0; i < coresPerSocket(); i++) {
            idleTime += getIdleTime(i);
            busyTime += (getUserTime(i) + getSystemTime(i));
        }
        double cpuLoad = (double) busyTime / (idleTime + busyTime);
        return cpuLoad * 100;
    }

    public static void showCPU(Display display)
    {
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);
        while(true) {
            display.setCpuLoad(cpu.getCpuLoad());
            display.setCoreCount(cpu.coresPerSocket());
        }
    }
}
