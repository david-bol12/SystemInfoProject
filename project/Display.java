import javax.swing.*;
import java.awt.*;

public class Display extends Thread {

    private DeviceInfo device;
    private JFrame frame = new JFrame("System Info App");
    private String[][] lines;
    private Body[] bodies;
    private Header[] headers = {
            new Header("CPU"),
            new Header("Memory"),
    };
    private JLabel[] labels;

    public Display(DeviceInfo device) {
        this.device = device;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Layout setup

        this.lines = getLines();

        this.bodies = new Body[lines.length];

        frame.setLayout(new BorderLayout());

        for (int i = 0; i < headers.length; i++) {
            frame.add(headers[i]);
            bodies[i] = new Body(lines[i]);
            frame.add(bodies[i]);
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
        this.lines = getLines();
        for (int i = 0; i < bodies.length; i++) {
            bodies[i].updateLabels();
        }
    }

    public String[][] getLines() {
        return new String[][]{
                {
                        String.format("CPU Load: %.2f%%", device.getCpuLoad()),
                        String.format("Total CPU Cores: %d", device.coresPerSocket),
                },
                {
                        String.format("Memory Used: %.2fGiB   -   %.2f%%", device.getMemoryUsed(), device.getMemoryPercentUsed())
                }
        };
    }
}

class Body extends JPanel {

    private JLabel[] labels;
    private String[] lines;

    public Body(String[] lines) {
        super(new GridLayout(lines.length, 1, 10 ,10));
        this.lines = lines;
        labels = new JLabel[lines.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(lines[i]);
            labels[i].setFont(new Font("TimesRoman", Font.PLAIN, 12));
            add(labels[i]);
        }
    }

    public void updateLabels() {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(lines[i]);
            labels[i].setFont(new Font("TimesRoman", Font.PLAIN, 12));
            add(labels[i]);
        }
    }
}

class Header extends JPanel {

    JLabel label = new JLabel();

    public Header(String title) {
        super(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        label.setText(title);
        label.setFont(new Font("TimesRoman", Font.BOLD, 16));
        add(label);
    }
}


