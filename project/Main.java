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
        System.loadLibrary("sysinfo");// Loads C++ Library
        pciInfo pci = new pciInfo();
        PciDevice pciDevice = new PciDevice(pci.vendorID(1, 1, 1), pci.productID(1,1,1) );
        System.out.println(pciDevice.getProductName() + " " + pciDevice.getVendorName());
    }
}

