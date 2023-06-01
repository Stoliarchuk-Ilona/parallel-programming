package org.example.symbols;

public class Main {
    public static void main(String[] args) {
//        noSyncSymbol();
        syncSymbol();
    }

    public static void noSyncSymbol() {
        SymbolNoSyncThread symbolNoSyncThread = new SymbolNoSyncThread('-');
        SymbolNoSyncThread symbolNoSyncThread1 = new SymbolNoSyncThread('|');
        symbolNoSyncThread.start();
        symbolNoSyncThread1.start();
    }

    public static void syncSymbol() {
        Lock lock = new Lock(2);
        SymbolSyncThread symbolSyncThread = new SymbolSyncThread(lock, '-', 0);
        SymbolSyncThread symbolSyncThread1 = new SymbolSyncThread(lock, '|', 1);
        symbolSyncThread.start();
        symbolSyncThread1.start();
    }
}
