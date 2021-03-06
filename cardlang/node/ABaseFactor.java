/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class ABaseFactor extends PFactor
{
    private PCompexpr _compexpr_;

    public ABaseFactor()
    {
        // Constructor
    }

    public ABaseFactor(
        @SuppressWarnings("hiding") PCompexpr _compexpr_)
    {
        // Constructor
        setCompexpr(_compexpr_);

    }

    @Override
    public Object clone()
    {
        return new ABaseFactor(
            cloneNode(this._compexpr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABaseFactor(this);
    }

    public PCompexpr getCompexpr()
    {
        return this._compexpr_;
    }

    public void setCompexpr(PCompexpr node)
    {
        if(this._compexpr_ != null)
        {
            this._compexpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._compexpr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._compexpr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._compexpr_ == child)
        {
            this._compexpr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._compexpr_ == oldChild)
        {
            setCompexpr((PCompexpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
