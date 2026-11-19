package appointmenttracking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	static final String DATA_DIR = Files.isDirectory(Path.of("data")) ? "data/" : "app/data/";

	public static void main(String[] args) {
		Console console = new Console(); 
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println();
			System.out.println("=== Appointment Tracking System ===");
			System.out.println("1. Book an appointment");
			System.out.println("2. Search for availability");
			System.out.println("3. Make availability");
			System.out.println("4. Import availabilities from CSV");
			System.out.println("5. Export availabilities to CSV");
			System.out.println("0. Exit");
			System.out.print("Choice: ");
			if (!in.hasNextLine()) {
				return;
			}
			String choice = in.nextLine().trim();

			try {
				switch (choice) {
				case "1": // UC-01 Book Appointment 
				case "2": // UC-20 Search Availability 
				case "3": // UC-21 Create Availability 
					System.out.println("Not connected yet.");
					break;
				case "4": { // UC-22 Import Data from CSV 
					List<Path> files;
					try (Stream<Path> listing = Files.list(Path.of(DATA_DIR))) {
						files = listing.filter(p -> p.toString().toLowerCase().endsWith(".csv")).sorted().toList();
					}
					if (files.isEmpty()) {
						System.out.println("No CSV files found in " + DATA_DIR);
						break;
					}
					System.out.println("CSV files in " + DATA_DIR + ":");
					for (int i = 0; i < files.size(); i++) {
						System.out.println("  " + (i + 1) + ". " + files.get(i).getFileName());
					}
					System.out.print("Pick a file (1-" + files.size() + "): ");
					int pick;
					try {
						pick = Integer.parseInt(in.nextLine().trim());
					} catch (NumberFormatException e) {
						pick = 0;
					}
					if (pick < 1 || pick > files.size()) {
						System.out.println("Invalid file number.");
						break;
					}
					int count = console.importAvailabilities(files.get(pick - 1).toString());
					System.out.println(count + " time slot(s) imported from " + files.get(pick - 1).getFileName() + ".");
					break;
				}
				case "5": { // UC-23 Export Availabilities to CSV 
					System.out.print("Name of the export file (e.g. exporttest.csv): ");
					String name = in.nextLine().trim();
					if (name.isEmpty()) {
						System.out.println("No file name given.");
						break;
					}
					name = Path.of(name).getFileName().toString(); 
					if (!name.toLowerCase().endsWith(".csv")) {
						name += ".csv";
					}
					Path target = Path.of(DATA_DIR, name);
					if (Files.exists(target)) {
						System.out.print(name + " already exists. Overwrite? (y/n): ");
						if (!in.nextLine().trim().equalsIgnoreCase("y")) {
							System.out.println("Export cancelled.");
							break;
						}
					}
					int count = console.exportAvailabilities(target.toString());
					System.out.println(count + " time slot(s) exported to " + target + ".");
					break;
				}
				case "0":
					System.out.println("Goodbye.");
					return;
				default:
					System.out.println("Invalid choice.");
				}
			} catch (IOException e) {
				System.out.println("File error (" + e.getClass().getSimpleName() + "): " + e.getMessage());
			}
		}
	}
}
