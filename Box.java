package com.company;

import lombok.NonNull;
import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList<T> contain;

    Box(){
        contain = new ArrayList<>();
    }

    int getWeight(){
        int weight = contain.size()*T.getWeight();
        return weight;
    }

    <F extends Fruit> boolean compare(@NonNull Box<F> box){
        return ( this.getWeight() == box.getWeight() );
    }

    void toOtherBox(Box<T> box){
        box.contain.addAll(this.contain);
        this.contain.clear();
    }

    void add(T fruit){
        contain.add(fruit);
    }
}
