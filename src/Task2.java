import java.util.LinkedList;

public class Task2 {

    final static int K = 8;
    final static int A = 2;
    final static int minRange = -100;
    final static int maxRange = 100;
    final static double EPS = Math.pow(10, -K);

    public static void main(String[] args) {
        LinkedList<Operationable> equations = new LinkedList<>();
        LinkedList<Operationable> derEquations = new LinkedList<>();
        equations.add(x -> Math.pow(x, 3) + Math.pow(x, 2) - x + 0.5); // 1.1 [-1.74]
        derEquations.add(x -> 3 * Math.pow(x, 2) + 2 * x - 1);

        equations.add(x -> Math.pow(Math.E, x) / A - x - 1); // 1.2 [-0.768]
        derEquations.add(x -> Math.pow(Math.E, x) / A - 1);

        equations.add(x -> Math.pow(x, 3) - 20 * x + 1); // 1.3 [0.05]
        derEquations.add(x -> 3 * Math.pow(x, 2) - 20);

        equations.add(x -> Math.pow(2, x) + Math.pow(x, 2) - 2); // 1.4 [0.6535]
        derEquations.add(x -> Math.pow(2, x) * Math.log(2) + 2 * x);

        equations.add(x -> x * Math.log(x + 2) - 1 + Math.pow(x, 2)); // 1.5 [0.6275]
        derEquations.add(x -> 2 * x + x / (x + 2) + Math.log(x + 2));

        equations.add(x -> Math.pow(x, 3) / A - A * Math.cos(x)); // 1.6 [1.1647]
        derEquations.add(x -> 3 * Math.pow(x, 2) / A + A * Math.sin(x));


        for (int i = 0; i < 6; i++) {
            System.out.println((i + 1) + ")");
            findDots(equations.get(i), derEquations.get(i));
            System.out.println("________________________________");
        }
    }

    private static void approximation(Operationable equation, Operationable derEquation, double x) {
        double lambda = 1 / derEquation.calculate(x);
        double x0;
        int n = 0;
        do {
            System.out.println("Итерация №" + ++n);
            x0 = x;
            x = x0 - lambda * equation.calculate(x0);
            System.out.println("x = " + x);
        } while ((Math.abs(x - x0)) > EPS);
        System.out.println("\nРешение: x = " + x);
    }

    interface Operationable {
        double calculate(double x);
    }

    private static void findDots(Operationable equation, Operationable derEquation) {
        int dotCnt = 1;
        for (double i = minRange; i <= maxRange; i += 0.5) {
            if (equation.calculate(i) * equation.calculate(i + 0.5) < 0 || equation.calculate(i) == 0) {
                System.out.println(dotCnt++ + " корень:");
                approximation(equation, derEquation, i);
                System.out.println("_____________________");
            }
        }
    }

}
