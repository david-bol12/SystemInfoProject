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
                return "<p style=\"color:red;\"> Critical - Low Memory </p>";
            } else if (getPercentUsed() > 80.0) {
                return "<p style=\"color:orange;\"> Warning - High Memory Usage </p>";
            } else if (getPercentUsed() > 65.0) {
                return "<p style=\"color:yellow;\"> Moderate Memory Usage </p>";
            } else if (getPercentUsed() > 50.0) {
                return "<p style=\"color:lightgreen;\"> Normal Memory Usage </p>";
            }  else {
                return "<p style=\"color:green;\"> Healthy - Sufficient Memory </p>";
            }
        }

}


