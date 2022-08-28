package source.pokemons;
import source.attacks.physical.SeismicToss;

public class Cascoon extends Wurmple {

  public Cascoon(String name, int level) {
    super(name, level);
    this.setStats(50,35,55,25,25,15);
    this.addMove(new SeismicToss(this.getLevel()));
  }
}
