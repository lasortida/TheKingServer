package org.example.TheKing;

public class Country {
    String name;
    int id;

    float moneyStatus = 0.5f;
    float armyStatus = 0.5f;
    float businessStatus = 0.5f;
    float workerStatus = 0.5f;
    float foodStatus = 0.5f;

    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }
}
