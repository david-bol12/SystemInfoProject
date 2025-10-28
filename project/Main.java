/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */



public class Main
{
public static void showDisk()
    {
        Disk[] disks = Disk.getDisks();
        for (Disk d : disks) {
            System.out.printf("Disk: %s, Type: %s, Total: %.2f GiB (%.2f GB), Used: %.2f GiB (%.2f GB) (%.1f%%), Current Health: %s%n",
                d.getName(),
                d.getType(),
                d.getTotalGiB(),
                d.getTotalGB(),
                d.getUsedGiB(),
                d.getUsedGB(),
                d.getPercentUsed(),
                d.getHealthStatus()
                );
        }
    }
    
    public static void showMem()
  {
        memInfo mem = new memInfo();
        mem.read();

        System.out.println(" Memory Information ");
        System.out.printf("Total: %.2f GiB or %.2f GB%n", mem.getTotalGiB(), mem.getTotalGB());
        System.out.printf("Used:  %.2f GiB or %.2f GB (%.1f%%)%n", mem.getUsedGiB(), mem.getUsedGB(), mem.getPercentUsed());
        System.out.printf("Free:  %.2f GiB or %.2f GB (%.1f%%)%n", mem.getFreeGiB(), mem.getFreeGB(), mem.getPercentFree());
        System.out.printf("Status: %s%n", mem.getMemoryStatus());
    }
    // Create a new branch before editing any code in main
    public static void main(String[] args)
    {
        System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        showDisk();
        showMem();
    }
}




            
