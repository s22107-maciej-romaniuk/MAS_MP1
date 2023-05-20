package main;

import java.time.LocalDate;

public class Incident extends ObjectPlusPlus{
    private String description;
    private LocalDate date;    //ustawianie liczności
    public static void initializeCardinalities(){
        setRoleCardinality("Zrzeszał R", Integer.MAX_VALUE);
    }

    public Incident(){

    }

    public Incident(String description, LocalDate date){
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString(){
        return date + ":\n" + description;
    }

    //============================================================================
    //asocjacja z atrybutem
    public void addParticipant(Ship participant, Activity activity) throws Exception {
        participant.addIncident(this, activity);
    }

    public void removeParticipant(Ship participant) throws Exception {
        participant.removeIncident(this);
    }
}
