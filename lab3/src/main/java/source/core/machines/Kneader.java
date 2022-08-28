package source.core.machines;

import source.utility.Action;

public class Kneader extends Machine {

  public Kneader(int productivity) {
    super(Action.KNEADING, productivity);
  }
}
