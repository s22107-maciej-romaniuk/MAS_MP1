import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainRead {
    public static void main(String[] args) {
        try{
            CivilianShip.readExtensionFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(CivilianShip.getCoordinatorStatic().firstName);
        System.out.println(CivilianShip.getFleet());
        System.out.println(CivilianShip.getHighestSerialNumber());
        System.out.println(CivilianShip.mostFrequentCargoCivilianShip());
        System.out.println(CivilianShip.mostFrequentCargoShip());
    }
}
