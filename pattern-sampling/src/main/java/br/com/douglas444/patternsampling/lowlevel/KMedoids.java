package br.com.douglas444.patternsampling.lowlevel;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.common.Oracle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KMedoids implements LowLevelStrategy {

    private int k;

    public KMedoids(int k) {
        this.k = k;
    }

    @Override
    public boolean isNovelty(final ClusterSummary clusterSummary, final List<Sample> samples,
                             final List<ClusterSummary> summaries, final Set<Integer> knownLabels) {

        final Sample centroid = clusterSummary.calculateCentroid();
        final List<Sample> sortedSamples = new ArrayList<>(samples);
        sortedSamples.sort(Comparator.comparing(sample -> sample.distance(centroid)));
        final List<Sample> kMedoids = sortedSamples.subList(0, k);
        return Oracle.isNovelty(kMedoids, knownLabels);

    }

    @Override
    public boolean isKnown(final ClusterSummary clusterSummary, final List<Sample> samples,
                           final List<ClusterSummary> summaries, final Set<Integer> knownLabels) {


        final Sample centroid = clusterSummary.calculateCentroid();
        final List<Sample> sortedSamples = new ArrayList<>(samples);
        sortedSamples.sort(Comparator.comparing(sample -> sample.distance(centroid)));
        final List<Sample> kMedoids = sortedSamples.subList(0, k);
        return !Oracle.isNovelty(kMedoids, knownLabels);
    }

    public int getK() {
        return k;
    }

    public KMedoids setK(int k) {
        this.k = k;
        return this;
    }
}
