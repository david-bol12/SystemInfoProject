/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
public class Main {
 public static void main(String[] args) {
 System.loadLibrary("sysinfo"); // Loads C++ Library

        System.out.println("System Info Project");
        System.out.println("In bus branch");

	DevReader d1 = new DevReader();

	d1. getVendorIDs();
	d1.getDevices();
        


 }
}

