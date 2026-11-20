package appointmenttracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appointmenttracking.types.InvoiceStatus;
import appointmenttracking.types.PaymentMethod;

public class Invoice {
    private String invoiceId;
    private double totalAmount;
    private InvoiceStatus status;
    private List<Payment> payments;

    public Invoice(String invoiceId, double totalAmount) {
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.status = InvoiceStatus.Unpaid;
        this.payments = new ArrayList<>();
    }

    public Payment recordPayment(double amount, LocalDate paymentDate, PaymentMethod paymentMethod) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, amount, paymentDate, paymentMethod);
        payments.add(payment);

        double totalPaid = 0;
        for (Payment p : payments) {
            totalPaid += p.getAmount();
        }

        if (totalPaid >= totalAmount) {
            this.status = InvoiceStatus.Paid;
        }

        return payment;
    }

    public String getInvoiceId() {
        return invoiceId;
    }
}
