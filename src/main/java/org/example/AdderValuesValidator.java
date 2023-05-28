package org.example;

public  class AdderValuesValidator {
    public static boolean validateAOrB(int aOrB, int n){
        if(Math.pow(2,n-1) >= aOrB) return false;
        return true;
    }

    public static boolean validateK(int k, int n){
        if(Math.pow(2,n-1)-1 >= k && k >= 3) return false;
        return true;
    }
}
