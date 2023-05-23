package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int k = (int)Math.ceil(Math.log(n)/Math.log(2));
        System.out.println(n+" "+k);
        int[][] dots = new int[n][k];

        int border = 1;
        int borderCounter = 0;
        int pom = 0;
        for(int j = 0; j < k; j++){
            for(int i = 0; i<n;i++){
                if(pom < border){
                    dots[i][j] = 0;
                    pom++;
                }else{
                    if(borderCounter < border){
                        dots[i][j] = 1;
                        borderCounter++;
                    }else{
                        borderCounter = 0;
                        pom = 1;
                    }
                }
            }
            borderCounter = 0;
            pom = 0;
            border*=2;
        }

        for(int j = 0; j < k; j++){
            for(int i = 0; i<n;i++){
                System.out.print(dots[i][j]+" ");
            }
            System.out.print("\n");
        }

    }
}