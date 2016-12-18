package pe;

import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Euler problems in Java...\n");

        long startTime = System.currentTimeMillis();

//        Stream<String> allSolutions = P001_010.solutions;
//        allSolutions = Stream.concat(allSolutions, P011_020.solutions);

        Stream<String> allSolutions = P011_020.solutions;

        allSolutions
                .parallel()
                .forEach(s -> System.out.format("%-25s thread: [%s]\n", s, currentThread().getName()));

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("done, complete in " + elapsedTime + " milliseconds");
    }

}
