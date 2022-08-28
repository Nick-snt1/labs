package source.attacks.physical;
import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {

  public AerialAce() {
    super(Type.FLYING, 60, Double.POSITIVE_INFINITY);
  }

  @Override
  protected final String describe() {
    return "use Aerial Ace";
  }
}
