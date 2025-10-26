import javax.swing.*;
import java.awt.*;

public class Display {

    private double cpuLoad = 0;
    private int coreCount = 0;
    JLabel cpuLoadLabel = new JLabel(String.valueOf(cpuLoad), SwingConstants.CENTER);
    JLabel coreCountLabel = new JLabel(String.valueOf(coreCount), SwingConstants.CENTER);

    public Display() {
        // Create the main frame (window)
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

    public void paint() {
        cpuLoadLabel.setText(String.format("CPU Load: %.2f", cpuLoad) + "%");
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }
}

