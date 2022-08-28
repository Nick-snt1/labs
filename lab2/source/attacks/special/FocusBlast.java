package source.attacks.special;
import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove {

  public FocusBlast() {
    super(Type.FIGHTING, 120, 70);
  }

  @Override
  protected final void applyOppEffects(Pokemon pokemon) {
    Effect e = new Effect().chance(0.1).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1);
    pokemon.addEffect(e);
  }

  @Override
  protected final String describe() {
    return "use Focus Blast";
  }
}
