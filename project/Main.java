/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
public class Main {
    public static void main(String[] args) throws InterruptedException
    {

          System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        System.out.println("In bus branch");

        
        usbInfo u1 = new usbInfo();
        u1.read();; 
        DevInfo d1 = new DevInfo();
        d1.devInfo();
    }

}
