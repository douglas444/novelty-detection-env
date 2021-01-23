package br.com.douglas444.patternsampling.common;

import br.com.douglas444.datastreamutils.interceptor.Context;
import br.com.douglas444.mltk.datastructure.PseudoPoint;

import java.util.List;

public class WarmUpContext implements Context {

    private List<PseudoPoint> pseudoPoints;

    public List<PseudoPoint> getPseudoPoints() {
        return pseudoPoints;
    }

    public WarmUpContext setPseudoPoints(List<PseudoPoint> pseudoPoints) {
        this.pseudoPoints = pseudoPoints;
        return this;
    }
}
