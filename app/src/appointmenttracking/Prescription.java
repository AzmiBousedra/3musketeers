package appointmenttracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import appointmenttracking.types.PrescriptionStatus;

public class Prescription {
    private String prescriptionId;
    private LocalDate issueDate;
    private PrescriptionStatus status;
    private List<PrescriptionItem> items;

    public Prescription(String prescriptionId, LocalDate issueDate) {
        this.prescriptionId = prescriptionId;
        this.issueDate = issueDate;
        this.status = PrescriptionStatus.Pending;
        this.items = new ArrayList<>();
    }

    public PrescriptionItem addItem(Medication medication, String dosage, String frequency, String duration) {
        PrescriptionItem item = new PrescriptionItem(medication, dosage, frequency, duration);
        items.add(item);
        return item;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }
}