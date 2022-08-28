import java.math.*;

public class Main {
    public static void main(String[] args) {
        long[] a = new long[(22-6)/2+1];

        double[] x = new double[16];

        for (int i = 0, j = 22; i < a.length; i++, j -= 2) { a[i] = j; }

        for (int i = 0; i < x.length; i++) { x[i] = Math.random() * 18 - 7; }
        
        double[][] z = createNestedArray(a,x);

        System.out.println(makeTable(z, maxWidth(z)));

        }

    public static double[][] createNestedArray(long[] first, double[] second) {
        double[][] a = new double[first.length][second.length];

        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {

                if (first[i] == 8) {
                    a[i][j] = Math.exp(Math.pow(2*Math.exp(second[j]), 2));

                } else if ((first[i] <= 12 && first[i] != 8) || first[i] == 20) {
                    a[i][j] = Math.pow(Math.PI/(Math.cbrt(Math.asin((second[j] + 1.5)/17)) + 1),2);

                } else {
                    a[i][j] = Math.atan(0.5 * Math.pow((second[j] + 1.5)/17,2));
                }
            }
        }
        return a;
    }

    public static int maxWidth(double[][] nestArray) {
        int maxWidth = 0;
        for (double[] array : nestArray) {
            for (double n: array) {
                maxWidth = Math.max(maxWidth,String.format("%.3e",n).length());
            }
        }
        return maxWidth;
    }

    public static String makeTable(double[][] nestArray, int width) {
        StringBuilder table = new StringBuilder();

        for (double[] array : nestArray) {
            for (int i = 0; i < array.length; i++){
                table.append("|");
                for (int l = 0 ; l < width+2; l++) { table.append("-"); }
            }
            table.append("|\n");

            for (double n : array) {
                table.append("|").append(String.format(" %-" + (width+1) + ".3e",n));
            }
            table.append("|\n");

        }
        return table.toString();
    }

}
