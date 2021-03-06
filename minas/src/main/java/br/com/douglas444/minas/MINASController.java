package br.com.douglas444.minas;

import br.com.douglas444.datastreamutils.DSClassifierController;
import br.com.douglas444.mltk.datastructure.DynamicConfusionMatrix;
import br.com.douglas444.mltk.datastructure.Sample;

import java.util.Optional;

public class MINASController implements DSClassifierController {

    private final MINAS minas;

    public MINASController(MINAS minas) {

        this.minas = minas;

    }

    @Override
    public Optional<Integer> process(Sample sample) {
        final Classification classification = minas.process(sample);
        return classification.getLabel();
    }

    @Override
    public String getLog() {

        return String.format("Timestamp = %d, CER = %f, UnkR = %f, Novelty count = %d",
                this.minas.getTimestamp(),
                this.minas.calculateCER(),
                this.minas.calculateUnkR(),
                this.minas.getNoveltyCount());

    }

    public int getNoveltyCount() {
        return this.minas.getNoveltyCount();
    }

    public long getTimestamp() {
        return this.minas.getTimestamp();
    }

    public DynamicConfusionMatrix getDynamicConfusionMatrix() {
        return this.minas.getConfusionMatrix();
    }

}
