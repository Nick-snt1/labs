package source.attacks.stats;
import ru.ifmo.se.pokemon.*;

public class CalmMind extends StatusMove {

  public CalmMind() {
    this.type = Type.PSYCHIC;
  }

  @Override
  protected final void applySelfEffects(Pokemon pokemon) {
    pokemon.setMod(Stat.SPECIAL_ATTACK, 1);
    pokemon.setMod(Stat.SPECIAL_DEFENSE,1);
  }

  @Override
  protected final String describe() {
    return "use Calm Mind";
  }
}
