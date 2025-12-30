package se.deved.utility;

import java.util.Scanner;

//A unified scanner

public class ScannerHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }
}