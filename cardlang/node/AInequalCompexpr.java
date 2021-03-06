/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class AInequalCompexpr extends PCompexpr
{
    private PNumexpr _lexpr_;
    private TOptinequal _optinequal_;
    private PNumexpr _rexpr_;

    public AInequalCompexpr()
    {
        // Constructor
    }

    public AInequalCompexpr(
        @SuppressWarnings("hiding") PNumexpr _lexpr_,
        @SuppressWarnings("hiding") TOptinequal _optinequal_,
        @SuppressWarnings("hiding") PNumexpr _rexpr_)
    {
        // Constructor
        setLexpr(_lexpr_);

        setOptinequal(_optinequal_);

        setRexpr(_rexpr_);

    }

    @Override
    public Object clone()
    {
        return new AInequalCompexpr(
            cloneNode(this._lexpr_),
            cloneNode(this._optinequal_),
            cloneNode(this._rexpr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAInequalCompexpr(this);
    }

    public PNumexpr getLexpr()
    {
        return this._lexpr_;
    }

    public void setLexpr(PNumexpr node)
    {
        if(this._lexpr_ != null)
        {
            this._lexpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lexpr_ = node;
    }

    public TOptinequal getOptinequal()
    {
        return this._optinequal_;
    }

    public void setOptinequal(TOptinequal node)
    {
        if(this._optinequal_ != null)
        {
            this._optinequal_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._optinequal_ = node;
    }

    public PNumexpr getRexpr()
    {
        return this._rexpr_;
    }

    public void setRexpr(PNumexpr node)
    {
        if(this._rexpr_ != null)
        {
            this._rexpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rexpr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lexpr_)
            + toString(this._optinequal_)
            + toString(this._rexpr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lexpr_ == child)
        {
            this._lexpr_ = null;
            return;
        }

        if(this._optinequal_ == child)
        {
            this._optinequal_ = null;
            return;
        }

        if(this._rexpr_ == child)
        {
            this._rexpr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lexpr_ == oldChild)
        {
            setLexpr((PNumexpr) newChild);
            return;
        }

        if(this._optinequal_ == oldChild)
        {
            setOptinequal((TOptinequal) newChild);
            return;
        }

        if(this._rexpr_ == oldChild)
        {
            setRexpr((PNumexpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
