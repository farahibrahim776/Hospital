  public class Semaphore{
    protected int val;
    public Semaphore(){
        val = 0;
    }
    public Semaphore(int init){
        val = init;
    }
    public synchronized void P(){

        boolean interrupted = false;
        while (val == 0) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                interrupted = true;
            }
        }

        val--;
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }
    public synchronized void V(){
        val++;
        notifyAll();
    }
    public synchronized boolean tryAcquire(long ms){
      long startTime = System.currentTimeMillis();
         while (val == 0) {

            long timeLeft = ms -
                    (System.currentTimeMillis() - startTime);

            if (timeLeft <= 0) {
                return false;
            }

            try {
                wait(timeLeft);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        val--;
        return true;
    }
}
