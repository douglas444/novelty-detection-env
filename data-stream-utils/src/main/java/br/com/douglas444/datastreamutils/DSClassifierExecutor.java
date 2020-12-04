package br.com.douglas444.datastreamutils;

import br.com.douglas444.mltk.datastructure.Sample;

import java.io.IOException;

public class DSClassifierExecutor {

    public static void start(final DSClassifierController dsClassifierController,
                             final boolean enableClassifierLogging,
                             final int classifierLoggingTimestampInterval,
                             final DSFileReader... dsFileReader)
            throws IOException {

        Sample sample;
        int timestamp = 0;

        for (DSFileReader f : dsFileReader) {

            while ((sample = f.next()) != null) {
                ++timestamp;

                dsClassifierController.process(sample);

                if (enableClassifierLogging && classifierLoggingTimestampInterval > 0 && timestamp %
                        classifierLoggingTimestampInterval == 0) {

                    System.out.println(dsClassifierController.getLog());

                }
            }
        }
    }

}
