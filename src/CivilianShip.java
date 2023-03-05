import java.util.List;

public class CivilianShip extends Ship{
    private static List<CivilianShip> fleet; //ekstensja klasy

    private static String highestSerialNumber; //atrybut klasowy
    @Override
    String highestSerialNumber() {
        return CivilianShip.highestSerialNumber;
    }
}
