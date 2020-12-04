package br.com.douglas444.bootable.framework.arguments;

import br.com.douglas444.bootable.enumeration.DatasetEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.DatasetEnum.*;

public class ECHOArguments {

    final public static HashMap<DatasetEnum, String[]> map = new HashMap<>();

    static {

        map.put(MOA3, new String[]{
                "-M", "100",
                "-S", "2000",
                "-L", "5",
                "-T", "4000",
                "-C", "2000",
                "-F", "./resources/dataset/fullArff/MOA3"});

        map.put(KDDTE5CLASSES, new String[]{
                "-M", "100",
                "-S", "9758",
                "-L", "5",
                "-T", "19516",
                "-C", "9758",
                "-F", "./resources/dataset/fullArff/KDDTe5Classes"});

        map.put(SYND, new String[]{
                "-M", "100",
                "-S", "5000",
                "-L", "5",
                "-T", "10000",
                "-C", "5000",
                "-F", "./resources/dataset/fullArff/SynD"});

        map.put(SYNEDC20D40NORM, new String[]{
                "-M", "100",
                "-S", "8000",
                "-L", "5",
                "-T", "16000",
                "-C", "8000",
                "-F", "./resources/dataset/fullArff/SynEDC20D40Norm"});

        map.put(FCTE, new String[]{
                "-M", "100",
                "-S", "6000",
                "-L", "5",
                "-T", "12000",
                "-C", "6000",
                "-F", "./resources/dataset/fullArff/fcTe"});

        map.put(COVTYPEORIGNORM, new String[]{
                "-M", "100",
                "-S", "9419",
                "-L", "5",
                "-T", "18838",
                "-C", "9419",
                "-F", "./resources/dataset/fullArff/covtypeOrigNorm"});

    }
}


