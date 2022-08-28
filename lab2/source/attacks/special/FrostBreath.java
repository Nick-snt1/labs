package source.attacks.special;
import ru.ifmo.se.pokemon.*;

public class FrostBreath extends SpecialMove {

  public FrostBreath() {
    super(Type.ICE, 60, 90);
  }

  @Override
  protected final double calcCriticalHit(Pokemon att, Pokemon deff) {
    return 1.5;
  }

  @Override
  protected final String describe() {
    return "use Frost Breath";
  }
}
