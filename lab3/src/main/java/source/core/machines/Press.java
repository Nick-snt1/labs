package source.core.machines;

import source.utility.Action;

public class Press extends Machine {

  public Press(int productivity) {
    super(Action.PRESSING, productivity);
  }
}
