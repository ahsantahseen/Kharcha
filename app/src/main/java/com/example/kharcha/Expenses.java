package com.example.kharcha;

public class Expenses {
    public double food;
    public double health;
    public double transport;
    public double entertainment;
    public double totalExpenses;

    public void Expenses(double food,double health,double transport,double entertainment){
        this.food=food;
        this.entertainment=entertainment;
        this.health=health;
        this.transport=transport;
        this.totalExpenses=food+health+entertainment+transport;
    }
}
