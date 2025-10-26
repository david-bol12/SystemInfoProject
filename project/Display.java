import javax.swing.*;
import java.awt.*;

public class Display extends Thread {

    private int coreCount = 0;
    DeviceInfo device;
    JLabel cpuLoadLabel = new JLabel("0.0", SwingConstants.CENTER);
    JLabel coreCountLabel = new JLabel(String.valueOf(coreCount), SwingConstants.CENTER);

    public Display(DeviceInfo device) {
        // Create the main frame (window)
        this.device = device;
        JFrame frame = new JFrame("System Info App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(cpuLoadLabel, BorderLayout.CENTER);
        frame.add(coreCountLabel, BorderLayout.AFTER_LAST_LINE);

        // Make frame visible
        frame.setVisible(true);
    }

    @Override
    public void run() {
        int FPS_SET = 20;
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            if ((System.nanoTime() - lastFrame) >= timePerFrame) {
                paint();
                lastFrame = System.nanoTime();
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void paint() {
        cpuLoadLabel.setText(String.format("CPU Load: %.2f", device.getCpuLoad()) + "%");
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }
}

