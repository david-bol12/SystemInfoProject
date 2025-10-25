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

    public static void showDisk()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        for (int i = 0; i < disk.diskCount(); i++) {
            System.out.printf("Disk: %s, Type: %s, Total: %.2f GiB, Used: %.2f GiB (%.1f%%), Current Health: %s%n",
                    disk.getName(i),
                    disk.getType(i),
                    disk.getTotalGiB(i),
                    disk.getUsedGiB(i),
                    disk.getPercentUsed(i),
                    disk.healthStatus(i)
            );
        }
    }
    
    
    public double getPercentUsed(int disk) {
        double total = getTotal(disk);
        double used = getUsed(disk);
        if (total == 0) {
            return 0; 
        }
        return (double) used / total * 100;
    }  

    public double getPercentFree(int disk) {
    double total = getTotal(disk);
    double available = getAvailable(disk);
    if (total == 0) {
        return 0;
    }
    return (available / total) * 100;
    }

    public double getFreeGiB(int disk) {
        double available = getAvailable(disk);
        double availableGiB = available / (1024 * 1024 );
        return availableGiB;
    }

    public double getTotalGiB(int disk) {  
        double total = getTotal(disk);
        double totalGiB = total / (1024 * 1024 );
        return totalGiB;
    }

    public double getTotalGB(int disk) {  
        double total = getTotal(disk);
        double totalGB = total / (1000 * 1000 );
        return totalGB;
    }


    public double getUsedGiB(int disk) {
        double used = getUsed(disk);
        double usedGiB = used / (1024 * 1024 );
        return usedGiB;
    }

    public double getUsedGB(int disk) {
        double used = getUsed(disk);
        double usedGB = used / (1000 * 1000 );
        return usedGB;
    }

    public String healthStatus(int disk) {
        if (getPercentUsed(disk) > 95.0) {
            return "Critical - Almost Full";
        } else if (getPercentUsed(disk) > 80.0) {
            return "Warning - Getting Full";
        } else {
            return "Healthy - Sufficient Space";
        }
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



