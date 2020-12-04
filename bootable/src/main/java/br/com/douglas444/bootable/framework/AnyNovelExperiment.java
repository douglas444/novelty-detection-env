package br.com.douglas444.bootable.framework;

import anynovel.conceptEvolution.AnyNovelLauncher;
import anynovel.conceptEvolution.ExpLauncher;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.InterceptionResult;
import br.com.douglas444.patternsampling.types.InterceptionResultSummary;

import java.util.ArrayList;
import java.util.List;

public class AnyNovelExperiment {

    public static InterceptionResultSummary execute(Strategy strategy, String[] args) throws Exception {

        final List<InterceptionResult> interceptionResults = new ArrayList<>();

        AnyNovelLauncher.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.define((context) -> {
            interceptionResults.add(strategy.intercept(context));
        });

        ExpLauncher.main(args);

        return new InterceptionResultSummary("", strategy.getParameter1(), strategy.getParameter2(),
                interceptionResults);
    }
}
