package source.pokemons;
import ru.ifmo.se.pokemon.*;
import source.attacks.stats.*;
import source.attacks.physical.AerialAce;


public class MimeJr extends Pokemon {

  public MimeJr(String name, int level) {
    super(name, level);
    this.setType(Type.PSYCHIC, Type.FAIRY);
    this.setStats(20,25,45,70,90,60);
    this.setMove(new DoubleTeam(), new AerialAce(), new Swagger());
  }

}
