/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("System Info Project");
        System.loadLibrary("sysinfo");
        sysInfo info = new sysInfo();
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        cpuInfo.showCPU();
        pciInfo.showPCI();
        usbInfo.showUSB();
        diskInfo.showDisk();
        memInfo.showMem();
    }
}

