import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MilitaryShip extends Ship{
    private static List<MilitaryShip> fleet; //ekstensja klasy

    @Override
    public void writeExtensionToFile(ObjectOutputStream stream) throws IOException {

    }

    @Override
    public void readExtensionFromFile(ObjectInputStream stream) throws IOException, ClassNotFoundException {

    }

    @Override
    int highestSerialNumber() {
        return 0;
    }

    @Override // przesłonięcie
    public String getDescription() {
        return String.format("Military ship %s %s", this.prefixName, this.shipName);
    }
}
