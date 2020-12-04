package br.com.douglas444.bootable.enumeration.id;

import br.com.douglas444.bootable.enumeration.DatasetEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.DatasetEnum.*;

public class DatasetId {

    final public static HashMap<Integer, DatasetEnum> map = new HashMap<>();

    static {

        map.put(0, MOA3);
        map.put(1, KDDTE5CLASSES);
        map.put(2, SYND);
        map.put(3, SYNEDC20D40NORM);
        map.put(4, FCTE);
        map.put(5, COVTYPEORIGNORM);

    }
}
