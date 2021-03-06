/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TOptminus extends Token
{
    public TOptminus()
    {
        super.setText("-");
    }

    public TOptminus(int line, int pos)
    {
        super.setText("-");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TOptminus(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTOptminus(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TOptminus text.");
    }
}
