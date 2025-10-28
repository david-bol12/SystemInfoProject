/*
 *  Memory information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */

public class memInfo 
{
    // Refresh the current values and counters - call this before other methods
    public native void read ();
    public native int getTotal ();
    public native int getUsed ();

    
        public double getTotalGiB() {
        double total = getTotal();
        double totalGiB = total / (1024 * 1024 );
        return totalGiB;
        }

        public double getTotalGB() {
        double total = getTotal();
        double totalGB = total / (1000 * 1000 );
        return totalGB;
        }

        public double getUsedGiB() {
        double used = getUsed();
        double usedGiB = used / (1024 * 1024 ); 
        return usedGiB;
        }

        public double getUsedGB() {
        double used = getUsed();
        double usedGB = used / (1000 * 1000 ); 
        return usedGB;
        }

        public double getFreeGiB() {
        double freeGiB = getTotalGiB() - getUsedGiB();
        return freeGiB;
        }

        public double getFreeGB() {
        double freeGB = getTotalGB() - getUsedGB();
        return freeGB;
        }

        public double getPercentUsed() {
        double total = getTotal();
        double used = getUsed();
        if (total == 0) {
            return 0; 
        }
        return (double) (used / total) * 100;
        }

        public double getPercentFree() {
        return 100.0 - getPercentUsed();
        }
        
        public String getMemoryStatus() {
            if (getPercentUsed() > 95.0) {
                return "Critical - Low Memory";
            } else if (getPercentUsed() > 80.0) {
                return "Warning - High Memory Usage";
            } else if (getPercentUsed() > 65.0) {
                return "Moderate Memory Usage";
            } else if (getPercentUsed() > 50.0) {
                return "Normal Memory Usage";
            }  else {
                return "Healthy - Sufficient Memory";
            }
        }

    public static void showMem()
    {
        memInfo mem = new memInfo();
        mem.read();

        System.out.println(" Memory Information ");
        System.out.printf("Total: %.2f GiB or %.2f GB%n", mem.getTotalGiB(), mem.getTotalGB());
        System.out.printf("Used:  %.2f GiB or %.2f GB (%.1f%%)%n", mem.getUsedGiB(), mem.getUsedGB(), mem.getPercentUsed());
        System.out.printf("Free:  %.2f GiB or %.2f GB (%.1f%%)%n", mem.getFreeGiB(), mem.getFreeGB(), mem.getPercentFree());
        System.out.printf("Status: %s%n", mem.getMemoryStatus());

    }
}


