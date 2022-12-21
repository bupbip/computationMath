import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.*;

public class Task4 {
    final static double EPS = Math.pow(10, -9);
    final static double L = 10;
    final static String[] normList = new String[]{"||X||∞", "||X||₁", "||X||₂ₗ"};

    public static void main(String[] args) {

        List<double[][]> eqs = new LinkedList<>();

        eqs.add(
                new double[][]{
                        {2, 2, -1, 1, 4},
                        {4, 3, -1, 2, 6},
                        {8, 5, -3, 4, 12},
                        {3, 3, -2, 2, 6}}
        );
        eqs.add(
                new double[][]{
                        {4, 1, -1, 1, -2},
                        {1, 4, -1, -1, -1},
                        {-1, -1, 5, 1, 0},
                        {1, -1, 1, 3, 1}}
        );
        eqs.add(
                new double[][]{
                        {2.8, 2.1, -1.3, 0.3, 1},
                        {-1.4, 4.5, -7.7, 1.3, 1},
                        {0.6, 2.1, -5.8, 2.4, 1},
                        {3.5, -6.5, 3.2, -7.9, 1}}
        );
        eqs.add(
                new double[][]{
                        {4, 1, 1, 0, 1, 6},
                        {1, 3, 1, 1, 0, 6},
                        {1, 1, 5, -1, -1, 6},
                        {0, 1, -1, 4, 0, 6},
                        {1, 0, -1, 0, 4, 6}}
        );
        eqs.add(
                new double[][]{
                        {4, -1, 0, -1, 0, 0, 0},
                        {-1, 4, -1, 0, -1, 0, 5},
                        {0, -1, 4, 0, 0, -1, 0},
                        {-1, 0, 0, 4, -1, 0, 6},
                        {0, -1, 0, -1, 4, -1, -2},
                        {0, 0, -1, 0, -1, 4, 6}}
        );
        eqs.add(
                new double[][]{
                        {4, -1, 0, 0, 0, 0, 0},
                        {-1, 4, -1, 0, 0, 0, 5},
                        {0, -1, 4, 0, 0, 0, 0},
                        {0, 0, 0, 4, -1, 0, 6},
                        {0, 0, 0, -1, 4, -1, -2},
                        {0, 0, 0, 0, -1, 4, 6}}
        );


        for (int i = 0; i < eqs.size(); i++) {
            System.out.println("Решение системы №" + (i + 1));
            for (int normNum = 0; normNum < normList.length; normNum++) {
                System.out.println("Для нормы " + normList[normNum]);
                minimumDiscrepancyOrSpeedDescentMethod("МН", eqs.get(i), normNum);
                minimumDiscrepancyOrSpeedDescentMethod("НС", eqs.get(i), normNum);
            }
        }

        System.out.println("Задача 2. Методом скорейшего спуска найти минимум функций");
        List<RealMatrix> A = new ArrayList<>();
        List<RealVector> b = new ArrayList<>();
        List<RealVector> x0 = new ArrayList<>();

        A.add(MatrixUtils.createRealMatrix(new double[][]{{2, 0}, {0, 2}}));
        b.add(MatrixUtils.createRealVector(new double[]{2, 4}));
        x0.add(MatrixUtils.createRealVector(new double[]{2, 3}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{{4, -2}, {-2, 2}}));
        b.add(MatrixUtils.createRealVector(new double[]{-2, 2}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.5, 0.5}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{{2, -2, 0}, {-2, 4, 0}, {0, 0, 2}}));
        b.add(MatrixUtils.createRealVector(new double[]{1, 0, -2}));
        x0.add(MatrixUtils.createRealVector(new double[]{2, 1, -1}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{{2, -1, 0}, {-1, 2, 0}, {0, 0, 2}}));
        b.add(MatrixUtils.createRealVector(new double[]{-1, 0, 2}));
        x0.add(MatrixUtils.createRealVector(new double[]{-1, -1, 0}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{
                {4, -1, 0, -1, 0, 0},
                {-1, 4, -1, 0, -1, 0},
                {0, -1, 4, 0, 0, -1},
                {-1, 0, 0, 4, -1, 0},
                {0, -1, 0, -1, 4, -1},
                {0, 0, -1, 0, -1, 4}
        }));
        b.add(MatrixUtils.createRealVector(new double[]{0, 5, 0, 6, -2, 6}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.9, 1.9, 0.9, 1.9, 0.9, 1.9}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{{2, -0.2}, {-0.2, 2}}));
        b.add(MatrixUtils.createRealVector(new double[]{2.2, -2.2}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.5, -0.5}));

        A.add(MatrixUtils.createRealMatrix(new double[][]{{10, -9}, {-9, 8.15}}));
        b.add(MatrixUtils.createRealVector(new double[]{-1, 0}));
        x0.add(MatrixUtils.createRealVector(new double[]{16, -19}));

        for (int i = 0; i < A.size(); ++i) {
            System.out.println("Система № " + (i + 1));
            for (int j = 0; j < normList.length; ++j) {
                System.out.println("\nНорма " + normList[j]);
                // Вывод результатов
                System.out.println("Точка минимума функции:");
                speedyDescent(A.get(i), b.get(i), x0.get(i), j);
            }
        }
        System.out.println();

    }


    private static void minimumDiscrepancyOrSpeedDescentMethod(String methodName, double[][] matrix, int norm) {
        RealVector xValues = MatrixUtils.createRealVector(new double[matrix[0].length - 1]);
        RealVector fValues = MatrixUtils.createRealVector(new double[matrix[0].length - 1]);
        RealVector xValuesNew = xValues.copy();

        // матрица неизвестных
        RealMatrix aMatrix = MatrixUtils.createRealMatrix(matrix.length, matrix[0].length - 1);

        for (int i = 0; i < aMatrix.getRowDimension(); i++) {
            for (int j = 0; j < aMatrix.getColumnDimension(); j++) {
                aMatrix.addToEntry(i, j, matrix[i][j]);
            }
            fValues.addToEntry(i, matrix[i][matrix[0].length - 1]);
        }

        RealMatrix aMatrixT = aMatrix.transpose();
        if (!aMatrixT.equals(aMatrix)) { // проверка на симметричную матрицу
            System.out.println("Матрица не симметрична");
            aMatrix = aMatrixT.multiply(aMatrix);
            fValues = aMatrixT.operate(fValues);
        }
        System.out.println(aMatrix);
        System.out.println(fValues);

        int iterationsNumber = 0; // Количество итераций

        do {
            RealVector residual = aMatrix.operate(xValuesNew).subtract(fValues);
            double tau = methodName.equals("МН") ?
                    getTauForDisperancy(residual, aMatrix) : getTauForSpeedDescent(residual, aMatrix);
            xValues = xValuesNew.copy();

            xValuesNew = xValuesNew.subtract(residual.mapMultiply(tau));
            iterationsNumber++;
        } while (findNorm(norm, xValuesNew.toArray(), xValues.toArray()) > EPS);

        System.out.print("Решение: " + xValuesNew);
        System.out.printf("\tКоличество итераций: %d\n", iterationsNumber);
    }

    private static double getTauForDisperancy(RealVector r, RealMatrix aMatrix) {

        RealVector Ar = aMatrix.operate(r);
        double Arr = Ar.dotProduct(r); // вектор на вектор
        double ArAr = Ar.dotProduct(Ar);
        return Arr / ArAr;
    }


    /**
     * Подсчёт длины шага вдоль направления градиента
     * @param r вектор невязки
     * @param aMatrix матрица системы
     * @return длина шага
     */
    private static double getTauForSpeedDescent(RealVector r, RealMatrix aMatrix) {
        double rr = r.dotProduct(r);
        RealVector Ar = aMatrix.operate(r);
        double Arr = Ar.dotProduct(r);
        if (Arr == 0) {
            return 0;
        }
        return rr / Arr;
    }


    private static double findNorm(int numOfNorm, double[] currentVariableValues, double[] previousVariableValues) {
        double norm = 0;
        int size = currentVariableValues.length;
        switch (numOfNorm) {
            // Для нормы ||X||∞
            case (0) -> {
                double[] values = new double[size];
                for (int i = 0; i < size; ++i) {
                    values[i] = Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                DoubleSummaryStatistics stat = Arrays.stream(values).summaryStatistics();
                return stat.getMax();
            }
            // Для нормы ||X||₁
            case (1) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                return norm;
            }
            // Для нормы ||X||₂ₗ
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(currentVariableValues[i] - previousVariableValues[i], L * 2);
                }
                return Math.pow(norm, 1. / (L * 2));
            }
        }
        return 0;
    }

    public static void speedyDescent(RealMatrix A, RealVector b, RealVector xo, int numOfNorm) {
        int size = xo.getDimension();

        double[] previousVariableValues = new double[size];

        double norm;
        int iterationsNumber = 0;

        do {
            double[] currentVariableValues = new double[size];
            RealVector xn;

            RealVector gradientVector = (A.operate(xo)).subtract(b);
            double tau = getTauForSpeedDescent(gradientVector, A);

            xn = xo.subtract(gradientVector.mapMultiply(tau));

            for (int i = 0; i < size; ++i) {
                currentVariableValues[i] = xo.getEntry(i);
            }
            norm = findNorm(numOfNorm, previousVariableValues, currentVariableValues);

            previousVariableValues = currentVariableValues;
            xo = xn;
            ++iterationsNumber;
        } while (norm > EPS);

        for (int i = 0; i < size; ++i) {
            System.out.print("x" + (i + 1) + " = " + xo.getEntry(i) + " ");
        }
        System.out.println("\nКол-во итераций: " + iterationsNumber);
    }


}
