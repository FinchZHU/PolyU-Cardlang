/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class AParenBoolobj extends PBoolobj
{
    private TLeftparen _leftparen_;
    private PBoolexpr _boolexpr_;
    private TRightparen _rightparen_;

    public AParenBoolobj()
    {
        // Constructor
    }

    public AParenBoolobj(
        @SuppressWarnings("hiding") TLeftparen _leftparen_,
        @SuppressWarnings("hiding") PBoolexpr _boolexpr_,
        @SuppressWarnings("hiding") TRightparen _rightparen_)
    {
        // Constructor
        setLeftparen(_leftparen_);

        setBoolexpr(_boolexpr_);

        setRightparen(_rightparen_);

    }

    @Override
    public Object clone()
    {
        return new AParenBoolobj(
            cloneNode(this._leftparen_),
            cloneNode(this._boolexpr_),
            cloneNode(this._rightparen_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAParenBoolobj(this);
    }

    public TLeftparen getLeftparen()
    {
        return this._leftparen_;
    }

    public void setLeftparen(TLeftparen node)
    {
        if(this._leftparen_ != null)
        {
            this._leftparen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._leftparen_ = node;
    }

    public PBoolexpr getBoolexpr()
    {
        return this._boolexpr_;
    }

    public void setBoolexpr(PBoolexpr node)
    {
        if(this._boolexpr_ != null)
        {
            this._boolexpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._boolexpr_ = node;
    }

    public TRightparen getRightparen()
    {
        return this._rightparen_;
    }

    public void setRightparen(TRightparen node)
    {
        if(this._rightparen_ != null)
        {
            this._rightparen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rightparen_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._leftparen_)
            + toString(this._boolexpr_)
            + toString(this._rightparen_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._leftparen_ == child)
        {
            this._leftparen_ = null;
            return;
        }

        if(this._boolexpr_ == child)
        {
            this._boolexpr_ = null;
            return;
        }

        if(this._rightparen_ == child)
        {
            this._rightparen_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._leftparen_ == oldChild)
        {
            setLeftparen((TLeftparen) newChild);
            return;
        }

        if(this._boolexpr_ == oldChild)
        {
            setBoolexpr((PBoolexpr) newChild);
            return;
        }

        if(this._rightparen_ == oldChild)
        {
            setRightparen((TRightparen) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
