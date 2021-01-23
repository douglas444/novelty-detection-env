package br.com.douglas444.patternsampling.strategy.common;

import br.com.douglas444.mltk.datastructure.ClusterSummary;

import java.util.List;
import java.util.Set;

public interface Valuable {

    double getValue(final ClusterSummary targetClusterSummary,
                    final List<ClusterSummary> knownClusterSummaries,
                    final Set<Integer> knownLabels);

}
