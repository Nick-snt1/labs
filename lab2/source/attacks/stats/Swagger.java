package source.attacks.stats;
import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {

  public Swagger() {
    this.type = Type.NORMAL;
    this.accuracy = 85;
  }

  @Override
  protected final void applyOppEffects(Pokemon pokemon) {
    pokemon.confuse();
    pokemon.setMod(Stat.ATTACK, 2);
  }

  @Override
  protected final String describe() {
    return "use Swagger";
  }
}
