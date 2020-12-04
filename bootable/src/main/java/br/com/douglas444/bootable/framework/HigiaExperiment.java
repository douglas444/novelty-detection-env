package br.com.douglas444.bootable.framework;

import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.InterceptionResult;
import br.com.douglas444.patternsampling.types.InterceptionResultSummary;
import higia.Run;
import higia.kNN_kem;

import java.util.ArrayList;
import java.util.List;

public class HigiaExperiment {

    public static InterceptionResultSummary execute(Strategy strategy, String[] args) throws Exception {

        final List<InterceptionResult> interceptionResults = new ArrayList<>();

        kNN_kem.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.define((context) -> {
            interceptionResults.add(strategy.intercept(context));
        });

        Run.main(args);

        return new InterceptionResultSummary("", strategy.getParameter1(), strategy.getParameter2(),
                interceptionResults);
    }
}
