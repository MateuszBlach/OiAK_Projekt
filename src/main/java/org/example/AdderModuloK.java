package org.example;

import javax.swing.text.DefaultEditorKit;
import java.util.Arrays;

public class AdderModuloK {
    private int n;
    private int a;
    private int b;
    private int k;

    private int[] vectorK;
    private int[] vectorA;
    private int[] vectorB;
    private int[] vectorH;
    private int[] vectorP;
    private int[] vectorG;
    private int[] vectorC;
    private int[] vectorAPrim;
    private int[] vectorBPrim;
    private int[] vectorHPrim;
    private int[] vectorPPrim;
    private int[] vectorGPrim;
    private int[] vectorCPrim;
    private int[] vectorS;
    private int numberOfFloors;
    private  int[][] dots;

    private int[][] G;
    private int[][] GPrim;
    private int[][] P;
    private int[][] PPrim;

    private  int Cout;
    public AdderModuloK(int n, int a, int b, int k) {
        this.n = n;
        this.a = a;
        this.b = b;
        this.k = k;
    }

    public void createABKVectors(){
        vectorA = new int[n];
        vectorB = new int[n];
        vectorK = new int[n];
        int tempA = this.a;
        int tempB = this.b;
        int tempK = this.k;
        for(int i = 0; i < n;i++) {
            vectorA[i] = tempA % 2;
            tempA /= 2;

            vectorB[i] = tempB % 2;
            tempB /= 2;

            vectorK[i] = tempK % 2;
            tempK /= 2;
        }
    }
    public void createABPrimVectors(){
        vectorAPrim = new int[n];
        vectorBPrim = new int[n+1];
        vectorBPrim[0] = 0;
        for(int i = 0; i < n; i++){
            if(vectorK[i] == 0){
                vectorAPrim[i] = (vectorA[i] == 1)^(vectorB[i] == 1) ? 1:0;
                vectorBPrim[i+1] = (vectorA[i] == 1)&&(vectorB[i] == 1) ? 1:0;
            }else{
                vectorAPrim[i] = (vectorA[i] == 1)==(vectorB[i] == 1) ? 1:0;
                vectorBPrim[i+1] = (vectorA[i] == 1)||(vectorB[i] == 1) ? 1:0;
            }
        }
    }
    public void createHPGVectors(){
        vectorH = new int[n];
        vectorP = new int[n];
        vectorG = new int[n];
        for(int i = 0; i < n;i++){
            vectorH[i] = (vectorA[i] == 1)^(vectorB[i] == 1) ? 1:0;
            vectorP[i] = (vectorA[i] == 1)||(vectorB[i] == 1) ? 1:0;
            vectorG[i] = (vectorA[i] == 1)&&(vectorB[i] == 1) ? 1:0;
        }
    }
    public void createHPGPrimVectors(){
        vectorHPrim = new int[n];
        vectorPPrim = new int[n];
        vectorGPrim = new int[n];
        for(int i = 0; i < n;i++) {
            vectorHPrim[i] = (vectorAPrim[i] == 1) ^ (vectorBPrim[i] == 1) ? 1 : 0;
            vectorPPrim[i] = (vectorAPrim[i] == 1) || (vectorBPrim[i] == 1) ? 1 : 0;
            vectorGPrim[i] = (vectorAPrim[i] == 1) && (vectorBPrim[i] == 1) ? 1 : 0;
        }
    }

    public void createGAndP(){
        G = new int[n][numberOfFloors];
        P = new int[n][numberOfFloors];
        GPrim = new int[n][numberOfFloors];
        PPrim = new int[n][numberOfFloors];
        for(int floorNumber = 0; floorNumber < numberOfFloors ; floorNumber++){
            for(int cell = 0; cell < n; cell++){
                if(dots[cell][floorNumber] == 1){
                    if(floorNumber == 0){
                        G[cell][floorNumber] = (vectorG[cell] == 1) || ((vectorP[cell] == 1) && (vectorG[cell-1] == 1)) ? 1:0;
                        P[cell][floorNumber] = (vectorP[cell] == 1) && (vectorP[cell-1] == 1) ? 1:0;

                        GPrim[cell][floorNumber] = (vectorGPrim[cell] == 1) || ((vectorPPrim[cell] == 1) && (vectorGPrim[cell-1] == 1)) ? 1:0;
                        PPrim[cell][floorNumber] = (vectorPPrim[cell] == 1) && (vectorPPrim[cell-1] == 1) ? 1:0;
                    }else{
                        //trzeba obliczyc indeks kropki czarnej powyzej
                        int above = 0;
                        for(int f = floorNumber-1; f >= 0; f--){
                            if(dots[cell][f] == 1){
                                above = f;
                                break;
                            }
                        }
                        //trzeba obliczyc indeks czarnej kropki po prawo poziom jeden nizej
                        int right = 0;
                        int buffer = (int)Math.pow(2,floorNumber-1);
                        int b = (int)Math.pow(2,floorNumber);
                        for(int r = cell-buffer; r >= 0; r--){
                            if(dots[r][floorNumber-1] == 1){
                                right = r;
                                break;
                            }
                        }
                        if(above == 0 && dots[cell][above] == 0){
                            G[cell][floorNumber] = (vectorG[cell] == 1) || ((vectorP[cell] == 1) && (G[right][floorNumber-1] == 1)) ? 1:0;
                            P[cell][floorNumber] = (vectorP[cell] == 1) && (P[right][floorNumber-1] == 1) ? 1:0;
                            GPrim[cell][floorNumber] = (vectorGPrim[cell] == 1) || ((vectorPPrim[cell] == 1) && (GPrim[right][floorNumber-1] == 1)) ? 1:0;
                            PPrim[cell][floorNumber] = (vectorPPrim[cell] == 1) && (PPrim[right][floorNumber-1] == 1) ? 1:0;
                        }else{
                            G[cell][floorNumber] = (G[cell][above] == 1) || ((P[cell][above] == 1) && (G[right][floorNumber-1] == 1)) ? 1:0;
                            P[cell][floorNumber] = (P[cell][above] == 1) && (P[right][floorNumber-1] == 1) ? 1:0;

                            GPrim[cell][floorNumber] = (GPrim[cell][above] == 1) || ((PPrim[cell][above] == 1) && (GPrim[right][floorNumber-1] == 1)) ? 1:0;
                            PPrim[cell][floorNumber] = (PPrim[cell][above] == 1) && (PPrim[right][floorNumber-1] == 1) ? 1:0;
                        }
                    }
                }
            }
        }
    }

