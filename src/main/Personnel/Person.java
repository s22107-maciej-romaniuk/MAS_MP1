package main.Personnel;

import main.CustomExceptions.NoLinkException;
import main.CustomExceptions.NotAnInstanceException;
import main.ObjectPlusPlus;
import main.Personnel.Affiliation.Civilian;
import main.Personnel.Affiliation.Military;
import main.Ship;

import java.io.Serializable;
import java.util.List;

public class Person extends ObjectPlusPlus implements Serializable, IPerson {
    public String firstName;
    public String lastName;

    public Person(){

    }
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return "Person: " + firstName + " " + lastName;
    }

    //===========================================================================
    //ustawianie liczności
    public static void initializeCardinalities(){
        setRoleCardinality("Jest zatrudniony w", 1);
        setRoleCardinality(KontraktL, 1);
        setRoleCardinality(SluzbaL, 1);
        setRoleCardinality(KontraktR, 1);
        setRoleCardinality(SluzbaR, 1);
    }
    //============================================================================
    //asocjacja kwalifikowana
    @Override
    public void addService(Ship ship, String jobName) throws Exception {
        ship.addLink("Zatrudnia", "Jest zatrudniony w", this, jobName);
    }

    @Override
    public void removeService(Ship ship, String jobName){
        ship.removeLink("Zatrudnia", "Jest zatrudniony w", this, jobName);
    }


    //dziedziczenie dynamiczne
    private static String KontraktL = "Kontrakt L";
    private static String KontraktR = "Kontrakt R";
    private static String SluzbaL = "Służba L";
    private static String SluzbaR = "Służba R";

    @Override
    public void changeToCivilian(String employerName) throws Exception {
        for(ObjectPlusPlus link : getLinks(KontraktL)){
            this.removeLink(KontraktL, KontraktR, link);
        }
        Civilian mode = new Civilian(employerName);
        this.addLink(KontraktL, KontraktR, mode);
        for(ObjectPlusPlus link : getLinks(SluzbaL)) {
            this.removeLink(SluzbaL, SluzbaR, link);
        }
    }
    @Override
    public void changeToMilitary(String rank) throws Exception {
        for(ObjectPlusPlus link : getLinks(SluzbaL)) {
            this.removeLink(SluzbaL, SluzbaR, link);
        }
        Military mode = new Military(rank);
        this.addLink(SluzbaL, SluzbaR, mode);
        for(ObjectPlusPlus link : getLinks(KontraktL)){
            this.removeLink(KontraktL, KontraktR, link);
        }
    }

    @Override
    public String getEmployerName() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(KontraktL);
            return ((Civilian) obj.get(0)).employerName;
        } catch (NoLinkException e) {
            throw new NotAnInstanceException("This person is not a civilian");
        }
    }

    @Override
    public String getRank() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(SluzbaL);
            return ((Military) obj.get(0)).rank;
        } catch (NoLinkException e) {
            throw new NotAnInstanceException("This person is not in military");
        }
    }
}
