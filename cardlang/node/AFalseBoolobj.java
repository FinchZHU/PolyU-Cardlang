/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class AFalseBoolobj extends PBoolobj
{
    private TKwfalse _kwfalse_;

    public AFalseBoolobj()
    {
        // Constructor
    }

    public AFalseBoolobj(
        @SuppressWarnings("hiding") TKwfalse _kwfalse_)
    {
        // Constructor
        setKwfalse(_kwfalse_);

    }

    @Override
    public Object clone()
    {
        return new AFalseBoolobj(
            cloneNode(this._kwfalse_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFalseBoolobj(this);
    }

    public TKwfalse getKwfalse()
    {
        return this._kwfalse_;
    }

    public void setKwfalse(TKwfalse node)
    {
        if(this._kwfalse_ != null)
        {
            this._kwfalse_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._kwfalse_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._kwfalse_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._kwfalse_ == child)
        {
            this._kwfalse_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._kwfalse_ == oldChild)
        {
            setKwfalse((TKwfalse) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
