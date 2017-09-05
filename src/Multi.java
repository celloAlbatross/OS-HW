import java.util.Random;

class Theater{
    int seat;

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public synchronized void lockSeat(int randNum) {
        seat -= randNum;
    }

    public synchronized void releaseSeat(int randNum) {
        seat += randNum;
    }

    public synchronized boolean check(int num) {
        return (seat - num) >= 0;
    }

    public int getSeat() {
        return seat;
    }

}

class TicketMachine extends Thread{
    Random rand = new Random();
    public String myName;
    public Thread thread;
    public Theater[] c;
    private static final int MAX_MOV = 5;


    TicketMachine(String name, Theater[] c1){
        myName = name;
        c = c1;
//        System.out.println("Creating " + myName);
    }
    public void run(){
//        System.out.println("Running " + myName);
        try{
            while(true){
                int theRestOfTick = 0;
                boolean isLock = false;

                for (int i=0; i<MAX_MOV; i++){
                    theRestOfTick += c[i].seat;
                }

                int randMov = rand.nextInt(MAX_MOV);
                int randTick = rand.nextInt(MAX_MOV) + 1;
                int delay = (int)(Math.random()*500);

                isLock = c[randMov].check(randTick);

                if(isLock) {
                    c[randMov].lockSeat(randTick);
                    int x = c[randMov].seat;
//                    System.out.println("remain seat of theater after lock thearter " + randMov + " = " + x );
                }
                Thread.sleep(delay);

                int isBuy = rand.nextInt(2); //random are you buy?

                if(isBuy == 1 && isLock) {

                }else {
                    if(isLock) {
                        c[randMov].releaseSeat(randTick);
                    }
                }
                System.out.print(myName + " : ");
                for(int i=0; i<MAX_MOV; i++) {
                    System.out.print(c[i].seat + " ");
                }
                System.out.println();
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
        Theater[] c = new Theater[MAX_MOV];

        for (int i=0; i<MAX_MOV; i++) {
            c[i] = new Theater();
        }

        System.out.println("debug");
        System.out.println(c[0].seat);

        for(int i=0; i<MAX_MOV; i++) {
            c[i].setSeat(MAX_SEAT);
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
