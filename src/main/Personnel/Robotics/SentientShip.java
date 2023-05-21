package main.Personnel.Robotics;

import main.Activity;
import main.CustomExceptions.NotAnInstanceException;
import main.CustomExceptions.UnfitForServiceException;
import main.IShip;
import main.Incident;
import main.ObjectPlusPlus;
import main.Personnel.IPerson;
import main.Personnel.Person;
import main.Reactor;
import main.Ship;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SentientShip extends Ship implements IPerson {
    private static String PersonSentientL = "PersonSentientL";
    private static String PersonSentientR = "PersonSentientR";
    private static String ShipSentientL = "ShipSentientL";
    private static String ShipSentientR = "ShipSentientR";
    public static void initializeCardinalities(){
        setRoleCardinality(PersonSentientL, 1);
        setRoleCardinality(PersonSentientR, 1);
        setRoleCardinality(ShipSentientL, 1);
        setRoleCardinality(ShipSentientR, 1);
    }
    public SentientShip(Reactor reactor, String shipName, String prefixName, String personaName, Class civilianOrMilitary, String arg) throws Exception {
        super(reactor, shipName, prefixName);
        Person person = new Person(personaName, null, civilianOrMilitary, arg);
        this.addLink(PersonSentientL, PersonSentientR, person, PersonSentientL);
    }

    private Person getPerson() throws Exception {
        return (Person) this.getLinkedObject(PersonSentientL, PersonSentientL);
    }

    @Override
    public String getName() throws Exception {
        return getPerson().getName();
    }

    @Override
    public void addService(Ship ship, String jobName) throws UnfitForServiceException {
        throw new UnfitForServiceException("Sentient ship cannot serve as a crewmember");
    }

    @Override
    public void removeService(Ship ship, String jobName) throws UnfitForServiceException {
        throw new UnfitForServiceException("Sentient ship cannot serve as a crewmember");
    }

    @Override
    public void changeToCivilian(String employerName) throws Exception {
        this.getPerson().changeToCivilian(employerName);
    }

    @Override
    public void changeToMilitary(String rank) throws Exception {
        this.getPerson().changeToMilitary(rank);
    }

    @Override
    public Person.Civilian castCivilian() throws Exception {
        return this.getPerson().castCivilian();
    }

    @Override
    public Person.Military castMilitary() throws Exception {
        return this.getPerson().castMilitary();
    }

    @Override
    public String getDescription() throws Exception {
        StringBuilder b = new StringBuilder();
        b.append(String.format("Sentient ship %s %s", this.prefixName, this.shipName));

        return b.toString();
    }
}
