package com.company;
class Cat
{
    private boolean patiety;
    private int appetite;
    Cat(int app){
        appetite = app;
    }
    public void eat(Plate p){
        if(p.getFood() >= appetite){
            p.DecreaseFood(appetite);
            patiety = true;
        }
    }
};

class Plate
{
    private int food;
    int getFood(){
        return food;
    }
    void AddFood(int num){
        food += num;
    }
    void DecreaseFood(int num){
        food -= num;
        if(food < 0)
            food = 0;
    }
    Plate(){};
    Plate(int Food){
        food = Food;
    }
};
public class Main {

    public static void main(String[] args) {
        Cat[] cats = new Cat[10];
        Plate p = new Plate(1000);

        for(int i = 0;i < cats.length;i++) {
            cats[i] = new Cat((int) (Math.random() * 100));
            cats[i].eat(p);
        }
    }
}
