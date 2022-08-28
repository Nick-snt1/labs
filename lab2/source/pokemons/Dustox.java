package source.pokemons;
import source.attacks.stats.CalmMind;
import ru.ifmo.se.pokemon.Type;

public class Dustox extends Cascoon {

  public Dustox(String name, int level) {
    super(name, level);
    this.addType(Type.POISON);
    this.setStats(60,50,70,50,90,65);
    this.addMove(new CalmMind());
  }
}
