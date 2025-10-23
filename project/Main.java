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
        int timer = 5;
        for (int i = 1; i < timer; i++) {
            System.out.println("Time: " + i);
            cpu.read(0);
            for (int j = 1; j <= cpu.coresPerSocket(); j++) {
                System.out.println("Core " + j + " User: " + cpu.getUserTime(j));
                System.out.println("Core " + j + " Idle: " + cpu.getUserTime(j));
            }
        }
    }
}

