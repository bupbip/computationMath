import java.util.LinkedList;
import java.util.List;

public class Task2 {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
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


        for (int i = 0; i < equations.size(); i++) {
            System.out.println(ANSI_RED + (i+1) + ")" + ANSI_RESET);
            findDots(equations.get(i), derEquations.get(i));
            System.out.println("________________________________");
        }

        LinkedList<OperationableNewton> task21 = new LinkedList<>();
        task21.add((x, y, a, alpha, betta) -> Math.tan(x * y + a) - Math.pow(x, 2));
        task21.add((x, y, a, alpha, betta) -> Math.pow(x, 2) * alpha + Math.pow(y, 2) * betta - 1);
        task21.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * y - 2 * x);
        task21.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * x);
        task21.add((x, y, a, alpha, betta) -> 2 * x * alpha);
        task21.add((x, y, a, alpha, betta) -> 2 * y * betta);

        LinkedList<Dot> dots = new LinkedList<>();
        dots.add(new Dot(0.2, 0.6, 2, 0.8, 0.5));
        dots.add(new Dot(0.4, 0.8, 2, -0.3, 0.6));
        dots.add(new Dot(0.3, 0.2, 3, 1, 0.5));
        dots.add(new Dot(0, 0.6, 2, 0.6, 0.6));

        LinkedList<OperationableNewton> task22 = new LinkedList<>();
        task22.add((x, y, a, alpha, betta) -> Math.pow(x, 2) + Math.pow(y, 2) - 2);
        task22.add(((x, y, a, alpha, betta) -> Math.exp(x - 1) + Math.pow(y, 3) - 2));
        task22.add((x, y, a, alpha, betta) -> 2 * x);
        task22.add((x, y, a, alpha, betta) -> 2 * y);
        task22.add((x, y, a, alpha, betta) -> Math.exp(x - 1));
        task22.add((x, y, a, alpha, betta) -> 2 * Math.pow(y, 2));

        LinkedList<Dot> dots2 = new LinkedList<>();
        dots2.add(new Dot(0, 0, 0, -0.5, 1));

        for (int i = 0; i < dots.size(); i++) {
            System.out.println(ANSI_RED + "\n№ 2.1." + (i + 1) + ANSI_RESET);
            newton(task21, dots.get(i));
            System.out.println("________________________________");
        }

        System.out.println(ANSI_RED + "№2.2" + ANSI_RESET);
        newton(task22, dots2.get(0));
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
        System.out.println(ANSI_GREEN + "\nРешение: x = " + x + ANSI_RESET);
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

    public static void newton(List<OperationableNewton> data, Dot dot) {
        OperationableNewton f1 = data.get(0);
        OperationableNewton f2 = data.get(1);
        OperationableNewton a = data.get(2);
        OperationableNewton b = data.get(3);
        OperationableNewton c = data.get(4);
        OperationableNewton d = data.get(5);
        int iterCnt = 1;
        double x1 = dot.x, y1 = dot.y, a2 = dot.a, alpha = dot.alpha, betta = dot.betta;
        System.out.println("x1 = " + x1);
        System.out.println("x2 = " + x1);
        double x, y;
        do {
            x = x1;
            y = y1;
            double func1 = f1.calculate(x, y, a2, alpha, betta);
            double func2 = f2.calculate(x, y, a2, alpha, betta);
            // Матрица Якоби
            double detFunc1x = a.calculate(x, y, a2, alpha, betta);
            double detFunc1y = b.calculate(x, y, a2, alpha, betta);
            double detFunc2x = c.calculate(x, y, a2, alpha, betta);
            double detFunc2y = d.calculate(x, y, a2, alpha, betta);

            double detA1 = (func1 * detFunc1y) - (func2 * detFunc2y);
            double detA2 = (func2 * detFunc1x) - (func1 * detFunc2x);

            double detJ = (detFunc1x * detFunc2y) - (detFunc1y * detFunc2x);
            x1 = x - (detA1 / detJ);
            y1 = y - (detA2 / detJ);
            iterCnt++;
        } while (Math.sqrt(Math.pow(f1.calculate(x1,y1,a2,alpha,betta),2) + Math.pow(f2.calculate(x1,y1,a2,alpha,betta),2)) > EPS &&
                Math.sqrt(Math.pow(x1-x,2) + Math.pow(y1-y,2)) > EPS);
        System.out.println(ANSI_GREEN + "Ответ: (" + x1 + "; " + y1 + ")" + ANSI_RESET);
        System.out.println("Количество итераций: " + iterCnt);
    }

    interface Operationable {
        double calculate(double x);

    }

    interface OperationableNewton {
        double calculate(double x, double y, double a, double alpha, double betta);
    }

    static class Dot {
        double x, y, a, alpha, betta;

        public Dot(double a, double alpha, double betta, double x, double y) {
            this.a = a;
            this.alpha = alpha;
            this.betta = betta;
            this.x = x;
            this.y = y;
        }
    }

    private double scalarProduct(double[] a, double[] b) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

}