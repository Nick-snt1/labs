package source.pokemons;
import source.attacks.physical.Bulldoze;

public class MrMime extends MimeJr {

  public MrMime(String name, int level) {
    super(name, level);
    this.setStats(40,45,65,100,120,90);
    this.addMove(new Bulldoze());
  }
}
