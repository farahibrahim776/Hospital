  public class Semaphore{
    protected int val;
    public Semaphore(){
        val = 0;
    }
    public Semaphore(int init){
        val = init;
    }
    public synchronized void P(){
        val--;
        if (val < 0){
            try {wait();} 
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void V(){
        val++;
        if (val <= 0) notifyAll();
    }
    public synchronized boolean tryAcquire(long ms){
      long startTime = System.currentTimeMillis();
        long timeLeft = ms;
        val--;
        if (val<0){
            try {
               while (val < 0 && timeLeft > 0) {
                    wait(timeLeft);
                    timeLeft = ms - (System.currentTimeMillis() - startTime);
                }
                //wait(ms);
                if (val <0){
                    val++;
                    return false;
                }
            } 
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
                val++;
                return false;
            }
        }
        return true;

    }
}
