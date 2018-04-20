/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TKwshuffle extends Token
{
    public TKwshuffle()
    {
        super.setText("Shuffle");
    }

    public TKwshuffle(int line, int pos)
    {
        super.setText("Shuffle");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwshuffle(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwshuffle(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwshuffle text.");
    }
}