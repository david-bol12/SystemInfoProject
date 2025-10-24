/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */



public class Main
{

    // Create a new branch before editing any code in main
    public static void main(String[] args)
    {
       
        System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        showMemInfo m1 = new showMemInfo();
        m1.showMemoryUsage();
        showDiskInfo d1 = new showDiskInfo();
        d1.diskMethod();
        
}
        
    }
class showMemInfo {
            private memInfo memory;

            public showMemInfo() {
                memory = new memInfo();
            }

        public void showMemoryUsage() {
        memory.read(); 

        int total = memory.getTotal(); 
        int used = memory.getUsed();

        double totalGB = total / (1024.0 * 1024.0 * 1024.0);
        double usedGB = used / (1024.0 * 1024.0 * 1024.0);
        double freeGB = totalGB - usedGB;
        double usedPercent = (usedGB / totalGB) * 100;

        System.out.println(" Memory Information ");
        System.out.printf("Total: %.2f GB%n", totalGB);
        System.out.printf("Used:  %.2f GB (%.1f%%)%n", usedGB, usedPercent);
        System.out.printf("Free:  %.2f GB%n", freeGB);
    }
}

class showDiskInfo {
    private diskInfo disk;

    public showDiskInfo() {
        disk = new diskInfo();
    }

    public void diskMethod() {
        disk.read();

        
        for (int i = 0; i < disk.diskCount(); i++) {
            double totalGB = disk.getTotal(i) / (1024.0 * 1024.0 * 1024.0);
            System.out.println ("disk "+disk.getName(i)+" has "+
                    disk.getTotal(i)+" blocks, of which "+
                    disk.getUsed(i)+" are used");
            System.out.printf("Block GB: %.2f%n", totalGB);
        }

    }
            
}