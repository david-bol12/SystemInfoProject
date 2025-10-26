import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Display extends Thread {

    private DeviceInfo device;
    private JFrame frame = new JFrame("System Info App");
    private String[] lines;
    private JLabel[] labels;

    public Display(DeviceInfo device) {
        this.device = device;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Layout setup

        this.lines = new String[] {
                String.format("CPU Load: %.2f%%", device.getCpuLoad()),
                String.format("Total CPU Cores: %d", device.coresPerSocket),
        };

        labels = new JLabel[lines.length];
        System.out.println(Arrays.toString(lines));

        frame.setLayout(new GridLayout(labels.length, 1, 10, 5));


        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(lines[i]);
        }

        for (int i = 0; i < labels.length; i++) {
            frame.add(labels[i]);
        }

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
        this.lines = new String[] {
                String.format("CPU Load: %.2f%%", device.getCpuLoad()),
                String.format("Total CPU Cores: %d", device.coresPerSocket),
        };
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(lines[i]);
        }
    }
}

