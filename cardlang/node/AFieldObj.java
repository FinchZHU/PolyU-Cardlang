/* This file was generated by SableCC (http://www.sablecc.org/). */

package cardlang.node;

import cardlang.analysis.*;

@SuppressWarnings("nls")
public final class AFieldObj extends PObj
{
    private PId _id_;
    private TPoint _point_;
    private PObj _obj_;

    public AFieldObj()
    {
        // Constructor
    }

    public AFieldObj(
        @SuppressWarnings("hiding") PId _id_,
        @SuppressWarnings("hiding") TPoint _point_,
        @SuppressWarnings("hiding") PObj _obj_)
    {
        // Constructor
        setId(_id_);

        setPoint(_point_);

        setObj(_obj_);

    }

    @Override
    public Object clone()
    {
        return new AFieldObj(
            cloneNode(this._id_),
            cloneNode(this._point_),
            cloneNode(this._obj_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFieldObj(this);
    }

    public PId getId()
    {
        return this._id_;
    }

    public void setId(PId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public TPoint getPoint()
    {
        return this._point_;
    }

    public void setPoint(TPoint node)
    {
        if(this._point_ != null)
        {
            this._point_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._point_ = node;
    }

    public PObj getObj()
    {
        return this._obj_;
    }

    public void setObj(PObj node)
    {
        if(this._obj_ != null)
        {
            this._obj_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._obj_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._point_)
            + toString(this._obj_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._point_ == child)
        {
            this._point_ = null;
            return;
        }

        if(this._obj_ == child)
        {
            this._obj_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._id_ == oldChild)
        {
            setId((PId) newChild);
            return;
        }

        if(this._point_ == oldChild)
        {
            setPoint((TPoint) newChild);
            return;
        }

        if(this._obj_ == oldChild)
        {
            setObj((PObj) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
