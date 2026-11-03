// waiting room: needs a constructor with capacity 
// doctor : needs a constructor with id, wait room, treatment room


import java.util.Scanner;
import java.util.ArrayList;

public class Hospital {
    public static void main(String[] args){
        // getting inputs
        int capacity, doctors, patients;
        Scanner input = new Scanner(System.in);

        System.out.print("Waiting Room Capacity: ");
        capacity = input.nextInt();
        
        System.out.print("Number of Doctors: ");
        doctors = input.nextInt();

        System.out.print("Number of Patients: ");
        patients = input.nextInt();

        // edge case handle
        while (capacity <= 0 || doctors <= 0 || patients <= 0) {
            System.out.println("Invalid input!");

            System.out.print("Waiting Room Capacity: ");
            capacity = input.nextInt();

            System.out.print("Number of Doctors: ");
            doctors = input.nextInt();

            System.out.print("Number of Patients: ");
            patients = input.nextInt();
        }

        // WaitingRoom waitingRoom = new WaitingRoom(capacity);
        Semaphore treatmentRoom = new Semaphore(2);
        ArrayList<Doctor> doctorList = new ArrayList();
        ArrayList<Patient> patientList = new ArrayList();

        // creating doctor & patients
        for (int i = 0; i < doctors; i++) {
            // Doctor doctor = new Doctor(i, waitingRoom, treatmentRoom);
            // doctorList.add(doctor);
        }
        for (int i = 0; i < patients; i++) {
            // Patient patient = new Patient(i, waitingRoom);
            // patientList.add(patient);
        }


        // -------------------- start of simulation --------------------
        System.out.println("Patients arriving:");
        for(int i = 0; i < patients ; i++){
            System.out.println("P"+ i+1 + " ");
        }

        for(Doctor doctor : doctorList){
            doctor.start();
        }
        for(Patient patient : patientList){
            patient.start();
        }

        try{
            for(Patient patient : patientList){
                patient.join();
            }
            for(Doctor doctor : doctorList){
                doctor.join();
            }
            System.out.println("All patients have been treated.");
            System.out.println("Simulation completed.");
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
