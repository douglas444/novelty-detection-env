package br.com.douglas444.bootable.enumeration.id;

import br.com.douglas444.bootable.enumeration.LowLevelStrategyEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.LowLevelStrategyEnum.*;

public class LowLevelStrategyId {

    final public static HashMap<Integer, LowLevelStrategyEnum> map = new HashMap<>();

    static {

        map.put(0, K_MEDOIDS);
        map.put(1, MOST_OR_LESS_INFORMATIVE);

    }
}
