package source.attacks.physical;
import ru.ifmo.se.pokemon.*;

public class SeismicToss extends PhysicalMove {

  public SeismicToss(int power){
    super(Type.FIGHTING, power, 100); // power == Pokemons level
  }

  @Override
  protected final String describe() {
      return "use Seismic Toss";
  }

}
