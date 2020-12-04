package br.com.douglas444.patternsampling.strategy.common;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class BayesianErrorEstimation {


    public static double estimateBayesianErrorDistanceBased(final Sample target, final List<Sample> targetConcepts,
                                                            final Set<Integer> knownLabels) {

        if (targetConcepts.isEmpty()) {
            return 1;
        }

        final List<Sample> closestCentroids = new ArrayList<>();

        knownLabels.forEach((knownLabel) -> {

            final Sample closestCentroid = targetConcepts
                    .stream()
                    .filter(centroid -> centroid.getY().equals(knownLabel))
                    .min(Comparator.comparing((Sample sample) -> sample.distance(target)))
                    .orElse(targetConcepts.get(0));

            closestCentroids.add(closestCentroid);

        });

        final double n = Math.pow(1.0 / closestCentroids
                .stream()
                .map(centroid -> centroid.distance(target))
                .min(Double::compare)
                .orElse(0.0), 2);

        if (Double.isInfinite(n)) {
            return 0;
        }

        if (closestCentroids.size() == 1) {
            return 1;
        }

        final double d = closestCentroids
                .stream()
                .map(centroid -> Math.pow(1.0 / centroid.distance(target), 2))
                .reduce(0.0, Double::sum);

        final double probability = n / d;
        final double error = 1 - probability;
        final double maxError = 1 - 1 / (double) knownLabels.size();
        final double normalizedError = error / maxError;

        if (Double.isNaN(normalizedError)) {
            throw new IllegalStateException("Result of estimateBayesError is not a number");
        }

        return normalizedError;
    }


    public static double estimateBayesianErrorNeighbours(final ClusterSummary target,
                                                         final List<ClusterSummary> targetClusterSummaries,
                                                         final Set<Integer> knownLabels) {

        if (targetClusterSummaries.isEmpty()) {
            return 1;
        }

        final List<ClusterSummary> closestClusterSummaries = new ArrayList<>();

        knownLabels.forEach((knownLabel) -> {

            final ClusterSummary closestClusterSummary = targetClusterSummaries
                    .stream()
                    .filter(clusterSummary -> clusterSummary.calculateCentroid().getY().equals(knownLabel))
                    .min(Comparator.comparing((ClusterSummary clusterSummary) ->
                            clusterSummary.calculateCentroid().distance(target.calculateCentroid())))
                    .orElse(targetClusterSummaries.get(0));

            closestClusterSummaries.add(closestClusterSummary);

        });

        final List<ClusterSummary> candidates = new ArrayList<>(targetClusterSummaries);
        candidates.removeAll(closestClusterSummaries);

        final double n = closestClusterSummaries
                .stream()
                .map(centroid -> calculateSimilarity(centroid, target, candidates))
                .max(Double::compare)
                .orElse(0.0);

        if (closestClusterSummaries.size() == 1) {
            return 1;
        }

        final double d = closestClusterSummaries
                .stream()
                .map(centroid -> calculateSimilarity(centroid, target, candidates))
                .reduce(0.0, Double::sum);

        final double probability;
        if (d == 0) {
            probability = 1 / (double) knownLabels.size();
        } else {
            probability = n / d;
        }

        final double error = 1 - probability;
        final double maxError = 1 - 1 / (double) knownLabels.size();
        final double normalizedError = error / maxError;

        if (Double.isNaN(normalizedError)) {
            throw new IllegalStateException("Result of estimateBayesError is not a number");
        }

        return normalizedError;
    }

    private static double calculateSimilarity(final ClusterSummary summary1, ClusterSummary summary2,
                                              final List<ClusterSummary> summaries) {

        final List<ClusterSummary> samplesFiltered = new ArrayList<>(summaries);

        final List<ClusterSummary> nearestNeighborsSummary1 =
                new ArrayList<>(getNearestNeighbors(summary1, samplesFiltered));

        final List<ClusterSummary> nearestNeighborsSummary2 =
                new ArrayList<>(getNearestNeighbors(summary2, samplesFiltered));


        final int sharedNeighbours = intersection(nearestNeighborsSummary1,
                nearestNeighborsSummary2).size();

        return  sharedNeighbours;

    }

    private static List<ClusterSummary> getNearestNeighbors(final ClusterSummary summary,
                                                            List<ClusterSummary> summaries) {

        summaries = new ArrayList<>(summaries);

        final double standardDeviation = summary.calculateStandardDeviation();

        final List<ClusterSummary> neighboursLevel1 = new ArrayList<>();
        for (ClusterSummary clusterSummary : summaries) {

            final double range = Math.sqrt(standardDeviation) + Math.sqrt(clusterSummary.calculateStandardDeviation());

            if (clusterSummary.calculateCentroid().distance(summary.calculateCentroid()) <= range) {
                neighboursLevel1.add(clusterSummary);
            }

        }

        summaries.removeAll(neighboursLevel1);
        final List<ClusterSummary> neighboursLevel2 = new ArrayList<>();

        for (ClusterSummary neighbour : neighboursLevel1) {

            final double neighbourStandardDeviation = neighbour.calculateStandardDeviation();

            for (ClusterSummary clusterSummary : summaries) {

                final double range = Math.sqrt(neighbourStandardDeviation) + Math.sqrt(clusterSummary.calculateStandardDeviation());

                if (clusterSummary.calculateCentroid().distance(summary.calculateCentroid()) <= range) {
                    neighboursLevel2.add(clusterSummary);
                }

            }

        }

        final List<ClusterSummary> neighbours = new ArrayList<>();
        neighbours.addAll(neighboursLevel1);
        neighbours.addAll(neighboursLevel2);

        return neighbours;
    }

    static private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
}
