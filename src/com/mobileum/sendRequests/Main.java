package com.mobileum.sendRequests;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int threads = 100;

    public static void main(String[] args) throws IOException, InterruptedException {

        // Fix SSL
        SSLFix.execute();

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        String endpoint1 = "https://localhost:3001/";
        String endpoint2 = "https://localhost:3001/";
        String endpoint3 = "https://localhost:3001/";
        String endpoint4 = "https://localhost:3001/";
        String endpoint5 = "https://localhost:3001/";

        String[] endPoints = new String[]{
                endpoint1,
                endpoint2,
                endpoint3,
                endpoint4,
                endpoint5
        };

        int callsSize = 300;

        ArrayList<String> urls = new ArrayList<>();

        for (int i = 0; i < callsSize; i++){
            urls.add(endPoints[(int)Math.round(Math.random() * (endPoints.length - 1))]);
        }
        System.out.println(urls.toArray().length);

        for (String url : urls) {
            Runnable worker = new ApiCallRunnable(url);
            executor.execute(worker);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            // empty body
        }
        System.out.println("\nFinished all threads");
    }

}
