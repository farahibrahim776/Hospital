public class Patient extends Thread {
    int patientID;
    WaitingRoom waitingroom;
Patient(int patientID,WaitingRoom waitingroom){
    this.patientID=patientID;
    this.waitingroom=waitingroom;
}
@Override
    public void run(){
    System.out.println("P"+patientID+"arrived");
    waitingroom.addpatient(this);
}
}
