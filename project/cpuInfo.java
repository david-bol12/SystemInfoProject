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

    public static void showCPU()
    {
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        // Show CPU model, CPU sockets and cores per socket
        System.out.println("CPU " + cpu.getModel() + " has "+
                cpu.socketCount() + " sockets each with "+
                cpu.coresPerSocket() + " cores");

        // Show sizes of L1,L2 and L3 cache
        System.out.println("l1d="+cpu.l1dCacheSize()+
                ", l1i="+cpu.l1iCacheSize()+
                ", l2="+cpu.l2CacheSize()+
                ", l3="+cpu.l3CacheSize());

        // Sleep for 1 second and display the idle time percentage for
        // core 1.  This assumes 10Hz so in one second we have 100
        int timer = 0;
        while(timer < 10) {
            cpu.read(1);
            int idleTime = 0;
            int userTime = 0;
            for (int i = 0; i < cpu.coresPerSocket(); i++) {
                idleTime += cpu.getIdleTime(i);
                userTime += cpu.getUserTime(i);
            }
            double cpuLoad = (double) userTime / (idleTime + userTime);
            System.out.println(cpuLoad);
            timer++;
        }
    }
}
