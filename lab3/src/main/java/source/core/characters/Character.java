package source.core.characters;

import source.utility.Nameable;
import source.utility.Producible;

public abstract class Character implements Nameable, Producible {

  private boolean hasAntilunit;

  private boolean hasWeightlessDevice;

  private final String name;

  private final int productivity;

  public Character(String name,
                   boolean antiL,
                   boolean weightL,
                   int productivity) {
    this.name = name;
    this.hasAntilunit = antiL;
    this.hasWeightlessDevice = weightL;
    this.productivity = productivity;
  }

  public boolean hasAntilunit() { return hasAntilunit; }

  public boolean hasWeightlessDevice() { return hasWeightlessDevice;}

  public void getAntilunit(Character character) {
      if (character.hasAntilunit()) {
        hasAntilunit = true;
        System.out.println( getName()
                            + " takes antilunit from "
                            + character.getName());
      }
  }

  public void getWeightlessDevice(Character character) {
      if (character.hasWeightlessDevice()) {
        hasWeightlessDevice = true;
        System.out.println( getName()
                            + " takes weightless device from "
                            + character.getName());
      }
  }

  @Override
  public String getName() { return name; }

  @Override
  public int getProductivity() { return productivity; }

  @Override
  public String toString() {
    return getClass().getName() + " [name=" + name
           + ", hasAntilunit=" + hasAntilunit
           + ", hasWeightlessDevice=" + hasWeightlessDevice 
           + ", productivity=" + productivity + "]";
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) { return true; }
    if (anObject instanceof Character) {
      Character other = (Character) anObject;
      return name.equals(other.name) && productivity == other.productivity;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 52;
    result = 31 * result + (name == null ? 0 : name.hashCode());
    result += productivity;
    return result;
  }
}
