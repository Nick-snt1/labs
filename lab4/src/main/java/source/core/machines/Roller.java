package source.core.machines;

import source.utility.Action;

public class Roller extends Machine {

  public Roller(int productivity) {
    super(Action.ROLLING, productivity);
  }
}
