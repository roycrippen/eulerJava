package pe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

@SuppressWarnings("WeakerAccess")
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        System.out.println("Euler problems in Java...\n");

        ArrayList<String> allSolutions = P001_010.solutions;
        allSolutions.addAll(P011_020.solutions);
        allSolutions.addAll(P021_030.solutions);

        if (args.length != 0) {
            int problem = Integer.valueOf(args[0]);
            String oneSolution = getOneSolution(problem, allSolutions);
            allSolutions = new ArrayList<>(Collections.singletonList(oneSolution));
        }

        ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

        allSolutions
                .forEach(solution -> futures.add(CompletableFuture.runAsync(() -> System.out.println(solution))));

        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
        //noinspection StatementWithEmptyBody
        while (!CompletableFuture.allOf(futuresArray).isDone()) { }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("done, complete in " + elapsedTime + " milliseconds");
    }

    private static String getOneSolution(int i, ArrayList<String> xs) {
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
