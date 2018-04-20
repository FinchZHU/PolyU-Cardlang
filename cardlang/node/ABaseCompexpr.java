/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class ABaseCompexpr extends PCompexpr
{
    private PNumexpr _numexpr_;

    public ABaseCompexpr()
    {
        // Constructor
    }

    public ABaseCompexpr(
        @SuppressWarnings("hiding") PNumexpr _numexpr_)
    {
        // Constructor
        setNumexpr(_numexpr_);

    }

    @Override
    public Object clone()
    {
        return new ABaseCompexpr(
            cloneNode(this._numexpr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABaseCompexpr(this);
    }

    public PNumexpr getNumexpr()
    {
        return this._numexpr_;
    }

    public void setNumexpr(PNumexpr node)
    {
        if(this._numexpr_ != null)
        {
            this._numexpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._numexpr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._numexpr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._numexpr_ == child)
        {
            this._numexpr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._numexpr_ == oldChild)
        {
            setNumexpr((PNumexpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
