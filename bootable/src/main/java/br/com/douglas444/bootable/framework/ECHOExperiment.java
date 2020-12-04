package br.com.douglas444.bootable.framework;

import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.InterceptionResult;
import br.com.douglas444.patternsampling.types.InterceptionResultSummary;
import echo.mineClass.Miner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ECHOExperiment {

    public static InterceptionResultSummary execute(Strategy strategy, String[] args) throws Exception {

        final List<InterceptionResult> interceptionResults = new ArrayList<>();

        Miner.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.define((context) -> {
            interceptionResults.add(strategy.intercept(context));
        });

        Miner.random = new Random(0);
        Miner.main(args);

        return new InterceptionResultSummary("", strategy.getParameter1(), strategy.getParameter2(),
                interceptionResults);

    }

}
