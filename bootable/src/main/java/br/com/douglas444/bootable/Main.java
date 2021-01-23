package br.com.douglas444.bootable;

import br.com.douglas444.bootable.enumeration.DatasetEnum;
import br.com.douglas444.bootable.enumeration.FrameworkEnum;
import br.com.douglas444.bootable.enumeration.LowLevelStrategyEnum;
import br.com.douglas444.bootable.enumeration.StrategyEnum;
import br.com.douglas444.bootable.enumeration.optionHandler.DatasetOptionHandler;
import br.com.douglas444.bootable.enumeration.optionHandler.FrameworkOptionHandler;
import br.com.douglas444.bootable.enumeration.optionHandler.LowLevelStrategyOptionHandler;
import br.com.douglas444.bootable.enumeration.optionHandler.StrategyOptionHandler;
import br.com.douglas444.bootable.framework.AnyNovelExperiment;
import br.com.douglas444.bootable.framework.ECHOExperiment;
import br.com.douglas444.bootable.framework.HigiaExperiment;
import br.com.douglas444.bootable.framework.MINASExperiment;
import br.com.douglas444.bootable.framework.arguments.AnyNovelArguments;
import br.com.douglas444.bootable.framework.arguments.ECHOArguments;
import br.com.douglas444.bootable.framework.arguments.HigiaArguments;
import br.com.douglas444.bootable.framework.arguments.MINASArguments;
import br.com.douglas444.bootable.out.Printable;
import br.com.douglas444.patternsampling.lowlevel.KMedoids;
import br.com.douglas444.patternsampling.lowlevel.KMostOrLessInformative;
import br.com.douglas444.patternsampling.lowlevel.LowLevelStrategy;
import br.com.douglas444.patternsampling.strategy.*;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.InterceptionResultSummary;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.util.ArrayList;
import java.util.List;

public class Main {

    @Option(name="--strategy", aliases = {"--s"}, handler= StrategyOptionHandler.class, required = true)
    private StrategyEnum strategyOption;

    @Option(name="--lowLevelStrategy", aliases = {"--ls"}, handler= LowLevelStrategyOptionHandler.class, required = true)
    private LowLevelStrategyEnum lowLevelStrategyOption;

    @Option(name="--parameter1", aliases = {"--p1"}, forbids={"--variateParameter1"})
    private Double parameter1;

    @Option(name="--parameter2", aliases = {"--p2"}, forbids={"--variateParameter2"})
    private Double parameter2;

    @Option(name="--lowLevelStrategyParameter1", aliases = {"--lsp1"}, required = true)
    private Double lowLevelStrategyParameter1;

    @Option(name="--framework", aliases = {"--f"}, handler= FrameworkOptionHandler.class, required = true)
    private FrameworkEnum frameworkOption;

    @Option(name="--dataset", aliases = {"--d"}, handler= DatasetOptionHandler.class, required = true)
    private DatasetEnum datasetOption;

    @Option(name="--variateParameter1", handler = StringArrayOptionHandler.class, aliases = {"--vp1"},
            forbids={"--variateParameter2"})
    private String[] variateParameter1;

    @Option(name="--variateParameter2", handler = StringArrayOptionHandler.class, aliases = {"--vp2"},
            forbids={"--variateParameter1"})
    private String[] variateParameter2;

    private double initialValue;

    private double increment;

    private int times;

    public static void main(final String[] args) throws Exception {
        new Main().doMain(args);
    }

    private void doMain(final String[] args) throws Exception {

        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        this.additionalArgumentsValidation();

        String folderId;

        if (this.variateParameter1 != null) {
            folderId = "/varying_parameter_1/";
        } else if (this.variateParameter2 != null) {
            folderId = "/varying_parameter_2/";
        } else {
            folderId = "/fixed_parameters/";
        }

        folderId += frameworkOption + "/strategy_" + strategyOption + "/dataset_" + datasetOption;

        System.out.println("Experiment started...");
        Printable.asFile(folderId,"arguments.txt", args);

        if (this.variateParameter1 != null) {

            final List<InterceptionResultSummary> resultSummaries = new ArrayList<>();
            double value = this.initialValue;

            for (int i = 0; i < this.times; ++i) {
                resultSummaries.add(execute(value, this.parameter2));
                System.out.println("Execution " + (i + 1) + "/" + this.times + " finished");
                value += this.increment;
            }

            Printable.asFile(folderId, "frameworkOutput.txt", resultSummaries.get(0).getFrameworkOutput());
            Printable.asFileParameter1XRecall(folderId, resultSummaries);
            Printable.asFileParameter1XPrecision(folderId, resultSummaries);
            Printable.asFileParameter1XF1(folderId, resultSummaries);
            Printable.asFileRecallXPrecision(folderId, resultSummaries);
            Printable.asFileParameter1XIndicatorKnownAccuracy(folderId, resultSummaries);
            Printable.asFileParameter1XIndicatorNoveltyAccuracy(folderId, resultSummaries);

        } else if (this.variateParameter2 != null) {

            final List<InterceptionResultSummary> resultSummaries = new ArrayList<>();
            double value = this.initialValue;

            for (int i = 0; i < this.times; ++i) {
                resultSummaries.add(execute(this.parameter1, value));
                System.out.println("Execution " + (i + 1) + "/" + this.times + " finished");
                value += this.increment;
            }

            Printable.asFile(folderId,"frameworkOutput.txt", resultSummaries.get(0).getFrameworkOutput());
            Printable.asFileParameter2XRecall(folderId, resultSummaries);
            Printable.asFileParameter2XPrecision(folderId, resultSummaries);
            Printable.asFileParameter2XF1(folderId, resultSummaries);
            Printable.asFileRecallXPrecision(folderId, resultSummaries);
            Printable.asFileParameter2XIndicatorKnownAccuracy(folderId, resultSummaries);
            Printable.asFileParameter2XIndicatorNoveltyAccuracy(folderId, resultSummaries);

        } else {

            final InterceptionResultSummary resultSummary = execute(this.parameter1, this.parameter2);
            System.out.println("Execution 1/1 finished");
            Printable.asFile(folderId,"indicatorOutput.txt", resultSummary.toString());
            Printable.asFile(folderId,"frameworkOutput.txt", resultSummary.getFrameworkOutput());

        }

        System.out.println("Output folder ID = " + folderId);

    }

