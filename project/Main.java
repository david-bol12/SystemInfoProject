/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
public class Main {
     public static void main(String[] args) {
     System.loadLibrary("sysinfo"); // Loads C++ Library
            DeviceInfo device = new DeviceInfo();
            device.start();
            Display display = new Display(device);
            display.start();
        }
}

