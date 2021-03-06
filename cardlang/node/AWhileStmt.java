/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class AWhileStmt extends PStmt
{
    private PWhile _while_;

    public AWhileStmt()
    {
        // Constructor
    }

    public AWhileStmt(
        @SuppressWarnings("hiding") PWhile _while_)
    {
        // Constructor
        setWhile(_while_);

    }

    @Override
    public Object clone()
    {
        return new AWhileStmt(
            cloneNode(this._while_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAWhileStmt(this);
    }

    public PWhile getWhile()
    {
        return this._while_;
    }

    public void setWhile(PWhile node)
    {
        if(this._while_ != null)
        {
            this._while_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._while_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._while_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._while_ == child)
        {
            this._while_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._while_ == oldChild)
        {
            setWhile((PWhile) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
