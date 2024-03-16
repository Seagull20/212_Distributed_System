import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class network {
    List<processor> network = new ArrayList<processor>();
    int taskNumber;

    public network(boolean isMainRing, int taskNumber) {
        System.out.println("network constructor Called");
        int N = 0;// # of totoal processor
        int n = 0;// # of interface processors
        Scanner sc = new Scanner(System.in);
        System.out.println(("input the size of the network (1 to 1100)"));
        try {
            N = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        if (N > 1100 || N < 0) {
            throw new IllegalArgumentException("size of net should in 1 to 1100");
        }
        if (isMainRing) {
            System.out.println(("input the number of the interface processor (1 to " + N + " )"));
            try {
                n = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
            if (n > N || n < 0) {
                throw new IllegalArgumentException("size of net should in 1 to " + N + " )");
            }
            switch (taskNumber) {
                case 1:
                network = networkGnerator(N, n, isMainRing, taskNumber);
                    break;
                case 2:
                network = networkGnerator(N, n, isMainRing, taskNumber);
                    break;
                default:
                    break;
            }
        } else {
            network = networkGnerator(N, n, isMainRing, taskNumber);
        }

    }

    

    // generate network
    List<processor> networkGnerator(int N, int n, boolean isMainRing, int taskNumber) {
        System.out.println("networkGenrator Called");
        List<Integer> uniqueIDs = IntStream.rangeClosed(1, 3 * N).boxed().collect(Collectors.toList());
        Random random = new Random();
        List<processor> processors = new ArrayList<>();

        // Shuffle for random unique ID assignment
        Collections.shuffle(uniqueIDs);
        if (isMainRing) {
            switch (taskNumber) {
                case 1: // generate processor with different startround
                    for (int i = 0; i < N; i++) {
                        int startRound = 1; // Default start round
                        if (i < n) { // Random start round for 'n' processors
                            startRound = 2 + random.nextInt(N-1); // Assuming start round ranges from 1 to N for
                                                                // simplicity
                        }
                        int id = uniqueIDs.get(i);
                        processors.add(new processor(id, startRound));
                    }
                    break;
                case 2:
                    for (int i = 0; i < N; i++) {
                        int id = uniqueIDs.get(i);
                        if (i < n) { // Random start round for 'n' processors, interface processor selected here
                            id = -1;
                        }
                        processors.add(new processor(id, 1));
                    }
                    break;
                default:
                    break;
                }
        }else{//normal ring
            for (int i = 0; i < N; i++) {
                int id = uniqueIDs.get(i);
                processors.add(new processor(id, 1));
            }
        }
        Collections.shuffle(processors);//random position of interface
        // Link processors to form a ring
        for (int i = 0; i < N; i++) {
            processor current = processors.get(i);
            processor next = processors.get((i + 1) % N); // Circular link
            current.setNextProcessor(next);
        }

        return processors;
    }

    int getsize(){
        return network.size();
    }

    int getMaxID(){
        int max=0;
        for (processor processor : network) {
            if (processor.getId()>max) {
                max= processor.getId();
            }
        }
        return max;
    }

    List<processor> getList(){
        return network;
    }
}