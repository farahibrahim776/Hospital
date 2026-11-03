public class WaitingRoom {
    private int size;
    private int inptr=0;
    private int outptr=0;
    private Patient store[];
    Semaphore spaces;
    Semaphore elements;
    WaitingRoom(int size){
        this.size=size;
        store =new Patient[size];
        spaces=new Semaphore(size);
        elements=new Semaphore(0);
    }
    public  void addPatient(Patient value){
        synchronized (this){
        store[inptr]=value;
        inptr=(inptr+1)%size;
        }
    }
    public  Patient removePatient(){
        Patient value;
        synchronized(this){
        value= store[outptr];
        outptr=(outptr+1)%size;
        }
        return value;

    }
    public void produce(Patient value) {
        spaces.P();
        addPatient(value);
        elements.V();
    }

    public Patient consume(){
        elements.P();
        Patient value=removePatient();
        spaces.V();
        return value;
    }
}