    public void createC(){
        vectorC = new int[n];
        vectorCPrim = new int[n];

        vectorC[0] = vectorG[0];
        vectorCPrim[0] = vectorGPrim[0];


        for(int i = 1; i < n; i++){
            int blackNodeLocation = -1;
            for(int b = numberOfFloors-1; b >= 0; b--) {
                if (dots[i][b] == 1) {
                    blackNodeLocation = b;
                    break;
                }
            }
            vectorC[i] = G[i][blackNodeLocation];
            vectorCPrim[i] = GPrim[i][blackNodeLocation];
        }
    }

    public void getResult(){
        vectorCPrim[n-1] = (vectorCPrim[n-1] == 1) || (vectorBPrim[n] == 1) ? 1:0;
        GPrim[n-1][numberOfFloors-1] = vectorCPrim[n-1];
        Cout = vectorCPrim[n-1];
        vectorS = new int[n];

        for(int i = 0; i < n; i++){
            if(Cout == 1){
                if(i == 0){
                    vectorS[0] = vectorHPrim[0];
                }else{
                    vectorS[i] = (vectorHPrim[i] == 1) ^ (vectorCPrim[i-1] == 1) ? 1:0;
                }
            }else{
                if(i == 0){
                    vectorS[0] = vectorH[0];
                }else{
                    vectorS[i] = (vectorH[i] == 1) ^ (vectorC[i-1] == 1) ? 1:0;
                }
            }
        }

    }
    public void displayVectors(){
        System.out.println("A = "+Arrays.toString(vectorA));
        System.out.println("B = "+Arrays.toString(vectorB));
        System.out.println("K = "+Arrays.toString(vectorK));
        System.out.println("A' = "+Arrays.toString(vectorAPrim));
        System.out.println("B' = "+Arrays.toString(vectorBPrim));
        System.out.println("H = "+Arrays.toString(vectorH));
        System.out.println("P = "+Arrays.toString(vectorP));
        System.out.println("G = "+Arrays.toString(vectorG));
        System.out.println("H' = "+Arrays.toString(vectorHPrim));
        System.out.println("P' = "+Arrays.toString(vectorPPrim));
        System.out.println("G' = "+Arrays.toString(vectorGPrim));
    }
    public void generateBlackDotsStructure(){
        numberOfFloors = (int)Math.ceil(Math.log(n)/Math.log(2));
        dots = new int[n][numberOfFloors];
        int border = 1;
        int borderCounter = 0;
        int pom = 0;
        for(int j = 0; j < numberOfFloors; j++){
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
    }
    public void displayBlackDotchStructure(){
        System.out.println("---Struktura czarnych kropek---");
        for(int j = 0; j < numberOfFloors; j++){
            for(int i = 0; i<n;i++){
                System.out.print(dots[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    public void displayResults(){
        System.out.println("---P---");
        for(int i = 0; i < numberOfFloors;i++){
            for(int j =0; j<n ;j++){
                System.out.print(" "+P[j][i]);
            }
            System.out.print("\n");
        }
        System.out.println("---G---");
        for(int i = 0; i < numberOfFloors;i++){
            for(int j =0; j<n ;j++){
                System.out.print(" "+G[j][i]);
            }
            System.out.print("\n");
        }
        System.out.println("---P'---");
        for(int i = 0; i < numberOfFloors;i++){
            for(int j =0; j<n ;j++){
                System.out.print(" "+PPrim[j][i]);
            }
            System.out.print("\n");
        }
        System.out.println("---G'---");
        for(int i = 0; i < numberOfFloors;i++){
            for(int j =0; j<n ;j++){
                System.out.print(" "+GPrim[j][i]);
            }
            System.out.print("\n");
        }
        System.out.println("C = "+Arrays.toString(vectorC));
        System.out.println("C' = "+Arrays.toString(vectorCPrim));
        System.out.println("Cout = "+Cout);
        System.out.println("S = "+Arrays.toString(vectorS));

        System.out.println(a+"+"+b+"mod"+calculateModulo()+"="+calculateS());
    }

    public int calculateModulo(){
        int result = 0;
        for(int i = 0; i < n; i++){
            result += vectorK[i]*Math.pow(2,i);
        }
        result = (int)Math.pow(2,n) - result;
        return result;
    }
    public int calculateS(){
        int result = 0;
        for(int i = 0; i < n; i++){
            result += vectorS[i]*Math.pow(2,i);
        }
        return result;
    }

    public int[] getVectorH() {
        return vectorH;
    }

    public int[] getVectorP() {
        return vectorP;
    }

    public int[] getVectorG() {
        return vectorG;
    }

    public int[] getVectorHPrim() {
        return vectorHPrim;
    }

    public int[] getVectorPPrim() {
        return vectorPPrim;
    }

    public int[] getVectorGPrim() {
        return vectorGPrim;
    }
}
