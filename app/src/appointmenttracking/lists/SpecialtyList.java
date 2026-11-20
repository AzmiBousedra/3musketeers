package appointmenttracking.lists;

import java.util.ArrayList;
import java.util.List;

import appointmenttracking.Specialty;

public class SpecialtyList {

	private List<Specialty> specialties = new ArrayList<>();

	public void addSpecialty(Specialty specialty) {
		specialties.add(specialty);
	}

	public Specialty findSpecialty(String name) {
		for (Specialty specialty : specialties) {
			if (specialty.getName().equalsIgnoreCase(name)) {
				return specialty;
			}
		}
		return null;
	}

	public List<Specialty> getSpecialties() {
		return specialties;
	}
}