    private void additionalArgumentsValidation() throws Exception {

        if (parameter1 == null && (variateParameter1 == null)) {
            throw new Exception("Option \"parameter1\" is required");
        }

        if (this.strategyOption == StrategyEnum.SHARED_NEIGHBOURS
                && parameter2 == null && (variateParameter2 == null)) {
            throw new Exception("Strategy 1 was selected, so option \"parameter2\" is required");
        }

        if (this.strategyOption == StrategyEnum.SHARED_NEIGHBOURS_JS
                && parameter2 == null && (variateParameter2 == null)) {
            throw new Exception("Strategy 3 was selected, so option \"parameter2\" is required");
        }

        if (this.variateParameter1 != null && this.variateParameter1.length != 3) {
            throw new Exception("Malformed variateParameter1 vector");
        }

        if (this.variateParameter2 != null && this.variateParameter2.length != 3) {
            throw new Exception("Malformed variateParameter2 vector");
        }

        if (this.variateParameter1 != null) {
            this.initialValue = Double.parseDouble(this.variateParameter1[0]);
            this.increment = Double.parseDouble(this.variateParameter1[1]);
            this.times = Integer.parseInt(this.variateParameter1[2]);
        }

        if (this.variateParameter2 != null) {
            this.initialValue = Double.parseDouble(this.variateParameter2[0]);
            this.increment = Double.parseDouble(this.variateParameter2[1]);
            this.times = Integer.parseInt(this.variateParameter2[2]);
        }

    }

    private InterceptionResultSummary execute(final Double parameter1, final Double parameter2) throws Exception {

        Strategy strategy = null;

        switch (strategyOption) {
            case EUCLIDEAN_DISTANCE:
                strategy = new StrategyEuclideanDistance(parameter1);
                break;
            case EUCLIDEAN_DISTANCE_JS:
                strategy = new StrategyEuclideanDistanceJS(parameter1);
                break;
            case SHARED_NEIGHBOURS_JS:
                strategy = new StrategySharedNeighboursJS(parameter1, parameter2);
                break;
            case SHARED_NEIGHBOURS:
                strategy = new StrategySharedNeighbours(parameter1, parameter2);
                break;
            case WEIGHTED_EUCLIDEAN_DISTANCE:
                strategy = new StrategyWeightedEuclideanDistance(parameter1);
                break;
            default:
                throw new IllegalArgumentException();
        }


        switch (lowLevelStrategyOption) {
            case K_MEDOIDS:
                strategy.setLowLevelStrategy(new KMedoids(lowLevelStrategyParameter1.intValue()));
                break;
            case MOST_OR_LESS_INFORMATIVE:
                strategy.setLowLevelStrategy(new KMostOrLessInformative(lowLevelStrategyParameter1.intValue()));
                break;
            default:
                throw new IllegalArgumentException();
        }


        final InterceptionResultSummary resultSummary;

        switch (frameworkOption) {
            case MINAS:
                resultSummary = MINASExperiment.execute(strategy, MINASArguments.map.get(this.datasetOption));
                break;
            case ECHO:
                resultSummary = ECHOExperiment.execute(strategy, ECHOArguments.map.get(this.datasetOption));
                break;
            case ANYNOVEL:
                resultSummary = AnyNovelExperiment.execute(strategy, AnyNovelArguments.map.get(this.datasetOption));
                break;
            case HIGIA:
                resultSummary = HigiaExperiment.execute(strategy, HigiaArguments.map.get(this.datasetOption));
                break;
            default:
                throw new IllegalArgumentException();
        }

        return resultSummary;

    }

}
