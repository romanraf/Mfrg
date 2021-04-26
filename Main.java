package com.company;
class animal{
    public animal(){ anim_Counter++; };
    public animal(String Name){
        name = Name;
        anim_Counter++;
    }
    public String name;
    void run(int dist){};
    void swim(int dist){};
    static int GetCount(){
        return anim_Counter;
    }
    private static int anim_Counter = 0;
};

class cat extends animal{
    public cat(String Name){
        name = Name;
        cat_Counter++;
    }
    void run(int dist){
        int m_dist = dist>Max_dist ? Max_dist : dist;
        System.out.println(name + " пробежала " + m_dist);
    }
    void swim(int dist){
        System.out.println("Кошка не умеет плавать");
    }
    static int GetCount(){
        return cat_Counter;
    }
    private final int Max_dist = 200;
    private static int cat_Counter = 0;
};
class dog extends animal{
    public dog(String Name){
        name = Name;
        dog_Counter++;
    }
    void run(int dist){
        int m_dist = dist>Max_dist ? Max_dist : dist;
        System.out.println(name + " пробежал " + m_dist);
    }
    void swim(int dist){
        int m_dist = dist>Max_sdist ? Max_sdist : dist;
        System.out.println(name + " проплыл " + m_dist);
    }
    static int GetCount(){
        return dog_Counter;
    }
    private final int Max_dist = 500;
    private final int Max_sdist = 10;
    private static int dog_Counter = 0;
};
public class Main {

    public static void main(String[] args) {
        animal[] st = {new cat("1"),new cat("2"), new dog("3")};
        for(animal i : st) {
            i.run(500);
            i.swim(5);
        }
        System.out.println(animal.GetCount());
        System.out.println(cat.GetCount());
        System.out.println(dog.GetCount());
    }
}
