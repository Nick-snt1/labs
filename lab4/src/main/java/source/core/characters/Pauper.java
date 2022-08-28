package source.core.characters;

import source.core.production.Product;
import source.utility.Nameable;

public class Pauper implements Nameable {

    private final String name;

    private int money;

    private int pastaStock = 0;

    private static int taxRate = 10;

    public Pauper(String name, int money) {
      this.name = name;
      this.money = money;
    }

    @Override
    public String getName() { return name; }

    public Pauper earnMoney(int hours) {
        class Work {
            private int salary = 10;
            private int workingHours;
            public Work() { this.workingHours = hours; }
            public int paySalary() { return salary*workingHours; }
        }
        this.money += new Work().paySalary();
        return this;
    }

    public Pauper eatPasta() {
      pastaStock -= 5;
      System.out.println("Pauper " + getName() + " eat some pasta.");
      return this;
    }

    public Pauper buyProduct(Product product, int m) {
      int currentPastaAmmo = product.sellProduct(m);
      pastaStock += currentPastaAmmo;
      int actualPrice = m + (int) (m * (1.0/taxRate));
      this.money -= actualPrice;
      System.out.println("Pauper " + getName()
                                   + " can buy "
                                   + currentPastaAmmo
                                   + " items of product "
                                   + product.getType()
                                   + " for "
                                   + actualPrice
                                   + " deneg.");
      return this;
    }

    public class Family {
      private int members;
      public Family(int members){ this.members = members; }
      public void eatPasta() {
        Pauper.this.pastaStock -= members*5;
        System.out.println("Family of pauper " + Pauper.this.getName()
                                               + ", consisting of "
                                               + members + " members, "
                                               + "eat "
                                               + members*5
                                               + " pieses of pasta.");
      }
    }

    public static class TaxOffice {
      public static void increaseTaxRate(int val) {
        taxRate += val;
        System.out.println("Tax office increased tax rate by " + val
                                                               + " percents.");
      }
    }
}
