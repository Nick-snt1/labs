package source.core.production;

import source.utility.*;
import source.core.machines.*;
import source.core.characters.Worker;


public class Factory {

  private String owner;

  private final String location;

  private boolean weightless = false;

  private Machine[] machines = {new Roller(32), new Kneader(11),
                               new Oven(25),   new Press(54),
                               new Dryer(42) };

  public Factory(Nameable owner, String location) {
    this.owner = owner.getName();
    this.location = location;
  }

  public String getOwner() { return owner; }

  public Factory changeOwner(Worker[] workers) throws CantStageARebellionException {
    if (weightless) {
      for (Worker worker : workers) {
        if (worker.hasAntilunit()) {
          System.out.println("Workers staged a rebellion and removed " + owner
                              +". New owner is " + worker.getName());
          owner = worker.getName();
        }
      }
    } else {
      throw new CantStageARebellionException("A rebellion can't be staged "
                                              + ", because there is no"
                                              + "weightlessness in this factory");
    }
    return this;
  }

  public Factory createWeightless(Worker[] workers) {
    for (Worker worker : workers) {
      if (worker.hasWeightlessDevice() && Machine.isAllReinforced(machines)) {
        weightless = true;
        System.out.println("Worker " + worker.getName()
                          + " create weightlessness on factory");
      }
    }
    return this;
  }


  public Factory reinforceMachines(Worker[] workers) {
    for (Worker worker : workers) {
      if (worker.hasAntilunit()) {
        for (Machine machine : machines) machine.reinforce(worker);
        System.out.println("Worker " + worker.getName()
                          + " reinforse all machines on factory");
      }
    }
    return this;
  }

  public void startProduction(Worker[] workers, Product[] products, int howers) {
    int totalAmmo = 0;
    for (Worker worker : workers) {
      for (Product product: products) {
        for (int i = 0; i < howers; i++) {
          totalAmmo += product.produce(machines, worker);
        }
      }
    }
    for (Product product: products) { product.checkPrice(Machine.isAllReinforced(machines)); }
    System.out.println("Factory with "
                      + ((workers.length == 1) ? (1 + " worker") : (workers.length + " workers"))
                      + " could produce " + totalAmmo
                      + " units of product per "
                      + ((howers == 1) ? (1 + " hower") : (howers + " howers")));
  }

  @Override
  public String toString() {
    return "Current owner of Factory is " + owner
           + System.lineSeparator()
           + "Factory located on " + location
           + System.lineSeparator()
           + "Gravity: " + ((weightless) ? "absent" : "normal")
           + System.lineSeparator()
           + "Machines are " + ((Machine.isAllReinforced(machines)) ? "" : "not ")
           + "reinforced";
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) { return true; }
    if (anObject instanceof Factory) {
      Factory other = (Factory) anObject;
      return location.equals(other.location);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 42;
    result = 37 * result + (location == null ? 0 : location.hashCode());
    return result;
  }
}
