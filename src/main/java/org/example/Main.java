package org.example;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) throws IOException {

        int a, b, k, choice;
        int n;
        int result;
        AdderModuloK adderModuloK;
        int[] vectorK;
        int tempK;
        int maxN;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("MENU");
            System.out.println("1.---Sumator 2^n - K---");
            System.out.println("2.---Smator 2^n + K---");
            System.out.println("3.---testy - K---");
            System.out.println("4.---testy + K---");
            System.out.println("0.---koniec---");
            System.out.println("");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Podaj n: ");
                    n = scanner.nextInt();
                    do {

                        System.out.println("Podaj K z przedzialu od 3 do " + ((int) Math.pow(2, n - 1) - 1) + ": ");
                        k = scanner.nextInt();

                    } while (AdderValuesValidator.validateK(k, n));

                    vectorK = new int[n];
                    tempK = k;

                    for (int i = 0; i < n; i++) {

                        vectorK[i] = tempK % 2;
                        tempK /= 2;

                    }

                    result = 0;

                    for (int i = 0; i < n; i++) {
                        result += vectorK[i] * Math.pow(2, i);
                    }

                    result = (int) Math.pow(2, n) - result;
                    System.out.println("Modulo: " + result);

                    do {
                        System.out.println("Podaj A z przedzialu od 0 do " + (result - 1) + ": ");
                        a = scanner.nextInt();
                    } while (a < 0 || a >= result);
                    do {
                        System.out.println("Podaj B z przedzialu od 0 do " + (result - 1) + ": ");
                        b = scanner.nextInt();
                    } while (b < 0 || b >= result);


                    adderModuloK = new AdderModuloK(n, a, b, k);
                    adderModuloK.createABKVectors();
                    adderModuloK.createABPrimVectors();
                    adderModuloK.createHPGVectors();
                    adderModuloK.createHPGPrimVectors();
                    adderModuloK.displayVectors();


                    adderModuloK.generateBlackDotsStructure();
                    adderModuloK.displayBlackDotchStructure();

                    adderModuloK.createGAndP();
                    adderModuloK.createC();
                    adderModuloK.getResult();
                    adderModuloK.displayResults();

                    break;
                case 2:

                    System.out.println("Podaj n: ");
                    n = scanner.nextInt() + 1;
                    do {

                        System.out.println("Podaj K z przedzialu od 3 do " + ((int) Math.pow(2, n - 2) - 1) + ": ");
                        k = scanner.nextInt();

                    } while (AdderValuesValidator.validateK(k, n));

                    vectorK = new int[n];
                    tempK = k;
                    int[] vectorNegationK = new int[n];
                    int tempNegationK = (int) Math.pow(2, n - 1) - k;
                    for (int i = 0; i < n; i++) {

                        vectorK[i] = tempK % 2;
                        tempK /= 2;

                        vectorNegationK[i] = tempNegationK % 2;
                        tempNegationK /= 2;

                    }

                    result = 0;

                    for (int i = 0; i < n; i++) {
                        result += vectorK[i] * Math.pow(2, i);
                    }

                    result = (int) Math.pow(2, n - 1) + result;
                    System.out.println("Modulo: " + result);

                    do {
                        System.out.println("Podaj A z przedzialu od 0 do " + (result - 1) + ": ");
                        a = scanner.nextInt();
                    } while (a < 0 || a >= result);
                    do {
                        System.out.println("Podaj B z przedzialu od 0 do " + (result - 1) + ": ");
                        b = scanner.nextInt();
                    } while (b < 0 || b >= result);


                    adderModuloK = new AdderModuloK(n, a, b, (int) Math.pow(2, n - 1) - k);
                    adderModuloK.createABKVectors();
                    adderModuloK.createABPrimVectors();
                    adderModuloK.createHPGVectors();
                    adderModuloK.createHPGPrimVectors();
                    adderModuloK.displayVectors();


                    adderModuloK.generateBlackDotsStructure();
                    adderModuloK.displayBlackDotchStructure();

                    adderModuloK.createGAndP();
                    adderModuloK.createC();
                    adderModuloK.getResult();
                    adderModuloK.displayResults();

                    break;
                case 3:
                    System.out.println("podaj max n: ");
                    maxN = scanner.nextInt();
                    minusKTests(maxN);
                    break;
                case 4:
                    System.out.println("podaj max n: ");
                    maxN = scanner.nextInt();
                    plusKTests(maxN);
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("podałeś zły numer");
            }
        }

    }

    private static void minusKTests(int maxN) throws IOException {

        FileWriter writer = null;

        try {
            writer = new FileWriter("wynikiMinusK.txt");
        } catch (IOException e) {
            System.out.println("błąd w zapisie pliku");
        }

        BufferedWriter bufferWriter = new BufferedWriter(writer);

        int a, b, k;
        int n;
        int result;
        AdderModuloK adderModuloK;
        int j=0;


        int i,counter;

        BitSet excludedPiostionVectorH;
        BitSet excludedPiostionVectorG;
        BitSet excludedPiostionVectorP;

        for (n = 4; n <= maxN; n++) {
            System.out.println(n);
            for (k = 3; k <= Math.pow(2, n - 1) - 1; k++) {
                result = (int) Math.pow(2, n) - k;

                excludedPiostionVectorG = new BitSet();
                excludedPiostionVectorH = new BitSet();
                excludedPiostionVectorP = new BitSet();

                counter = 0;

                for (a = 0; a <= result - 1 && counter < 3*n; a++) {

                    for (b = 0; b <= result - 1 && counter < 3*n; b++) {

                        adderModuloK = new AdderModuloK(n, a, b, k);
                        adderModuloK.createABKVectors();
                        adderModuloK.createABPrimVectors();
                        adderModuloK.createHPGVectors();
                        adderModuloK.createHPGPrimVectors();

                        for (i = 0; i < n; i++) {
                            if (!excludedPiostionVectorG.get(i) && adderModuloK.getVectorG()[i] != adderModuloK.getVectorGPrim()[i]) {
                                excludedPiostionVectorG.set(i);
                                counter++;
                            }

                            if (!excludedPiostionVectorH.get(i) && adderModuloK.getVectorH()[i] != adderModuloK.getVectorHPrim()[i]) {
                                excludedPiostionVectorH.set(i);
                                counter++;
                            }
                            if (!excludedPiostionVectorP.get(i) && adderModuloK.getVectorP()[i] != adderModuloK.getVectorPPrim()[i]) {
                                excludedPiostionVectorP.set(i);
                                counter++;
                            }

                        }
                    }

                }
                for (i = 0; i < n; i++) {
                    if (!excludedPiostionVectorH.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora H: ");
                        bufferWriter.write(i + " ");
                    }
                }
                for (i = 0; i < n; i++) {

                    if (!excludedPiostionVectorG.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora G: ");
                        bufferWriter.write(i + " ");
                    }
                }
                for (i = 0; i < n; i++) {
                    if (!excludedPiostionVectorP.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora P: ");
                        bufferWriter.write(i + " ");
                    }
                }
            }

        }


        bufferWriter.close();
    }



    private static void plusKTests(int maxN) throws IOException {

        FileWriter writer = null;

        try {
            writer = new FileWriter("wynikiPlusK.txt");
        } catch (IOException e) {
            System.out.println("błąd w zapisie pliku");
        }

        BufferedWriter bufferWriter = new BufferedWriter(writer);

        int a, b, k;
        int n;
        int result;
        AdderModuloK adderModuloK;
        int j=0;


        int i,counter;

        BitSet excludedPiostionVectorH;
        BitSet excludedPiostionVectorG;
        BitSet excludedPiostionVectorP;

        for (n = 3; n <= maxN; n++) {
            System.out.println(n);
            for (k = 3; k <= Math.pow(2, n - 1) - 1; k++) {
                result = (int) Math.pow(2, n) + k;

                excludedPiostionVectorG = new BitSet();
                excludedPiostionVectorH = new BitSet();
                excludedPiostionVectorP = new BitSet();
                counter = 0;

                for (a = 0; a <= result - 1 && counter < 3*(n+1); a++) {

                    for (b = 0; b <= result - 1 && counter < 3*(n+1); b++) {

                        adderModuloK = new AdderModuloK(n+1, a, b, (int) Math.pow(2, n) - k);
                        adderModuloK.createABKVectors();
                        adderModuloK.createABPrimVectors();
                        adderModuloK.createHPGVectors();
                        adderModuloK.createHPGPrimVectors();

                        for (i = 0; i <= n; i++) {
                            if( !excludedPiostionVectorG.get(i) && adderModuloK.getVectorG()[i] != adderModuloK.getVectorGPrim()[i]){
                                excludedPiostionVectorG.set(i);
                                counter++;

                            }

                            if(!excludedPiostionVectorH.get(i) && adderModuloK.getVectorH()[i] != adderModuloK.getVectorHPrim()[i]){
                                excludedPiostionVectorH.set(i);
                                counter++;
                            }
                            if (!excludedPiostionVectorP.get(i) && adderModuloK.getVectorP()[i] != adderModuloK.getVectorPPrim()[i] ){
                                excludedPiostionVectorP.set(i);
                                counter++;
                            }

                        }


                    }

                }
                for (i = 0; i <=n; i++) {
                    if (!excludedPiostionVectorH.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora H: ");
                        bufferWriter.write(i + " ");
                    }
                }
                for (i = 0; i <=n; i++) {

                    if (!excludedPiostionVectorG.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora G: ");
                        bufferWriter.write(i + " ");
                    }
                }
                for (i = 0; i <=n; i++) {
                    if (!excludedPiostionVectorP.get(i)) {
                        bufferWriter.write("\n");
                        bufferWriter.write("n: " + n +" K: " +k+ "\n" + "takie same indeksy dla wektora P: ");
                        bufferWriter.write(i + " ");
                    }
                }
            }

        }


        bufferWriter.close();
    }


}