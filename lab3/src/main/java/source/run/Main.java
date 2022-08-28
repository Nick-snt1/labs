package source.run;

import source.core.machines.*;
import source.core.characters.*;
import source.core.production.*;

public class Main {
    public static void main(String ... args) {
        Cosmonaut cosmonaut = new Cosmonaut("Valera");

        Worker[] workers = {new Worker("Pasha", 13),
                            new Worker("Semyon", 11),
                            new Worker("Grisha", 15)};

        Product[] product = {new Product("Makarohi"),
                             new Product("Lapsha")};

        Factory factory = new Factory(() -> "Skuperfield", "Earth");

        System.out.println(factory.toString());
        factory.startProduction(workers, product, 1);

        workers[0].getAntilunit(cosmonaut);
        workers[1].getWeightlessDevice(cosmonaut);

        factory.reinforceMachines(workers)
               .createWeightless(workers)
               .changeOwner(workers);

        System.out.println(factory.toString());
        factory.startProduction(workers, product, 1);
    }
}
