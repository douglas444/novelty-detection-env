package br.com.douglas444.bootable.framework.arguments;

import br.com.douglas444.bootable.enumeration.DatasetEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.DatasetEnum.*;

public class HigiaArguments {

    final public static HashMap<DatasetEnum, String[]> map = new java.util.HashMap<>();

    static {

        map.put(MOA3, new String[]{"./resources/dataset/fullArff/MOA3.arff", "10000"});

        map.put(KDDTE5CLASSES, new String[]{"./resources/dataset/fullArff/KDDTe5Classes.arff", "48791"});

        map.put(SYND, new String[]{"./resources/dataset/fullArff/SynD.arff", "25000"});

        map.put(SYNEDC20D40NORM, new String[]{"./resources/dataset/fullArff/SynEDC20D40Norm.arff", "40000"});

        map.put(FCTE, new String[]{"./resources/dataset/fullArff/fcTe.arff", "30000"});

        map.put(COVTYPEORIGNORM, new String[]{"./resources/dataset/fullArff/covtypeOrigNorm.arff", "47045"});

    }

}
