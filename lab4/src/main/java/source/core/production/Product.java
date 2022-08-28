package source.core.production;

import java.util.Arrays;
import source.utility.Action;
import source.core.machines.*;
import source.core.characters.Worker;

public class Product {

  private final String type;

  private int ammount = 0;

  private int price = 3;

  private Action[] actions = new Action[5];

  public Product(String type) { this.type = type; }

  public String getType() { return type; }

  public int getPrice() { return price; }

  public int getAmmount() { return ammount; }

  public int produce(Machine[] machines, Worker worker) {
    int currentAmmo = worker.getProductivity();
    for (int i = 0; i < machines.length; i++) {
      actions[i] = machines[i].getAction();
      currentAmmo += machines[i].getProductivity();
    }
    if (isPrepared(actions)) {
      int a = (int) currentAmmo / (machines.length + 1);
      ammount += (Machine.isAllReinforced(machines) ? 3*a : a);
      return (Machine.isAllReinforced(machines) ? 3*a : a);
    } else {
      return 0;
    }
  }

  public void checkPrice(boolean flag) {
    price = (flag ? (int) price/3 : price);
  }

  public int sellProduct(int money) {
    int sellingProduct = (int) money/price;
    ammount -= sellingProduct;
    return sellingProduct;
  }

  private static boolean isPrepared(Action[] actions) {
    return Arrays.equals(actions, Action.values());
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) { return true; }
    if (anObject instanceof Product) {
      Product other = (Product) anObject;
      return type.equals(other.type) &&
             Arrays.equals(actions, other.actions) &&
             ammount == other.ammount;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 13;
    result = 32 * result + (type == null ? 0 : type.hashCode());
    result = 32 * result + ammount;
    for (Action action: actions) {
      result = 32 * result + (action == null ? 0 : action.hashCode());
    }
    return result;
  }

  @Override
  public String toString() {
    return getClass().getName() + " [type=" + type
           + ", ammount=" + ammount
           + ", actions=" + Arrays.toString(actions) + "]";
  }
}
