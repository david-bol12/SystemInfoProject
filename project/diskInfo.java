/*
 *  Disk information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

public class diskInfo 
{
    // Refresh the current values and counters - call this before other methods
    public native void read ();
    public native int diskCount ();
    public native String getName (int disk);
    public native long getTotal (int disk);
    public native long getUsed (int disk);
    public native long getAvailable (int disk);
    
    
   public double getPercentUsed(int disk) {
        read();
        double total = getTotal(disk);
        double used = getUsed(disk);
        if (total == 0) {
            return 0; 
        }
        return (double) used / total * 100;
    }
    
    public double getTotalGB(int disk) {
        read();
        double total = getTotal(disk);
        double totalGB = total / (1024 * 1024 * 1024);
        return totalGB;
    }

     public double getUsedGB(int disk) {
        read();
        double used = getUsed(disk);
        double usedGB = used / (1024 * 1024 * 1024);
        return usedGB;
    }

    public String getType(int disk) {
        String name = getName(disk);
        if (name.startsWith("sd")) {
            return "SATA";
        } else if (name.startsWith("nvme")) {
            return "NVMe";
        } else if (name.startsWith("hd")) {
            return "IDE";
        } else if (name.startsWith("vd")) {
            return "Virtual Disk";
        } else if (name.startsWith("mmc")) {
            return "eMMC";
        } else if (name.startsWith("sr")) {
            return "Optical";
        } else if (name.startsWith("loop")) {
            return "Loopback";
        } else { 
            return "Unknown";
        }
    } 
        

}



