import java.util.Scanner;
import java.util.ArrayList;

// 1. The Object Class - One Call
class PhoneCall {
    private int durationInSeconds;
    private int callHour;
    private boolean isSameNetwork;

    public PhoneCall(int durationInSeconds, int callHour, boolean isSameNetwork) {
        this.durationInSeconds = durationInSeconds;
        this.callHour = callHour;
        this.isSameNetwork = isSameNetwork;
    }

    public double getRatePerMinute() {
        if (!isSameNetwork) {
            return 5.00; // Rule (c) Other network
        } else if (callHour >= 6 && callHour < 18) {
            return 4.00; // Rule (a) Day 6am - 6pm
        } else {
            return 3.00; // Rule (b) Night 6pm - 6am
        }
    }

    public double calculateBaseCost() {
        return (getRatePerMinute() / 60) * durationInSeconds;
    }

    public double calculateVat() {
        if (durationInSeconds > 120) { // Rule (d) More than 2 mins
            return calculateBaseCost() * 0.16;
        }
        return 0;
    }

    public double calculateTotalBill() {
        return calculateBaseCost() + calculateVat();
    }

    // For displaying one call
    public void displayCall(int callNumber) {
        System.out.println("\n--- Call " + callNumber + " ---");
        System.out.println("Duration: " + durationInSeconds + " seconds");
        System.out.println("Hour: " + callHour + ":00 hrs");
        System.out.println("Network: " + (isSameNetwork ? "Same Network" : "Other Network"));
        System.out.println("Rate: Ksh " + getRatePerMinute() + " / min");
        System.out.println("Base: Ksh " + String.format("%.2f", calculateBaseCost()));
        System.out.println("VAT: Ksh " + String.format("%.2f", calculateVat()));
        System.out.println("Total for this call: Ksh " + String.format("%.2f", calculateTotalBill()));
    }
}

// 2. The Main Class - Handles Many Calls
public class PhoneBilling {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<PhoneCall> calls = new ArrayList<>();

        System.out.println("===== MOBILE PHONE BILLING SYSTEM =====");
        System.out.print("Enter customer name: ");
        String name = input.nextLine();

        System.out.print("Enter phone number: ");
        String phone = input.nextLine();

        System.out.print("How many calls did the customer make? ");
        int numberOfCalls = input.nextInt();

        // Loop to collect all calls
        for (int i = 1; i <= numberOfCalls; i++) {
            System.out.println("\nEnter details for Call " + i);
            System.out.print("Duration in seconds: ");
            int duration = input.nextInt();

            System.out.print("Hour of call (0-23): ");
            int hour = input.nextInt();

            System.out.print("Same network? (1 = Yes, 2 = No): ");
            int choice = input.nextInt();
            boolean sameNetwork = (choice == 1);

            // Create object and add to list
            PhoneCall call = new PhoneCall(duration, hour, sameNetwork);
            calls.add(call);
        }

        // Display all bills + Grand Total
        double grandBase = 0;
        double grandVat = 0;
        double grandTotal = 0;

        System.out.println("\n========== FINAL BILL ==========");
        System.out.println("Customer: " + name);
        System.out.println("Phone: " + phone);

        for (int i = 0; i < calls.size(); i++) {
            PhoneCall c = calls.get(i);
            c.displayCall(i + 1);
            grandBase += c.calculateBaseCost();
            grandVat += c.calculateVat();
            grandTotal += c.calculateTotalBill();
        }

        System.out.println("\n----------------------------------");
        System.out.println("Total Base Cost: Ksh " + String.format("%.2f", grandBase));
        System.out.println("Total VAT: Ksh " + String.format("%.2f", grandVat));
        System.out.println("GRAND TOTAL TO PAY: Ksh " + String.format("%.2f", grandTotal));
        System.out.println("----------------------------------");

        input.close();
    }
}
