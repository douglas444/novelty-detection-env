package br.com.douglas444.bootable.enumeration.id;

import br.com.douglas444.bootable.enumeration.StrategyEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.StrategyEnum.*;

public class StrategyId {

    final public static HashMap<Integer, StrategyEnum> map = new HashMap<>();

    static {

        map.put(0, EUCLIDEAN_DISTANCE);
        map.put(1, SHARED_NEIGHBOURS);
        map.put(2, EUCLIDEAN_DISTANCE_JS);
        map.put(3, SHARED_NEIGHBOURS_JS);

    }
}
