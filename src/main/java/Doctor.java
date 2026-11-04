import java.util.concurrent.ThreadLocalRandom;

public class Doctor extends Thread{
int doctorID;
WaitingRoom waitingroom;
Semaphore treatmentRooms;

 public Doctor(
            int id,
            WaitingRoom waitingRoom,
            Semaphore treatmentRooms) {

        this.doctorID = id;
        this.waitingroom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    private String getDoctorName() {
        return "Doctor " + doctorID;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Patient patient = waitingroom.consume();

                // if (patient == null) {
                //     // System.out.println(getDoctorName() + " is leaving");
                //     break;
                // }

                if (patient.getPatientId() == -1) {
                    waitingroom.releaseSeat();
                    break;
                }

                System.out.println(getDoctorName() + " is assigned to " + patient.getPatientName() + "\n");
                waitingroom.releaseSeat();
                System.out.println(getDoctorName() + " is trying to acquire a treatment room.\n");

                boolean acquired = treatmentRooms.tryAcquire(3000);
                if (acquired) {
                    try {
                        System.out.println(getDoctorName() + " acquired a treatment room.");
                        System.out.println(getDoctorName() + " begins treating " + patient.getPatientName() + ".\n");

                        // random treatment time
                        int treatmentTime =ThreadLocalRandom.current().nextInt(1000, 5001);
                        Thread.sleep(treatmentTime);

                        System.out.println(getDoctorName() + " finished treating " + patient.getPatientName() + ".");
                        
                        System.out.println(getDoctorName() + " released the treatment room.");
                        System.out.println(getDoctorName() + " is now available.\n");
                        System.out.println(patient.getPatientName() + " left.");
                        Hospital.patientTreated();
                        
                    } finally {
                        treatmentRooms.V();
                    }

                } else {
                    System.out.println("Doctor " + doctorID + " could not acquire a treatment room.\n");
                    System.out.println(getDoctorName() + " is sending " + patient.getPatientName() + " back to the waiting room.");

                    // patient goes back into the bounded buffer.
                    waitingroom.produce(patient);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
