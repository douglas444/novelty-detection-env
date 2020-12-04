package br.com.douglas444.bootable.out;

import br.com.douglas444.patternsampling.types.InterceptionResultSummary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printable {
    
    public final static String BOOTABLE_OUTPUT_FOLDER = "output";

    private static void checkDir(final String folder) {
        File dir = new File(folder);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }

    public static void asFile(final String folderId, final String fileName, final String[] strings) throws IOException {

        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        FileWriter writer = new FileWriter(folder + "/" + fileName);

        for (String string : strings) {
            writer.write(string + " ");
        }

        writer.close();
    }

    public static void asFile(final String folderId, final String fileName, final String string) throws IOException {

        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        writer.write(string);
        writer.close();
    }

    public static void asFileParameter1XRecall(final String folderId,
                                               final List<InterceptionResultSummary> resultSummaries)
            throws IOException {

        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter1_x_recall.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);

        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter1() + " " + summary.calculateRecall() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter2XRecall(final String folderId,
                                               final List<InterceptionResultSummary> resultSummaries)
            throws IOException {

        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter2_x_recall.csv";
        FileWriter writer = new FileWriter(folder + "/" + fileName);

        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter2() + " " + summary.calculateRecall() + "\n");
        }

        writer.close();


    }


    public static void asFileParameter1XPrecision(final String folderId,
                                                  final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter1_x_precision.csv";
        FileWriter writer = new FileWriter(folder + "/" + fileName);

        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter1() + " " + summary.calculatePrecision() + "\n");
        }

        writer.close();


    }

    public static void asFileParameter2XPrecision(final String folderId,
                                                  final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter2_x_precision.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter2() + " " + summary.calculatePrecision() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter1XF1(final String folderId,
                                           final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter1_x_f1.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter1() + " " + summary.calculateF1() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter2XF1(final String folderId,
                                           final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter2_x_f1.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter2() + " " + summary.calculateF1() + "\n");
        }

        writer.close();

    }

    public static void asFileRecallXPrecision(final String folderId,
                                              final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "recall_x_precision.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.calculateRecall()  + " " + summary.calculatePrecision() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter1XIndicatorKnownAccuracy(final String folderId,
                                                               final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter1_x_knownAccuracy.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter1()  + " " +
                    summary.calculateAccuracyForKnownPrediction_indicator() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter1XIndicatorNoveltyAccuracy(final String folderId,
                                                                 final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter1_x_noveltyAccuracy.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter1()  + " " +
                    summary.calculateAccuracyForNoveltyPrediction_indicator() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter2XIndicatorKnownAccuracy(final String folderId,
                                                               final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter2_x_knownAccuracy.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter2()  + " " +
                    summary.calculateAccuracyForKnownPrediction_indicator() + "\n");
        }

        writer.close();

    }

    public static void asFileParameter2XIndicatorNoveltyAccuracy(final String folderId,
                                                                 final List<InterceptionResultSummary> resultSummaries)
            throws IOException {


        final String folder = BOOTABLE_OUTPUT_FOLDER + folderId;
        checkDir(folder);

        final String fileName = "parameter2_x_noveltyAccuracy.csv";

        FileWriter writer = new FileWriter(folder + "/" + fileName);
        for (InterceptionResultSummary summary : resultSummaries) {
            writer.write(summary.getParameter2()  + " " +
                    summary.calculateAccuracyForNoveltyPrediction_indicator() + "\n");
        }

        writer.close();

    }
}
