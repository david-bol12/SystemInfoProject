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
        cpuInfo cpu = new cpuInfo();
        cpu.read(1);
        for (int i = 1; i <= cpu.coresPerSocket(); i++) {
            System.out.println("Core " + i + " User: " + cpu.getUserTime(i));
            System.out.println("Core " + i + " Idle: " + cpu.getUserTime(i));
        }
    }
}

