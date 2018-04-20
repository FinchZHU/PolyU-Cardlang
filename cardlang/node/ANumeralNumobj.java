/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class ANumeralNumobj extends PNumobj
{
    private TNumeral _numeral_;

    public ANumeralNumobj()
    {
        // Constructor
    }

    public ANumeralNumobj(
        @SuppressWarnings("hiding") TNumeral _numeral_)
    {
        // Constructor
        setNumeral(_numeral_);

    }

    @Override
    public Object clone()
    {
        return new ANumeralNumobj(
            cloneNode(this._numeral_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANumeralNumobj(this);
    }

    public TNumeral getNumeral()
    {
        return this._numeral_;
    }

    public void setNumeral(TNumeral node)
    {
        if(this._numeral_ != null)
        {
            this._numeral_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._numeral_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._numeral_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._numeral_ == child)
        {
            this._numeral_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._numeral_ == oldChild)
        {
            setNumeral((TNumeral) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
