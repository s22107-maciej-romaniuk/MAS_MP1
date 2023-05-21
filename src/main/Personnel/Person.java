package main.Personnel;

import main.CustomExceptions.NoLinkException;
import main.CustomExceptions.NotAnInstanceException;
import main.ObjectPlusPlus;
import main.Ship;

import java.io.Serializable;
import java.util.List;

public class Person extends ObjectPlusPlus implements Serializable, IPerson {
    public String firstName;
    public String lastName;

    public Person(){

    }
    public Person(String firstName, String lastName, Class civilianOrOverlapping, String arg) throws Exception {
        this.firstName = firstName;
        this.lastName = lastName;
        if(civilianOrOverlapping == Civilian.class){
            this.changeToCivilian(arg);
        }
        else if(civilianOrOverlapping == Military.class){
            this.changeToMilitary(arg);
        }
    }

    @Override
    public String toString(){
        return getName();
    }

    @Override
    public String getName() { return "Person: " + firstName + (lastName == null ? "" : " " + lastName);}
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
        try {
            for (ObjectPlusPlus link : getLinks(KontraktL)) {
                this.removePart(KontraktL, KontraktR, link);
            }
        }
        catch(NoLinkException ex){
            //do nothing
        }
        Civilian mode = new Civilian(employerName);
        this.addPart(KontraktL, KontraktR, mode);
        try {
            for(ObjectPlusPlus link : getLinks(SluzbaL)) {
                this.removeLink(SluzbaL, SluzbaR, link);
            }
        }
        catch(NoLinkException ex){
            //do nothing
        }
    }
    @Override
    public void changeToMilitary(String rank) throws Exception {
        try {
            for(ObjectPlusPlus link : getLinks(SluzbaL)) {
                this.removePart(SluzbaL, SluzbaR, link);
            }
        }
        catch(NoLinkException ex){
            //do nothing
        }
        Military mode = new Military(rank);
        this.addPart(SluzbaL, SluzbaR, mode);
        try {
            for(ObjectPlusPlus link : getLinks(KontraktL)){
                this.removeLink(KontraktL, KontraktR, link);
            }
        }
        catch(NoLinkException ex){
            //do nothing
        }
    }

    @Override
    public Civilian castCivilian() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(KontraktL);
            return ((Civilian) obj.get(0));
        } catch (NoLinkException | IndexOutOfBoundsException e) {
            throw new NotAnInstanceException("This person is not a civilian");
        }
    }

    @Override
    public Military castMilitary() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(SluzbaL);
            return ((Military) obj.get(0));
        } catch (NoLinkException | IndexOutOfBoundsException e) {
            throw new NotAnInstanceException("This person is not in military");
        }
    }

    public class Civilian extends ObjectPlusPlus {
        public String employerName;

        public Civilian(String employerName) {
            this.employerName = employerName;
        }

        public String getEmployerName() {
            return employerName;
        }
    }

    public class Military extends ObjectPlusPlus {
        public String rank;

        public Military(String rank) {
            this.rank = rank;
        }

        public String getRank() {
            return rank;
        }
    }
}
