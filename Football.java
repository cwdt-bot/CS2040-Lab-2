/**
 * Name         :
 * Matric. No   :
*/

import java.util.*;

public class Football {
    private void run() {
        Scanner sc = new Scanner(System.in);
        int numPlayers = sc.nextInt();
        Pair[] instruct = new Pair[numPlayers];
        boolean noZero = true;
        //create the instructions
        for (int n = 0; n < numPlayers; n++) {
            int currIn = sc.nextInt();
            instruct[n] = new Pair(currIn);
            if (currIn == 0) {
                noZero = false;
            }
        }
        //if there are no zeroes, straight away NO
        if (noZero) {
            System.out.println("NO");
            return;
        }

        Pair next;
        Pair first  = instruct[0];
        Pair curr = first;
        while (true) {
            next = instruct[curr.get()];
            if (curr.visited()) {
                if (curr.equals(first)) {
                    System.out.println("YES");
                    return;
                } else {
                    System.out.println("NO");
                    return;
                }
            }
            curr.flip();
            curr = next;
        }
    }

    public static void main(String args[]) {
        Football football = new Football();
        football.run();
    }
}

class Pair {
    private final int value;
    private boolean visited = false;

    public Pair(int value) {
        this.value = value;
    }

    public boolean visited() {
        return this.visited;
    }

    public void flip() {
        this.visited = true;
    }

    public int get() {
        return this.value;
    }
}
