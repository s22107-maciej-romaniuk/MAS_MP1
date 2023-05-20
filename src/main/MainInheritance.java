package main;

import main.Personnel.IPerson;
import main.Personnel.Robotics.SentientShip;

import java.util.LinkedList;
import java.util.List;

public class MainInheritance {
    public static void main(String[] args) throws Exception {
        List<IShip> shipList = new LinkedList<>();
        List<IPerson> personList = new LinkedList<>();

        SentientShip sentientShip = new SentientShip(new CivilianShip(new Reactor(), "Test", "HMS"));
        shipList.add(sentientShip);
        personList.add(sentientShip);
        for (IShip ship:
             shipList) {
            System.out.println(ship.toString());
        }
        for (IPerson person:
                personList) {
            System.out.println(person.ge));
        }
    }
}
