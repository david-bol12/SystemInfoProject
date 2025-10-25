/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */



public class Main
{
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
        //showMemInfo m1 = new showMemInfo();
       // m1.showMemoryUsage();
        showDisk();
        showMem();
    }
}
/*class showMemInfo {
            private memInfo memory;

            public showMemInfo() {
                memory = new memInfo();
            }

        public void showMemoryUsage() {
        memory.read(); 

        int total = memory.getTotal(); 
        int used = memory.getUsed();

        double totalGB = total / (1000.0 * 1000.0);
        double usedGB = used / (1000.0 * 1000.0);
        double freeGB = totalGB - usedGB;
        double usedPercent = (usedGB / totalGB) * 100; //test

        System.out.println(" Memory Information ");
        System.out.printf("Total: %.2f GiB%n", totalGB);
        System.out.printf("Used:  %.2f GiB (%.1f%%)%n", usedGB, usedPercent);
        System.out.printf("Free:  %.2f GiB%n", freeGB);
    }
}*/



            
