package br.com.douglas444.bootable.framework;

import br.com.douglas444.datastreamutils.DSClassifierExecutor;
import br.com.douglas444.datastreamutils.DSFileReader;
import br.com.douglas444.minas.MINASBuilder;
import br.com.douglas444.minas.MINASController;
import br.com.douglas444.minas.interceptor.MINASInterceptor;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.InterceptionResult;
import br.com.douglas444.patternsampling.types.InterceptionResultSummary;
import br.com.douglas444.patternsampling.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MINASExperiment {

    public static InterceptionResultSummary execute(final Strategy strategy, final String[] args) throws Exception {

        final String configurationFile = args[0];
        final String[] files = Arrays.copyOfRange(args, 1, args.length);
        final DSFileReader[] fileReaders = new DSFileReader[files.length];

        for (int i = 0; i < files.length; i++) {
            fileReaders[i] = new DSFileReader(",", FileUtil.getFileReader(files[i]));
        }

        final List<InterceptionResult> interceptionResults = new ArrayList<>();

        final MINASInterceptor interceptor = new MINASInterceptor();
        interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.define((context) -> {
            interceptionResults.add(strategy.intercept(context));
        });

        final MINASBuilder minasBuilder = new MINASBuilder(configurationFile, interceptor);
        final MINASController minasController = minasBuilder.build();
        DSClassifierExecutor.start(minasController, false, 0, fileReaders);

        final String frameworkOutput = minasController.getLog()
                + "\n" + minasController.getDynamicConfusionMatrix().toString();

        return new InterceptionResultSummary(frameworkOutput, strategy.getParameter1(), strategy.getParameter2(),
                interceptionResults);

    }

}
