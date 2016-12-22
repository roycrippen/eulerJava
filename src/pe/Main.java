package pe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.currentThread;

@SuppressWarnings("WeakerAccess")
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("Euler problems in Java...\n");

        ArrayList<Euler> allSolutions = P001_010.solutions;
        allSolutions.addAll(P011_020.solutions);
        allSolutions.addAll(P021_030.solutions);

        if (args.length != 0) {
            int problem = Integer.valueOf(args[0]);
            Euler oneSolution = getOneSolution(problem, allSolutions);
            allSolutions = new ArrayList<>(Collections.singletonList(oneSolution));
        }

        allSolutions
                .stream()
                .parallel()
                .forEach(s -> System.out.format("%-25s thread: [%s]\n", s.run(), currentThread().getName()));


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("done, complete in " + elapsedTime + " milliseconds");
    }

    private static Euler getOneSolution(int i, ArrayList<Euler> xs) {
        if (i > xs.size() || i < 1) {
            System.out.println("\nargument must be between 1 and " + (xs.size()));
            System.out.println("\naborting Euler problems");
            System.exit(1);
            return xs.get(0);
        }
        else
            return xs.get(i-1);
    }

}
