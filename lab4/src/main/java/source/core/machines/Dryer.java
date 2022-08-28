package source.core.machines;

import source.utility.Action;

public class Dryer extends Machine {

  public Dryer(int productivity) {
    super(Action.DRYING, productivity);
  }
}
