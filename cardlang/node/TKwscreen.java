/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class TKwscreen extends Token
{
    public TKwscreen()
    {
        super.setText("Screen");
    }

    public TKwscreen(int line, int pos)
    {
        super.setText("Screen");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwscreen(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwscreen(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwscreen text.");
    }
}
