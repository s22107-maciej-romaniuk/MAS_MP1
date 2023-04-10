import java.io.Serializable;

public class Person extends ObjectPlusPlus implements Serializable {
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
    }
    //============================================================================
    //asocjacja kwalifikowana
    public void addEmployment(Ship ship, String jobName) throws Exception {
        ship.addLink("Zatrudnia", "Jest zatrudniony w", this, jobName);
    }

    public void removeEmployment(Ship ship, String jobName){
        ship.removeLink("Zatrudnia", "Jest zatrudniony w", this, jobName);
    }
}
