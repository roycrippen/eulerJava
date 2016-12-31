package pe;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        System.out.println("Euler problems in Java...\n");

        TreeMap<Integer, Supplier<String>> allSolutions = P001_010.loadSolutionMap();
        allSolutions.putAll(P011_020.loadSolutionMap());
        allSolutions.putAll(P021_030.loadSolutionMap());

        if (args.length != 0) {
            Supplier<String> oneSolution = Utils.getOneSolution(args[0], allSolutions);
            allSolutions.clear();
            allSolutions.put(0, oneSolution);
        }

        NavigableMap<Integer, Supplier<String>> descendingSolutions = allSolutions.descendingMap();
        ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

        descendingSolutions
                .forEach((k, solution) -> futures.add(CompletableFuture.runAsync(() -> System.out.println(solution.get()))));

        // holds here until all futures return
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
        CompletableFuture.allOf(futuresArray).join();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("\ndone, completed " + allSolutions.size() + " problems in " + elapsedTime + " milliseconds");

    }


}
