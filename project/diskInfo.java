/*
 *  Disk information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
class Disk 
{

    private String name = "";
    private String type = "";
    private double totalGiB = 0.0;
    private double totalGB = 0.0;
    private double usedGiB = 0.0;
    private double usedGB = 0.0;
    private double freeGiB = 0.0;
    private double freeGB = 0.0;
    private double percentFree = 0.0;
    private double percentUsed = 0.0;
    private String healthStatus = "";

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getTotalGiB() {
        return totalGiB;
    }

    public double getTotalGB() {
        return totalGB;
    }

    public double getUsedGiB() {
        return usedGiB;
    }

    public double getUsedGB() {
        return usedGB;
    }

    public double getPercentUsed() {
        return percentUsed;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public double getFreeGiB() {
        return freeGiB;
    }

    public double getFreeGB() {
        return freeGB;
    }

    public double getPercentFree() {
        return percentFree;
    }

    public static Disk[] getDisks()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        Disk[] disks = new Disk[disk.diskCount()];
        for (int i = 0; i < disk.diskCount(); i++) {
            Disk d = new Disk();
            d.name = disk.getName(i);
            d.type = disk.getType(i);
            d.totalGiB = disk.getTotalGiB(i);
            d.totalGB = disk.getTotalGB(i);
            d.usedGB = disk.getUsedGB(i);
            d.usedGiB = disk.getUsedGiB(i);
            d.percentUsed = disk.getPercentUsed(i);
            d.freeGiB = disk.getFreeGiB(i);
            d.freeGB = d.totalGB - d.usedGB;
            d.percentFree = disk.getPercentFree(i);
            d.healthStatus = disk.healthStatus(i);
            disks[i] = d; 
        }
        return disks;
    }
}

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
        } else if (name.startsWith("tmp")) {
            return "RAM Disk (Temporary Filesytem)";
        } else if (name.startsWith("/dev/sda")) {
            return "Virtual SATA Disk";
        } else { 
            return "Unknown";
        }
    } 
}



