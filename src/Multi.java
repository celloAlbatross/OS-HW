import java.util.Random;

class Theater{
    int seat;
    int randNum;

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public synchronized boolean lockSeat(int randNum) {
        if(seat - randNum >= 0) {
            this.randNum = randNum;
            seat -= randNum;
            return true;
        }
        return  false;
    }

    public synchronized void releaseSeat(int randNum) {
        seat += this.randNum;
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
        System.out.println("Creating " + myName);
    }
    public void run(){
        System.out.println("Running " + myName);
        try{
            while(true){
                int theRestOfTick = 0;
                boolean isLock = false;

                for (int i=0; i<MAX_MOV; i++){
                    theRestOfTick += c[i].seat;
                }

                int randMov = rand.nextInt(MAX_MOV);
                int randTick = rand.nextInt(5) + 1;
                int delay = (int)(Math.random()*500);

                isLock = c[randMov].lockSeat(randTick);
                if(isLock) {
                    System.out.println(myName + " lock " + randMov + " for " + randTick);
                    System.out.println("remain seat of theater " + randMov + " = " + c[randMov].seat);
                }
                Thread.sleep(delay);

                int isBuy = rand.nextInt(2); //random are you buy?

                if(isBuy == 1 && isLock) {
//                    System.out.println(myName + " buy " + randMov + " for " + randTick);
                }else {
                    if(isLock) {
                        c[randMov].releaseSeat(randTick);
                        System.out.println(myName + " Release " + randMov + " for " + randTick);
                    }
                }
                if(isLock && isBuy == 1) {
//                    System.out.print(myName + " : ");
//                    for (int i = 0; i < MAX_MOV; i++) {
//                        System.out.print(c[i].seat + " ");
//                    }
//                    System.out.println();
                    System.out.println(myName + " Buy " + randMov + " for " + randTick);
                    System.out.println("remain seat of theater " + randMov + " = " + c[randMov].seat);
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
