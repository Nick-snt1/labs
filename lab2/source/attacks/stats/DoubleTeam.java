package source.attacks.stats;
import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {

  public DoubleTeam() {
    this.type = Type.NORMAL;
  }

  @Override
  protected final void applySelfEffects(Pokemon pokemon) {
    pokemon.setMod(Stat.EVASION, 1);
  }

  @Override
  protected final String describe() {
    return "use Double Team";
  }
}
