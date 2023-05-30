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
            System.out.println("Podaj K z przedzialu od 3 do "+((int)Math.pow(2,n-1)-1)+": ");
            k = Integer.parseInt(scanner.nextLine());
        }while (AdderValuesValidator.validateK(k,n));

        int[] vectorK = new int[n];
        int tempK = k;
        for(int i = 0; i < n;i++) {
            vectorK[i] = tempK % 2;
            tempK /= 2;
        }
        int result = 0;
        for(int i = 0; i < n; i++){
            result += vectorK[i]*Math.pow(2,i);
        }
        result = (int)Math.pow(2,n) - result;
        System.out.println("Modulo: "+result);

        do{
            System.out.println("Podaj A z przedzialu od 0 do "+(result-1)+": ");
            a = Integer.parseInt(scanner.nextLine());
        }while (a < 0 || a >= result);
        do{
            System.out.println("Podaj B z przedzialu od 0 do "+(result-1)+": ");
            b = Integer.parseInt(scanner.nextLine());
        }while (b < 0 || b >= result);


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