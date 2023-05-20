package main;

import main.Personnel.Person;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        Person civilianShipCoordinator = new Person();
        civilianShipCoordinator.firstName = "Maciej";
        civilianShipCoordinator.lastName = "Romaniuk";

        CivilianShip.setCoordinatorStatic(civilianShipCoordinator);
        System.out.println(CivilianShip.getCoordinatorStatic().firstName);

        CivilianShip civShip1 = new CivilianShip(new Reactor(), "Dreadnought", "HMS");
        CivilianShip civShip2 = new CivilianShip(new Reactor(), "Julia", "USS");
        System.out.println(CivilianShip.getFleet());
        //System.out.println(CivilianShip.getHighestSerialNumber());
        try {
            civShip1.setShipReactor(null);
        }
        catch(NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        //System.out.println(civShip2.getSerialNumber());
        civShip1.setCargoManifest(Stream.of("foo", "bar")
                                        .collect(Collectors.toList()));
        civShip2.setCargoManifest(Stream.of("tutu", "tutu")
                                        .collect(Collectors.toList()));
        System.out.println(CivilianShip.mostFrequentCargoCivilianShip());
        System.out.println(CivilianShip.mostFrequentCargoShip());
        try{
            CivilianShip.writeExtensionToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
