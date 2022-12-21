import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Task5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Для 5.1 введите 1, для 5.2 введите 2");
        int mode = scanner.nextInt();
        if (mode == 1) {
            System.out.println("5.1");
            List<List<Point>> tasks = preparePoints();
            int count = 1;
            for (List<Point> task : tasks) {
                XYSeries lagrangePolynomeSeries = new XYSeries("Полином Лагранжа");
                XYSeries newtonPolynomeSeries = new XYSeries("Полином Ньютона");


                Function<Double, Double> newtonPolynomial = createNewtonPolynomial(task);
                for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.1) {
                    lagrangePolynomeSeries.add(x, lagrangePolynome(task, x));
                    newtonPolynomeSeries.add(x, newtonPolynomial.apply(x));
                }
                showGraphic(lagrangePolynomeSeries, "Полином лагранжа, задание " + count);
                showGraphic(newtonPolynomeSeries, "Полином ньютона, задание " + count);

                count++;
            }
        } else if (mode == 2) {
            System.out.println("5.2");

            List<FunctionData> functions = new ArrayList<>();
            functions.add(new FunctionData(((x) -> Math.pow(x, 3) - 6.5 * Math.pow(x, 2) + 11 * x - 4), 2, 4));
            functions.add(new FunctionData((x -> 3 * Math.cos(Math.PI * x / 8)), 0.5, 3));
            functions.add(new FunctionData((x -> Math.exp(-x / 4) * Math.sin(x / 3)), 4, 10));
            functions.add(new FunctionData((x -> 8 * x * Math.exp(-1 * Math.pow(x, 2) / 12)), 0, 12));
            List<Point> task;
            List<Integer> splittings = new ArrayList<>(Arrays.asList(3, 4, 8, 10, 16, 64, 256));
            int count = 1;
            int taskNum;

            System.out.println("Для 5.2.1 введите 1, для 5.2.2 введите 2");
            taskNum = scanner.nextInt();
            for (FunctionData function : functions) {
                if (taskNum == 1) {
                    for (Integer splitting : splittings) {
                        task = generatePoints(splitting, function);
                        XYSeries lagrangePolynomeSeries = new XYSeries("полином Лагранжа");
                        XYSeries newtonPolynomeSeries = new XYSeries("полином Ньютона");
                        XYSeries functionSeries = new XYSeries("функция");

                        Function<Double, Double> newtonPolynomial = createNewtonPolynomial(task);
                        for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.001) {
                            lagrangePolynomeSeries.add(x, lagrangePolynome(task, x));
                            newtonPolynomeSeries.add(x, newtonPolynomial.apply(x));
                            functionSeries.add(x, function.formula().calculate(x));
                        }
                        showGraphic(lagrangePolynomeSeries, "полином лагранжа, задание " + count + ", колличество разбиений " + splitting);
                        showGraphic(newtonPolynomeSeries, "полином ньютона, задание " + count + ", колличество разбиений " + splitting);
                        showGraphic(functionSeries, "график функции, задание " + count + ", колличество разбиений " + splitting);
                    }

                } else if (taskNum == 2) {
                    List<Point> points = generatePoints(16, function);
                    double x, lagrange, newton, functionY;
                    System.out.println("Уравнение " + count + "\nВведите значение x в пределах [" + function.a() + ";" + function.b() + "]");
                    x = scanner.nextDouble();

                    lagrange = lagrangePolynome(points, x);
                    newton = createNewtonPolynomial(points).apply(x);
                    functionY = function.formula().calculate(x);

                    System.out.println("Значение полинома Лагранжа: " + lagrange);
                    System.out.println("Значение полинома Ньютона: " + newton);
                    System.out.println("Значение функции: " + functionY + "\n");

                }
                count++;
            }
        }


    }

    public static void showGraphic(XYSeries series, String title) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame = new JFrame("MinimalStaticChart");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static double lagrangePolynome(List<Point> points, double x) {
        double y = 0;
        for (int i = 0; i < points.size(); i++) {
            y += points.get(i).getY() * lagrangeMultiplier(points, i, x);
        }
        return y;
    }

    public static double lagrangeMultiplier(List<Point> points, int i, double x) {
        double result = 1;
        for (int j = 0; j < points.size(); j++) {
            if (j != i) {
                result *= (x - points.get(j).getX()) / (points.get(i).getX() - points.get(j).getX());
            }
        }
        return result;
    }

    public static double calculateDividedDifferences(List<Point> points, int k) {
        double result = 0;
        for (int j = 0; j <= k; j++) {
            double mul = 1;
            for (int i = 0; i <= k; i++) {
                if (j != i) {
                    mul *= (points.get(j).getX() - points.get(i).getX());
                }
            }
            result += points.get(j).getY() / mul;
        }
        return result;
    }

    public static Function<Double, Double> createNewtonPolynomial(List<Point> points) {
        double[] divDiff = new double[points.size() - 1];
        for (int i = 1; i < points.size(); i++) {
            divDiff[i - 1] = calculateDividedDifferences(points, i);
        }
        Function<Double, Double> newtonPolynomial = (xVal) -> {
            double result = points.get(0).getY();
            for (int k = 1; k < points.size(); k++) {
                double mul = 1;
                for (int j = 0; j < k; j++) {
                    mul *= (xVal - points.get(j).getX());
                }
                result += divDiff[k - 1] * mul;
            }
            return result;
        };
        return newtonPolynomial;
    }

    private static List<List<Point>> preparePoints() {
        List<List<Point>> tasks = new ArrayList<>();
        tasks.add(Arrays.asList(
                new Point(-1.0, 0.86603),
                new Point(0.0, 1.0),
                new Point(1.0, 0.86603),
                new Point(2.0, 0.50),
                new Point(3.0, 0.0),
                new Point(4.0, -0.50)
        ));
        tasks.add(Arrays.asList(
                new Point(-0.9, -0.36892),
                new Point(0.0, 0.0),
                new Point(0.9, 0.36892),
                new Point(1.8, 0.85408),
                new Point(2.7, 1.7856),
                new Point(3.6, 6.3138)
        ));
        tasks.add(Arrays.asList(
                new Point(1.0, 2.4142),
                new Point(1.9, 1.0818),
                new Point(2.8, 0.50953),
                new Point(3.7, 0.11836),
                new Point(4.6, -0.24008),
                new Point(5.5, -0.66818)
        ));
        return tasks;
    }

    private static List<Point> generatePoints(int countOfPoints, FunctionData functionData) {
        List<Point> points = new ArrayList<>();
        double step = Math.abs(functionData.b() - functionData.a()) / countOfPoints;
        double currentX = functionData.a();
        for (int i = 0; i < countOfPoints; i++) {
            points.add(new Point(currentX, functionData.formula().calculate(currentX)));
            currentX += step;
        }
        return points;
    }


}

record FunctionData(FormulaOneParameter formula, double a, double b) {

}

interface FormulaOneParameter {
    double calculate(double x);
}

class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}


