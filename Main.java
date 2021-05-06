package com.company;

interface participant{
    boolean Run(int lim);
    boolean Jump(int lim);
    String Info();
    void SetFinished(boolean flag);
    boolean HasFinished();
}

class Human implements participant{
    String name;
    boolean finished = true;
    int runLim;
    int jmpLim;

    Human(String Name, int RunLim,int JmpLim){
        name = Name;
        runLim = RunLim;
        jmpLim = JmpLim;
    }
    public boolean Run(int lim){
        if(lim > runLim)
            return false;

        System.out.println("Human have run");
        return true;
    }
    public boolean Jump(int lim){
        if(lim > jmpLim)
            return false;

        System.out.println("Human have jumped");
        return true;
    }
    public String Info(){
        return name;
    }
    public void SetFinished(boolean flag){
        finished = flag;
    }
    public boolean HasFinished(){
        return finished;
    }
}

class Cat implements participant{
    String name;
    boolean finished = true;
    int runLim;
    int jmpLim;

    Cat(String Name, int RunLim,int JmpLim){
        name = Name;
        runLim = RunLim;
        jmpLim = JmpLim;
    }
    public boolean Run(int lim){
        if(lim > runLim)
            return false;

        System.out.println("Cat have run");
        return true;
    }
    public boolean Jump(int lim){
        if(lim > jmpLim)
            return false;

        System.out.println("Cat have jumped");
        return true;
    }
    public String Info(){
        return name;
    }
    public void SetFinished(boolean flag){
        finished = flag;
    }
    public boolean HasFinished(){
        return finished;
    }
}

class Robo implements participant{
    String name;
    boolean finished = true;
    int runLim;
    int jmpLim;

    Robo(String Name, int RunLim,int JmpLim){
        name = Name;
        runLim = RunLim;
        jmpLim = JmpLim;
    }
    public boolean Run(int lim){
        if(lim > runLim)
            return false;

        System.out.println("Robo have run");
        return true;
    }
    public boolean Jump(int lim){
        if(lim > jmpLim)
            return false;

        System.out.println("Robo have jumped");
        return true;
    }
    public String Info(){
        return name;
    }
    public void SetFinished(boolean flag){
        finished = flag;
    }
    public boolean HasFinished(){
        return finished;
    }
}

interface Obstacle{
    void Use(participant p);
}

class raceTrack implements Obstacle{
    int Lng;
    raceTrack(int Length){
        Lng = Length;
    }
    public void Use(participant p){
        if(!p.Run(Lng)) {
            System.out.println(p.Info() + " haven't run");
            p.SetFinished(false);
        }
    }
}

class Wall implements Obstacle {
    int Hght;
    Wall(int Height){
        Hght = Height;
    }
    public void Use(participant p){
        if(!p.Jump(Hght)) {
            System.out.println(p.Info() + " haven't jumped");
            p.SetFinished(false);
        }
    }
}

class Team{
    public participant[] members;

    Team(participant[] prts){
        if(prts.length < 4)
            throw new IllegalArgumentException("Team length should be equal or higher than 4");

        members = new participant[4];
        for(int i = 0;i < prts.length;i++)
            members[i] = prts[i];
    }

    void ShowFinished(){
        for(participant i : members){
            if(i.HasFinished())
                System.out.println(i.Info());
        }
    }
    void ShowAll(){
        for(participant i : members)
            System.out.println(i.Info());
    }
}

class Course{
    public Obstacle[] track;
    Course(Obstacle[] obstcls){
        track = obstcls;
    }
    void TeamUse(Team t){
        for(participant membr : t.members) {
            for (Obstacle obstcl : track)
                obstcl.Use(membr);
        }

    }
}

public class Main {

    public static void main(String[] args) {
        Team team = new Team(new participant[]{
                new Human("Ivan",10,10),
                new Cat("Boris",5,15),
                new Human("Vikt",10,15),
                new Robo("Fedor",2,5)});
        System.out.println("Participants:");
        team.ShowAll();
        Course crs = new Course(new Obstacle[]{new Wall(10),new raceTrack(10)});
        crs.TeamUse(team);
        System.out.println("finished: ");
        team.ShowFinished();
    }
}
