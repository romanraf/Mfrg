package com.company;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

class Phonebook{
    HashMap<String, String> PhnSur = new HashMap<>();
    void add(String Phone, String Surname){
        PhnSur.put(Phone, Surname);
    }
    HashSet<String> get(String Surname){
        HashSet<String> phones = new HashSet<>();
        for (Map.Entry<String, String> s : PhnSur.entrySet()) {
            if (Objects.equals(Surname, s.getValue())) {
                phones.add(s.getKey());
            }
        }
        return phones;
    }
}


public class Main {

    public static void main(String[] args) {
        String[] newSt = {"A", "B", "A", "C","a", "B", "ag", "D", "Gg", "dg", "SSG"};
        HashSet<String> st = new HashSet<>();
        st.addAll(Arrays.asList(newSt));
        System.out.println("Различных: " + st.size());

        ArrayList<String> bufLs = new ArrayList<>(Arrays.asList(newSt));
        Collections.sort(bufLs);

        st.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s + ": "+ Integer.toString(bufLs.lastIndexOf(s)- bufLs.indexOf(s) +1) );
            }
        });

        Phonebook phn = new Phonebook();
        phn.add("111111666", "Art");
        HashSet<String> chck = phn.get("Art");
        System.out.println(chck);
    }
}
