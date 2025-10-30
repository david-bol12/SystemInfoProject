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
            "CPU",
            "Memory",
            "Disk",
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
                frames = 0;
            }

            if (device.isUsbDeviceAdded()) {
                JOptionPane.showMessageDialog(frame, "New USB Device Added");
            }
            if (device.isUsbDeviceRemoved()) {
                JOptionPane.showMessageDialog(frame, "USB Device Removed");
            }
        }
    }

    public void paint() {
        this.lines = getLines();
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].updateLabels(lines[i]);
        }
    }

    public String getDisksInfo() {
        String disksInfo = "<html>";
        for (Disk disk : device.getDisks()) {
            disksInfo += String.format(
                    "<b>%s</b> <br>" +
                    "Disk Type: %s <br>" +
                    "Disk Size: %.2f GiB <br>" +
                    "Used Space: %.2f GiB  -  %.2f%% <br>" +
                    "Remaining Space: %.2f GiB  -  %.2f%% <br>" +
                    "Disk Status: %s <br> <br>",
                    disk.getName(),
                    disk.getType(),
                    disk.getTotalGiB(),
                    disk.getUsedGiB(),
                    disk.getPercentUsed(),
                    disk.getFreeGiB(),
                    disk.getPercentFree(),
                    disk.getHealthStatus()
            );
        }
        return disksInfo + "</html>";
    }

    public String[] getLines() {
        return new String[] {
                //General
                String.format("<html>" +
                                "<b>General</b> <br>" +
                            "CPU Load: %.2f%% <br>" +
                            "Used Memory: %.2f GiB   -   %.2f%% <br>" +
                            "Total Remaining Disk Space: %.2f/%.2f GiB  -  %.2f%% <br>" +
                            "</html>",
                        device.getCpuLoad(),
                        device.getMemoryUsed(),
                        device.getMemoryPercentUsed(),
                        device.getTotalUsedDiskSpace(),
                        device.getTotalDiskSpace(),
                        device.getTotalUsedDiskSpacePercentage()
                        ),
                //CPU
                String.format("<html>" +
                            "<b>CPU</b> <br>" +
                            "%s <br>" +
                            "CPU Load: %.2f%% <br>" +
                            "Total CPU Cores: %d <br>" +
                            "L1 Instruction Cache: %d <br>" +
                            "L1 Data Cache: %d <br>" +
                            "L2 Cache: %d <br>" +
                            "L3 Cache: %d <br>" +
                                "</html>",
                        device.cpuModel,
                        device.getCpuLoad(),
                        device.socketCount,
                        device.l1dCacheSize,
                        device.l1iCacheSize,
                        device.l2CacheSize,
                        device.l3CacheSize
                ),
                //Memory
                String.format("<html>" +
                        "Total Memory: %.2f GiB <br>" +
                        "Memory Used: %.2f GiB -  %.2f%% <br>" +
                        "Memory Free: %.2f GiB -  %.2f%% <br>" +
                        "Memory Status: %s <br>" +
                        "</html>",
                        device.memoryTotal,
                        device.getMemoryUsed(),
                        device.getMemoryPercentUsed(),
                        device.getMemoryFree(),
                        device.getMemoryPercentFree(),
                        device.getMemoryStatus()
                        ),
                //Disk
                getDisksInfo(),
        };
    }
}

class Tab {

    private String title;
    private Body body;
    private JScrollPane scrollPane;

    public Tab(String title, Body body) {
        this.title = title;
        this.body = body;
        this.scrollPane = new JScrollPane(body);
    }

    public void updateLabels(String text) {
        body.updateLabels(text);
    }

    public JComponent getBody() {
        return scrollPane;
    }

    public String getTitle() {
        return title;
    }
}

class Body extends JPanel {

    private JLabel label;
    private String text;

    public Body(String text) {
        super(new BorderLayout());
        this.text = text;
        label = new JLabel(text);
        label.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        add(label, BorderLayout.NORTH);
    }

    public void updateLabels(String updatedLine) {
        label.setText(updatedLine);
    }
}


