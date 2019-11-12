package com.example.darryl.assignment2;

public class CoinStorage {
    private static CoinStorage instance;
    //Global variable
    private int coin = 10;

    //Restrict the constructor from being instantiated
    private CoinStorage() {
    }

    //setCoin method to set amount of coins
    public void setCoin(int c) {
        this.coin = c;
    }

    //getCoin method to get the amount of coins
    public int getCoin() {
        return this.coin;
    }

    public static synchronized CoinStorage getInstance() {
        if (instance == null) {
            instance = new CoinStorage();
        }
        return instance;
    }
}


