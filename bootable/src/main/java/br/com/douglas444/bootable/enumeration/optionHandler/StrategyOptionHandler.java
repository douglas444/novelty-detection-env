package br.com.douglas444.bootable.enumeration.optionHandler;

import br.com.douglas444.bootable.enumeration.StrategyEnum;
import br.com.douglas444.bootable.enumeration.id.StrategyId;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Messages;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrategyOptionHandler extends OneArgumentOptionHandler<StrategyEnum> {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);

    public StrategyOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super StrategyEnum> setter) {
        super(parser, option, setter);
    }

    @Override
    protected StrategyEnum parse(String s) throws NumberFormatException, CmdLineException {

        Matcher matcher = INTEGER_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, option.toString(), s);
        }

        final int value = Integer.parseInt(s);

        if (!StrategyId.map.containsKey(value)) {
            throw new CmdLineException(owner, Messages.MAP_HAS_NO_KEY, option.toString(), s);
        }

        return StrategyId.map.get(value);
    }
}