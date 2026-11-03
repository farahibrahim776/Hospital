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
        if (val <= 0) notify();
    }
    public synchronized boolean tryAcquire(long ms){
        val--;
        if (val<0){
            try {
                wait(ms);
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