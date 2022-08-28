package source.core.machines;

import source.utility.*;
import source.core.characters.Worker;

public abstract class Machine implements Producible {

  private boolean isReinforced = false;

  private final int productivity;

  private final Action action;

  public Machine(Action action, int productivity) {
    this.action = action;
    this.productivity = productivity;
  }

  public boolean isReinforced() { return isReinforced; }

  @Override
  public int getProductivity() { return productivity; }

  public Action getAction() { return action; }

  public void reinforce(Worker worker) {
    if (worker.hasAntilunit()) isReinforced = true;
  }

  public static boolean isAllReinforced(Machine[] machines) {
    boolean flag = true;
    for (Machine machine : machines) {
      if (!machine.isReinforced()) flag = false; break;
    }
    return flag;
  }

  @Override
  public String toString() {
    return getClass().getName() + " [action=" + action
           + ", productivity=" + productivity + "]";
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) { return true; }
    if (anObject instanceof Machine) {
      Machine other = (Machine) anObject;
      return action == other.action && productivity == other.productivity;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 34;
    result = 45 * result + (action == null ? 0 : action.hashCode());
    result += productivity;
    return result;
  }
}
