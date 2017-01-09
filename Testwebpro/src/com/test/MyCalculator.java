package com.test;


class MyCalculator {

    int power(int n, int p) throws Exception {
        if(n < 0 || p < 0) {
            throw new Exception("n and p should be non-negative");
        }
          
        return (p == 0) ? 1 : n*power(n,p-1);
    }
}
