/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class ATrueVal extends PVal
{
    private TKwtrue _kwtrue_;

    public ATrueVal()
    {
        // Constructor
    }

    public ATrueVal(
        @SuppressWarnings("hiding") TKwtrue _kwtrue_)
    {
        // Constructor
        setKwtrue(_kwtrue_);

    }

    @Override
    public Object clone()
    {
        return new ATrueVal(
            cloneNode(this._kwtrue_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATrueVal(this);
    }

    public TKwtrue getKwtrue()
    {
        return this._kwtrue_;
    }

    public void setKwtrue(TKwtrue node)
    {
        if(this._kwtrue_ != null)
        {
            this._kwtrue_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._kwtrue_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._kwtrue_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._kwtrue_ == child)
        {
            this._kwtrue_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._kwtrue_ == oldChild)
        {
            setKwtrue((TKwtrue) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
