import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display {

    private double cpuLoad = 0;
    JLabel label = new JLabel(String.valueOf(cpuLoad), SwingConstants.CENTER);

    public Display() {
        // Create the main frame (window)
        JFrame frame = new JFrame("System Info App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);

        // Make frame visible
        frame.setVisible(true);
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
        label.setText(String.valueOf(cpuLoad));
    }
}

