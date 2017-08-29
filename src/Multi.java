import java.util.Random;

class Theater{
    int[] theater = new int[5];
}

class TicketMachine extends Thread{
    Random rand = new Random();
    public String myName;
    public Thread thread;
    public Theater c;
    private static final int MAX_MOV = 5;


    TicketMachine(String name, Theater c1){
        myName = name;
        c = c1;
        System.out.println("Creating " + myName);
    }
    public void run(){
        System.out.println("Running " + myName);
        try{
            while(true){
                int theRestOfTick = 0;
                boolean isLock = false;
                for (int i=0; i<MAX_MOV; i++){
                    theRestOfTick += c.theater[i];
                }
                int randMov = rand.nextInt(MAX_MOV);
                int randTick = rand.nextInt(5) + 1;
                int delay = (int)(Math.random()*500);
                if(c.theater[randMov] - randTick >= 0) {
                    c.theater[randMov] -= randTick;
                    isLock = true;
                }
                Thread.sleep(delay);
                int isBuy = rand.nextInt(2);

                if(isBuy == 1) {
//                    Thread.sleep(delay);

                    for(int i=0; i<MAX_MOV; i++) {
                        System.out.println("Theater " + (i+1) + ": " + c.theater[i]);
                    }
                }else {
                    if(isLock)
                        c.theater[randMov] += randTick;
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Thread " + myName + " interrupted.");
        }
        System.out.println("Thread " + myName + " exiting.");
    }

    public void start(){
        System.out.println("Start " + myName);
        if(thread == null){
            thread = new Thread(this, myName);
            thread.start();
        }

    }
}

public class Multi {
    private static final int MAX_SEAT = 20;
    private static final int MAX_MOV = 5;
    public static void main(String args[]){
        Theater c = new Theater();
        for(int i=0; i<MAX_MOV; i++) {
            c.theater[i] = MAX_SEAT;
        }
        TicketMachine t1 = new TicketMachine("t1", c);
        TicketMachine t2 = new TicketMachine("t2", c);
        TicketMachine t3 = new TicketMachine("t3", c);
        TicketMachine t4 = new TicketMachine("t4", c);
        TicketMachine t5 = new TicketMachine("t5", c);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
