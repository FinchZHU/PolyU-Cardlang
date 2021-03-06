/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TLine extends Token
{
    public TLine()
    {
        super.setText("|");
    }

    public TLine(int line, int pos)
    {
        super.setText("|");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TLine(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLine(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TLine text.");
    }
}
