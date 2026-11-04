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
                System.out.println(getDoctorName() + " is idle, waiting for patients"); // =>>>
                Patient patient = waitingroom.consume();

                if (patient == null) {
                    System.out.println(getDoctorName() + " is leaving");
                    break;
                }

                System.out.println(getDoctorName() + " is assigned to " + patient.getPatientName());

                boolean acquired = treatmentRooms.tryAcquire(3000);
                if (acquired) {
                    try {
                        System.out.println(getDoctorName() + " entered a treatment room");

                        System.out.println(getDoctorName() + " is treating " + patient.getPatientName());

                        Thread.sleep(2000);

                        System.out.println(getDoctorName() + " has finished treating " + patient.getPatientName());

                    } finally {
                        treatmentRooms.V();
                    }
                } else {
                    System.out.println(getDoctorName() + " couldn't get a treatment room");
                    waitingroom.produce(patient);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
