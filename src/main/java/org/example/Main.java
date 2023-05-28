package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        int a,b,k;
        Scanner scanner = new Scanner(System.in);
        System.out.println("---Sumator 2^n - K---");
        System.out.println("Podaj n: ");
        int n = Integer.parseInt(scanner.nextLine());
        do{
            System.out.println("Podaj A: ");
            a = Integer.parseInt(scanner.nextLine());
        }while (AdderValuesValidator.validateAOrB(a,n));
        do{
            System.out.println("Podaj B: ");
            b = Integer.parseInt(scanner.nextLine());
        }while (AdderValuesValidator.validateAOrB(b,n));
        do{
            System.out.println("Podaj K: ");
            k = Integer.parseInt(scanner.nextLine());
        }while (AdderValuesValidator.validateK(k,n));

        AdderModuloK adderModuloK = new AdderModuloK(n,a,b,k);
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
    }
}