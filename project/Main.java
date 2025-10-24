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
            System.out.printf("Disk %s: Total: %.2f GB, Used: %.2f GB (%.1f%%), Type: %s%n",
                    disk.getName(i),
                    disk.getTotalGB(i),
                    disk.getUsedGB(i),
                    disk.getPercentUsed(i),
                    disk.getType(i));
        }
    }
    // Create a new branch before editing any code in main
    public static void main(String[] args)
    {
       
        System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        showMemInfo m1 = new showMemInfo();
        m1.showMemoryUsage();
        
        
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

        double totalGB = total / (1024.0 * 1024.0);
        double usedGB = used / (1024.0 * 1024.0);
        double freeGB = totalGB - usedGB;
        double usedPercent = (usedGB / totalGB) * 100;

        System.out.println(" Memory Information ");
        System.out.printf("Total: %.2f GB%n", totalGB);
        System.out.printf("Used:  %.2f GB (%.1f%%)%n", usedGB, usedPercent);
        System.out.printf("Free:  %.2f GB%n", freeGB);
    }
}



            
