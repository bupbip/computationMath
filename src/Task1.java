import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Task1 {

    public static int solveNumber = 0;
    public static final double eps = Math.pow(10, -8);

    public static double a = 2, n = 2;

    public static void main(String[] args) {
        LinkedList<Operationable> equations = new LinkedList<>();
        LinkedList<Points> points = new LinkedList<>();
        Scanner in = new Scanner(System.in);
        equations.add(x -> Math.sin(x) - 2 * Math.pow(x, 2) + 0.5);
        equations.add(x -> Math.pow(x,n) - a);
        equations.add(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1);
        equations.add(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) - 2);
        equations.add(x -> Math.log(x) - (1 / (1 + Math.pow(x, 2))));
        equations.add(x -> Math.pow(3, x) - 5 * Math.pow(x, 2) + 1);
        equations.add(x -> Math.sin(x));
        equations.add(x -> Math.log(x) - 1);
        points.add(new Points(0,1,1));
        points.add(new Points(1,2,1));
        points.add(new Points(0,1,0));
        points.add(new Points(-1,0,-1));
        points.add(new Points(1,2,1));
        points.add(new Points(0,1,1));
        points.add(new Points(3));
        points.add(new Points(2));
        for (int i = 0; i < equations.size(); i++) {
            System.out.println((i + 1) + ")");
            if(i < 6) dichotomy(equations.get(0), points.get(i).x1, points.get(i).x2);
            newton(equations.get(i), points.get(i).newtonNumber);
            System.out.println("________________________________");
        }
        System.out.println("Спасибо за внимание! Всем пока!");
    }

    public static void dichotomy(Operationable func, double firstPoint, double secondPoint) {
        int iterationNumber = 0;
        double approximation = secondPoint - firstPoint;
        if ((func.calculate(firstPoint) * func.calculate(secondPoint)) > 0) {
            System.out.println("На данном отрезке нет решений.\n");
            return;
        }
        while (approximation > eps) {
            double currentX = (firstPoint + secondPoint) / 2;
            // значение функции в первой точке пр-ка должно иметь противоположный знак значению в точке Х
            if (func.calculate(firstPoint) * func.calculate(currentX) < 0) {
                secondPoint = currentX;
            } else {
                firstPoint = currentX;
            }
            iterationNumber++;
            approximation = secondPoint - firstPoint;
            System.out.println("Номер итерации = " + iterationNumber);
            System.out.println("Приближение = " + approximation);
        }
        System.out.println("Ответ = " + (firstPoint + secondPoint) / 2);
    }

    public static void newton(Operationable func,double choosePoint) {
        int iterationNumber = 0;
        double currentX = choosePoint;
        double nextX;
        double approximation;
        do {
            double functionIncrement = currentX * eps;
            double funcValue = func.calculate(currentX);
            double diffFuncValue = (func.calculate(currentX + functionIncrement) - funcValue) / functionIncrement;
            nextX = currentX - (funcValue / diffFuncValue);
            approximation = Math.abs(nextX - currentX);
            currentX = nextX;
            iterationNumber++;
            System.out.println("Номер итерации = " + iterationNumber);
            System.out.println("Приближение = " + approximation);
        } while (approximation > eps);
        System.out.println("Ответ = " + nextX);
    }
}

class Points {

    double x1;
    double x2;
    double newtonNumber;

    public Points(double x1, double x2, double newtonNumber) {
        this.x1 = x1;
        this.x2 = x2;
        this.newtonNumber = newtonNumber;
    }

    public Points(double newtonNumber) {
        this.newtonNumber = newtonNumber;
    }

}

interface Operationable {
    double calculate(double x);
}