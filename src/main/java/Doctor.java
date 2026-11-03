import java.util.concurrent.Semaphore;

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
                // Patient patient = waitingroom.getPatient();
                treatmentRooms.acquire();
                try {
                    // System.out.println(getDoctorName() + " is treating " + patient.patientID);
                    Thread.sleep(2000);
                    // System.out.println(getDoctorName() + " has finished treating " + patient.patientID);
                } finally {
                    treatmentRooms.release();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
