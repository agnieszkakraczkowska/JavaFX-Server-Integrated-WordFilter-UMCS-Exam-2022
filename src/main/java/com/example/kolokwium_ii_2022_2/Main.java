package com.example.kolokwium_ii_2022_2;

public class Main {
    public static void main(String[] args) {
        WordBag wordBag = new WordBag();
        wordBag.populate();
        Server server = new Server(5000, wordBag);
        server.start();
        server.startSending();
    }
}