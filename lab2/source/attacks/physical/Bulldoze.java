package source.attacks.physical;
import ru.ifmo.se.pokemon.*;

public class Bulldoze extends PhysicalMove {

  public Bulldoze() {
    super(Type.GROUND, 60, 100);
  }

  @Override
  protected final void applyOppEffects(Pokemon pokemon) {
    pokemon.setMod(Stat.SPEED, -1);
  }

  @Override
  protected final String describe() {
      return "use Bulldoze";
  }

}
