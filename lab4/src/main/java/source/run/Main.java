package source.run;

import source.core.machines.*;
import source.core.characters.*;
import source.core.production.*;
import source.utility.*;

public class Main {
    public static void main(String ... args) {
        Cosmonaut cosmonaut = new Cosmonaut("Valera");

        Worker[] workers = {new Worker("Pasha", 13),
                            new Worker("Semyon", 11),
                            new Worker("Grisha", 15)};

        Pauper pauper = new Pauper("Slava", 100);
        Pauper.Family paupersFamily = pauper.new Family(3);
        Pauper.TaxOffice taxOffice = new Pauper.TaxOffice();


        Product[] product = {new Product("Makarohi"),
                             new Product("Lapsha")};

        Factory factory = new Factory(() -> "Skuperfield", "Earth");


        System.out.println(factory.toString());
        factory.startProduction(workers, product, 1);

        System.out.println();

        pauper.earnMoney(10)
              .buyProduct(product[1], 50)
              .eatPasta();
        paupersFamily.eatPasta();

        System.out.println();

        taxOffice.increaseTaxRate(5);

        System.out.println();

        try {
          workers[0].getAntilunit(cosmonaut);
          workers[1].getWeightlessDevice(cosmonaut);
          workers[0].getAntilunit(workers[1]);
        } catch (AbsenceOfItemException a) {
          System.out.println(a.getMessage());
        }

        System.out.println();

        try {
          factory.changeOwner(workers);
        } catch (CantStageARebellionException c) {
          System.out.println(c.getMessage());
        }

        factory.reinforceMachines(workers)
               .createWeightless(workers);

        try {
          factory.changeOwner(workers);
        } catch (CantStageARebellionException c) {
          System.out.println(c.getMessage());
        }

        System.out.println();

        System.out.println(factory.toString());
        factory.startProduction(workers, product, 1);

        System.out.println();

        pauper.earnMoney(10)
              .buyProduct(product[1], 50);
    }
}
