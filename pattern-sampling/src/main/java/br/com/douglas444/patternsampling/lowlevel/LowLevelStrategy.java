package br.com.douglas444.patternsampling.lowlevel;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;

import java.util.List;
import java.util.Set;

public interface LowLevelStrategy {
    boolean isNovelty(final ClusterSummary targetSummary, final List<Sample> samples, final List<ClusterSummary> summaries, final Set<Integer> knownLabels);
    boolean isKnown(final ClusterSummary targetSummary, final List<Sample> samples, final List<ClusterSummary> summaries, final Set<Integer> knownLabels);
}
