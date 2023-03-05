import java.util.List;

public class MilitaryShip extends Ship{
    private static List<MilitaryShip> fleet; //ekstensja klasy
    private static String highestSerialNumber; //atrybut klasowy
    @Override
    String highestSerialNumber() {
        return MilitaryShip.highestSerialNumber;
    }
}
