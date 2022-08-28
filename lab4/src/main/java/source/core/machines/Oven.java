package source.core.machines;

import source.utility.Action;

public class Oven extends Machine {

  public Oven(int productivity) {
    super(Action.BAKING, productivity);
  }
}
