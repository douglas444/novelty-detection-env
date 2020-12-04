package br.com.douglas444.patternsampling.strategy.common;

import Jama.Matrix;
import br.com.douglas444.mltk.datastructure.ClusterFeature;
import br.com.douglas444.mltk.datastructure.Sample;
import org.openimaj.math.matrix.PseudoInverse;

import java.util.Arrays;

public class JamesSteinEstimator {


    public static double[] calculateShrinkageFactors(final double[] target,
                                                      final ClusterFeature[] clustersFeature,
                                                      final Sample[] centroids) {

        final int dimensions = target.length;

        final double[][][] covarianceMatrices = Arrays.stream(clustersFeature)
                .map(ClusterFeature::calculateCovarianceMatrix)
                .toArray(double[][][]::new);

        final double[][][] invertedCovarianceMatrices = Arrays.stream(covarianceMatrices)
                .map(covarianceMatrix -> PseudoInverse.pseudoInverse(new Matrix(covarianceMatrix)).getArray())
                .toArray(double[][][]::new);

        final double[][][] differenceVectors = Arrays.stream(centroids).map(centroid -> {
            final double[][] difference = new double[centroid.getX().length][1];
            for (int i = 0; i < dimensions; ++i) {
                difference[i][0] = centroid.getX()[i] - target[i];
            }
            return difference;
        }).toArray(double[][][]::new);

        final double[][][] transposeDifferenceVectors = Arrays.stream(differenceVectors).map(difference -> {
            final double[][] transpose = new double[1][difference.length];
            for (int i = 0; i < dimensions; ++i) {
                transpose[0][i] = difference[i][0];
            }
            return transpose;
        }).toArray(double[][][]::new);

        final double[] factors = new double[centroids.length];

        for (int i = 0; i < centroids.length; ++i) {

            double[][] m = multiplyMatrix(
                    1, dimensions, transposeDifferenceVectors[i],
                    dimensions, dimensions, invertedCovarianceMatrices[i]);

            m = multiplyMatrix(1, dimensions, m, dimensions, 1, differenceVectors[i]);

            double factor = 1 - ((dimensions - 2) / m[0][0]);
            factor = factor < 0 ? 0 : factor;
            factor = factor > 1 ? 1 : factor;
            factors[i] = factor;

        }

        normalize(factors);

        return factors;

    }

    public static double[][] multiplyMatrix(
            final int row1, final int col1, final double[][] A,
            final int row2, final int col2, final double[][] B)
    {
        int i, j, k;

        if (row2 != col1) {
            throw new IllegalArgumentException();
        }

        final double[][] result = new double[row1][col2];

        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    result[i][j] += A[i][k] * B[k][j];
            }
        }

        return result;
    }

    private static void normalize(final double[] vector) {


        if (vector.length == 0) {
            throw new IllegalArgumentException();
        }

        final double min = Arrays.stream(vector).min().orElse(0);
        final double max = Arrays.stream(vector).max().orElse(0);

        for (int i = 0; i < vector.length; ++i) {
            vector[i] = (1 - min) * (vector[i] - min) / (max - min) + min;
        }

    }

}
