import java.util.Scanner;
import java.util.ArrayList;

public class Hospital {

    private static int treatedPatients = 0;
    public static void patientTreated() {
        synchronized (Hospital.class) {
            treatedPatients++;
            Hospital.class.notifyAll();
        }
    }

    private static void waitForAllPatients(int total) throws InterruptedException {
        synchronized (Hospital.class) {
            while (treatedPatients < total) {
                Hospital.class.wait();
            }
        }
    }

    public static void main(String[] args) {
        int capacity, doctors, patients;
        Scanner input = new Scanner(System.in);

        System.out.print("Waiting Room Capacity: ");
        capacity = input.nextInt();

        System.out.print("Number of Doctors: ");
        doctors = input.nextInt();

        System.out.print("Number of Patients: ");
        patients = input.nextInt();

        while (capacity <= 0 || doctors <= 0 || patients <= 0) {
            System.out.println("Invalid input!");

            System.out.print("Waiting Room Capacity: ");
            capacity = input.nextInt();

            System.out.print("Number of Doctors: ");
            doctors = input.nextInt();

            System.out.print("Number of Patients: ");
            patients = input.nextInt();
        }

        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        Semaphore treatmentRoom = new Semaphore(2);
        ArrayList<Doctor> doctorList = new ArrayList();
        ArrayList<Patient> patientList = new ArrayList();

        for (int i = 1; i <= doctors; i++) {
            Doctor doctor = new Doctor(i, waitingRoom, treatmentRoom);
            doctorList.add(doctor);
        }
        for (int i = 1; i <= patients; i++) {
            Patient patient = new Patient(i, waitingRoom);
            patientList.add(patient);
        }

        // -------------------- start of simulation --------------------

        System.out.println("\nPatients arriving:");
        for (int i = 1; i <= patients; i++) {
            System.out.print("P" + i + " ");
        }
        System.out.print("\n\n");

        for (Doctor doctor : doctorList) {
            doctor.start();
        }
        for (Patient patient : patientList) {
            patient.start();
        }

        try {
            for (Patient patient : patientList) {
                patient.join();
            }
            waitForAllPatients(patients);

            for (int i = 0; i < doctors; i++) {
                waitingRoom.produce(new Patient(-1, waitingRoom));
            }

            for (Doctor doctor : doctorList) {
                doctor.join();
            }

            System.out.println("All patients have been treated.");
            System.out.println("Simulation completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        input.close();
    }
}
