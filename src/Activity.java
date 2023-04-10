public class Activity extends ObjectPlusPlus{

    public String description;

    public Activity(String description) {
        this.description = description;
    }

    //ustawianie liczności
    public static void initializeCardinalities(){
        setRoleCardinality("Brał udział w R", 1);
        setRoleCardinality("Zrzeszał L", 1);
    }

    @Override
    public String toString(){
        return description;
    }
}
