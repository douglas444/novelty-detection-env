package br.com.douglas444.bootable.framework.arguments;

import br.com.douglas444.bootable.enumeration.DatasetEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.DatasetEnum.*;

public class MINASArguments {

    final public static HashMap<DatasetEnum, String[]> map = new java.util.HashMap<>();

    static {

        map.put(MOA3, new String[]{
                "./resources/config/minas_MOA3.xml",
                "./resources/dataset/csv/MOA3.csv"});

        map.put(KDDTE5CLASSES, new String[]{
                "./resources/config/minas_KDDTe5Classes.xml",
                "./resources/dataset/csv/KDDTe5Classes.csv"});

        map.put(SYND, new String[]{
                "./resources/config/minas_SynD.xml",
                "./resources/dataset/csv/SynD.csv"});

        map.put(SYNEDC20D40NORM, new String[]{
                "./resources/config/minas_SynEDC20D40Norm.xml",
                "./resources/dataset/csv/SynEDC20D40Norm.csv"});

        map.put(FCTE, new String[]{
                "./resources/config/minas_fcTe.xml",
                "./resources/dataset/csv/fcTe.csv"});

        map.put(COVTYPEORIGNORM, new String[]{
                "./resources/config/minas_covtypeOrigNorm.xml",
                "./resources/dataset/csv/covtypeOrigNorm.csv"});
    }


}
