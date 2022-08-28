package source.pokemons;
import ru.ifmo.se.pokemon.*;
import source.attacks.special.*;
import source.attacks.stats.CalmMind;
import source.attacks.physical.DoubleSlap;

public class Solrock extends Pokemon {

  public Solrock(String name, int level) {
    super(name, level);
    this.setType(Type.ROCK, Type.PSYCHIC);
    this.setStats(90,95,85,55,65,70);
    this.setMove(new CalmMind(),
                 new DoubleSlap(),
                 new FocusBlast(),
                 new FrostBreath());

  }

}
