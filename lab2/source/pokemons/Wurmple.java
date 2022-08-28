package source.pokemons;
import ru.ifmo.se.pokemon.*;
import source.attacks.stats.*;

public class Wurmple extends Pokemon {

  public Wurmple(String name, int level) {
    super(name, level);
    this.setType(Type.BUG);
    this.setStats(45,45,35,20,30,20);
    this.setMove(new DoubleTeam(), new Swagger());
  }
}
