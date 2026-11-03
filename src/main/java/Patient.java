public class Patient extends Thread {

    private final int patientId;
    private final WaitingRoom waitingRoom;

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    public String getPatientName() {
        return "P" + patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public void run() {

        System.out.println(getPatientName() + " arrived.");

        waitingRoom.produce(this);
    }

    @Override
    public String toString() {
        return getPatientName();
    }
}