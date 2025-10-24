/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Thread
{

    private Display display = new Display();
    private cpuInfo cpu = new cpuInfo();

    // Create a new branch before editing any code in main
    public static void main(String[] args)
    {
        System.out.println("System Info Project");
        System.loadLibrary("sysinfo"); // Loads C++ Library
        Main thread = new Main();
        thread.start();
        System.out.println("This code is outside of the thread");

    }
    public void run() {
        System.out.println("This code is running in a thread");

        cpuInfo.showCPU(display);
    }
}

