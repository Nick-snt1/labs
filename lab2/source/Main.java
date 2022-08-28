package source;
import ru.ifmo.se.pokemon.*;
import source.pokemons.*;

public class Main {
  public static void main(String[] args) {
    Battle b = new Battle();

    Pokemon[] team1 = { new Solrock("first", 2),
                        new MimeJr("second",2),
                        new MrMime("third", 2) };

    Pokemon[] team2 = { new Wurmple("fourth", 2),
                        new Cascoon("fifth", 2),
                        new Dustox("sixth",2) };

    for(Pokemon pokemon : team1) b.addAlly(pokemon);
    for(Pokemon pokemon : team2) b.addFoe(pokemon);
    
    b.go();
  }
}
