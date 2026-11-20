package appointmenttracking;

import java.time.LocalDate;

import appointmenttracking.types.PaymentMethod;

public class Payment {
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;

    public Payment(String paymentId, double amount, LocalDate paymentDate, PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }
}