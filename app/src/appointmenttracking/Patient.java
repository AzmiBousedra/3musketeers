package appointmenttracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String ramqNumber;
    private String insuranceProvider;
    private String insuranceNumber;
    private List<Consultation> consultations;
    private List<Invoice> invoices;

    public Patient(String password, String firstName, String lastName, LocalDate dateOfBirth,
            String phoneNumber, String ramqNumber, String insuranceProvider,
            String insuranceNumber) {
        super(password, firstName, lastName);
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.ramqNumber = ramqNumber;
        this.insuranceProvider = insuranceProvider;
        this.insuranceNumber = insuranceNumber;
        this.consultations = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public void addConsultation(Consultation consulation) {
        consultations.add(consulation);
    }

    public Invoice findInvoice(String invoiceId) {
        for (Invoice inv : invoices) {
            if (inv.getInvoiceId() != null && inv.getInvoiceId().equals(invoiceId)) {
                return inv;
            }
        }
        return null;
    }

    public Consultation findConsultation(String consultationId) {
        for (Consultation c : consultations) {
            if (c.getConsultationId() != null && c.getConsultationId().equals(consultationId)) {
                return c;
            }
        }
        return null;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

}
