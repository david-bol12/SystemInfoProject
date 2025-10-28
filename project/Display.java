import javax.swing.*;
import java.awt.*;

public class Display extends Thread {

    private DeviceInfo device;
    private JFrame frame = new JFrame("System Info App");
    private JTabbedPane tabbedPane;
    private String[] lines;
    private Tab[] tabs;
    private String[] tabTitles = new String[] {
            "General",
            "CPU"
    };
    private JLabel[] labels;

    public Display(DeviceInfo device) {
        this.device = device;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Layout setup

        this.lines = getLines();
        this.tabs = new Tab[tabTitles.length];

        this.tabbedPane = new JTabbedPane();


        frame.setLayout(new BorderLayout());
        frame.add(tabbedPane, BorderLayout.CENTER);

        for (int i = 0; i < tabTitles.length; i++) {
            this.tabs[i] = new Tab(tabTitles[i], new Body(lines[i]));
            tabbedPane.add(tabs[i].getTitle(), tabs[i].getBody());
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
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].updateLabels(lines[i]);
        }
    }

    public String[] getLines() {
        return new String[] {
                String.format("<html>" +
                        "CPU Load: %.2f%%" +
                        "Total CPU Cores: %d" +
                        "" +
                        "" +
                        "</html>",
                        device.getCpuLoad(),
                        device.socketCount
                        )
        };
    }
}

class Tab {

    private String title;
    private Body body;

    public Tab(String title, Body body) {
        this.title = title;
        this.body = body;
    }

    public void updateLabels(String text) {
        body.updateLabels(text);
    }

    public Body getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }
}

class Body extends JPanel {

    private JLabel label;
    private String text;

    public Body(String text) {
        super(new GridLayout(1, 1, 10 ,10));
        this.text = text;
        label = new JLabel(text);
        label.setFont(new Font("TimesRoman", Font.PLAIN, 12));
    }

    public void updateLabels(String updatedLine) {
        label.setText(updatedLine);
    }
}


