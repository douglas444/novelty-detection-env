package br.com.douglas444.datastreamutils;

import br.com.douglas444.mltk.datastructure.Sample;

import java.util.Optional;

public interface DSClassifierController {

    Optional<Integer> process(final Sample sample);

    String getLog();

}
