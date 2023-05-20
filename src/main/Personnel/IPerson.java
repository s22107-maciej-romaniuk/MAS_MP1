package main.Personnel;

import main.CustomExceptions.NotAnInstanceException;
import main.CustomExceptions.UnfitForServiceException;
import main.Ship;

public interface IPerson {
    @Override
    String toString();

    void addService(Ship ship, String jobName) throws Exception;

    void removeService(Ship ship, String jobName) throws UnfitForServiceException;

    void changeToCivilian(String employerName) throws Exception;

    void changeToMilitary(String rank) throws Exception;

    String getEmployerName() throws Exception;

    String getRank() throws Exception;
}
