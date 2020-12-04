package br.com.douglas444.bootable.enumeration.id;

import br.com.douglas444.bootable.enumeration.FrameworkEnum;

import java.util.HashMap;

import static br.com.douglas444.bootable.enumeration.FrameworkEnum.*;

public class FrameworkId {

    final public static HashMap<Integer, FrameworkEnum> map = new HashMap<>();

    static {

        map.put(0, MINAS);
        map.put(1, ECHO);
        map.put(2, ANYNOVEL);
        map.put(3, HIGIA);

    }
}
