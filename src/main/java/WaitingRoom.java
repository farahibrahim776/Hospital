public class WaitingRoom {
    private int size;
    private int inptr = 0;
    private int outptr = 0;
    private Patient store[];
    Semaphore spaces;
    Semaphore elements;

    WaitingRoom(int size) {
        this.size = size;
        store = new Patient[size];
        spaces = new Semaphore(size);
        elements = new Semaphore(0);
    }

    public void addPatient(Patient value) {
        synchronized (this) {
            store[inptr] = value;
            inptr = (inptr + 1) % size;
        }
    }

    public Patient removePatient() {
        Patient value;
        synchronized (this) {
            value = store[outptr];
            store[outptr] = null;
            outptr = (outptr + 1) % size;
        }
        return value;
    }

    public void produce(Patient value) {
        boolean normalPatient = value.getPatientId() != -1;
        synchronized (this) {
        if (spaces.tryAcquire(0)) {
            addPatient(value);
            if (normalPatient) {
                synchronized (System.out) {
                    System.out.println(value.getPatientName()+ " entered the waiting room.\n");
                }
            }
            elements.V();
            return;
        }

        if (normalPatient) {
            synchronized (System.out) {
                System.out.println("The waiting room is full.");
                System.out.println(value.getPatientName()+ " is waiting outside for an available seat.\n");
            }
        }
    }

    spaces.P();
    synchronized (this) {
        addPatient(value);
        if (normalPatient) {
            synchronized (System.out) {
                System.out.println(value.getPatientName()+ " entered the waiting room.\n");
            }
        }
        elements.V();
    }
}

    public Patient consume() {
        elements.P();
        return removePatient();
    }

    public void releaseSeat(){
        spaces.V();
    }
}
