package com.example.kharcha;

public class Expenses {
    public int food;
    public int health;
    public int transport;
    public int entertainment;
    public int totalExpenses;

    public Expenses(){

    }

    public Expenses(int food,int health,int transport,int entertainment){
        this.food=food;
        this.entertainment=entertainment;
        this.health=health;
        this.transport=transport;
        this.totalExpenses=food+health+entertainment+transport;
    }
}
