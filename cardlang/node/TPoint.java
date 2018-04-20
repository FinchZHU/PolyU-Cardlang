/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TPoint extends Token
{
    public TPoint()
    {
        super.setText(".");
    }

    public TPoint(int line, int pos)
    {
        super.setText(".");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPoint(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPoint(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TPoint text.");
    }
}
