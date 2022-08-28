package source.attacks.physical;
import ru.ifmo.se.pokemon.*;

public class DoubleSlap extends PhysicalMove {

  public DoubleSlap() {
    super(Type.NORMAL, 15, 85, 0, 2);
  }

  @Override
  protected final String describe() {
      return "use Double Slap";
  }

}
