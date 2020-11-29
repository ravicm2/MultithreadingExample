package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Case Study 1
 */
public class CaseStudy1 {

    private static int MAX_PASSWORD = 99999;

    public static void main(String[] args) {

        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threadList = new ArrayList<>(2);
        threadList.add(new AscendingHackerThread(vault));
        threadList.add(new DescendingHackerThread(vault));
        threadList.add(new PoliceThread());

        threadList.forEach(Thread::start);
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isValidPassword(int guess) {

            //To slow down the hackers to get response
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.password == guess;
        }


    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Thread starting " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isValidPassword(guess)) {
                    System.out.println("Password guessed : " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= MAX_PASSWORD; guess--) {
                if (vault.isValidPassword(guess)) {
                    System.out.println("Password guessed : " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {

        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }

            System.out.println("You're arrested!!!");
            System.exit(0);
        }
    }
}


