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
                treatmentRooms.P();
                try {
                    System.out.println(getDoctorName() + " is treating " + patient.getPatientName());
                    Thread.sleep(2000);
                    System.out.println(getDoctorName() + " has finished treating " + patient.getPatientName());
                } finally {
                    treatmentRooms.V();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
