package br.com.douglas444.bootable.framework.arguments;

import br.com.douglas444.bootable.enumeration.DatasetEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.DatasetEnum.*;

public class AnyNovelArguments {

    final public static HashMap<DatasetEnum, String[]> map = new HashMap<>();

    static {

        map.put(MOA3, new String[]{
                "-BLM","train","./dataset/splitArff/train/MOA3_train.arff",
                "-test","./dataset/splitArff/test/MOA3_test.arff",
                "./config/anynovel_MOA3_parameters.txt"});
        map.put(KDDTE5CLASSES, new String[]{
                "-BLM","train","./dataset/splitArff/train/KDDTe5Classes_train.arff",
                "-test","./dataset/splitArff/test/KDDTe5Classes_test.arff",
                "./config/anynovel_KDDTe5Classes_parameters.txt"});
        map.put(SYND, new String[]{
                "-BLM","train","./dataset/splitArff/train/SynD_train.arff",
                "-test","./dataset/splitArff/test/SynD_test.arff",
                "./config/anynovel_SynD_parameters.txt"});
        map.put(SYNEDC20D40NORM, new String[]{
                "-BLM","train","./dataset/splitArff/train/SynEDC20D40Norm_train.arff",
                "-test","./dataset/splitArff/test/SynEDC20D40Norm_test.arff",
                "./config/anynovel_SynEDC20D40Norm_parameters.txt"});
        map.put(FCTE, new String[]{
                "-BLM","train","./dataset/splitArff/train/fcTe_train.arff",
                "-test","./dataset/splitArff/test/fcTe_test.arff",
                "./config/anynovel_fcTe_parameters.txt"});
        map.put(COVTYPEORIGNORM, new String[]{
                "-BLM","train","./dataset/splitArff/train/covtypeOrigNorm_train.arff",
                "-test","./dataset/splitArff/test/covtypeOrigNorm_test.arff",
                "./config/anynovel_covtypeOrigNorm_parameters.txt"});
    }
}
