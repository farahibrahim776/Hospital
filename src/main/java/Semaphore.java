  public class Semaphore{
    protected int val;
    public Semaphore(){
        val = 0;
    }
    public Semaphore(int init){
        val = init;
    }
    public synchronized void P(){
        // val--;
        // if (val < 0){
        //     try {wait();} 
        //     catch (InterruptedException e){
        //         Thread.currentThread().interrupt();
        //     }
        // }

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
        // if (val <= 0)
    }
    public synchronized boolean tryAcquire(long ms){
      long startTime = System.currentTimeMillis();

        // long timeLeft = ms;
        // if (val<0){
        //     try {
        //         while (val < 0 && timeLeft > 0) {
        //             wait(timeLeft);
        //             timeLeft = ms - (System.currentTimeMillis() - startTime);
        //         }
        //         //wait(ms);
        //         if (val <0){
        //             val++;
        //             return false;
        //         }
        //     } 
        //     catch (InterruptedException e){
        //         Thread.currentThread().interrupt();
        //         val++;
        //         return false;
        //     }
        // }

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
