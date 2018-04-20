/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TKwelse extends Token
{
    public TKwelse()
    {
        super.setText("Else");
    }

    public TKwelse(int line, int pos)
    {
        super.setText("Else");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwelse(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwelse(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwelse text.");
    }
}