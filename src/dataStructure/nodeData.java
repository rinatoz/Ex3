// 
// Decompiled by Procyon v0.5.36
// 

package dataStructure;

import utils.Point3D;
import java.io.Serializable;
import dataStructure.node_data;

public class nodeData implements node_data, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point3D _pos;
    private double _weight;
    private String _msg;
    private int _tag;
    private int _id;
    private static int _count;
    
    static {
        nodeData._count = 0;
    }
    
    public static void resetCount() {
        nodeData._count = 0;
    }
    
    public nodeData() {
        this.set_id(nodeData._count);
        ++nodeData._count;
    }
    
	public nodeData(int key) 
	{
		this._id=key;
		this._pos=null;
		this._weight=0;
		this._msg=null;
		this._tag=0;
	}
    
    public nodeData(final int id, final Point3D p) {
        this.set_id(id);
        this.setLocation(p);
    }
    
    public nodeData(final int t, final double w, final Point3D p, final String msg) {
        this();
        this.setTag(t);
        this.setWeight(w);
        this.setLocation(p);
        this.setInfo(msg);
    }
	public nodeData(Point3D p,double w,String i,int t) 
	{
		this();
		this._pos=p;
		this._weight=w;
		this._msg=i;
		this._tag=t;
	}
    @Override
    public Point3D getLocation() {
        return this._pos;
    }
    
    @Override
    public void setLocation(final Point3D p) {
        this._pos = new Point3D(p);
    }
    
    @Override
    public double getWeight() {
        return this._weight;
    }
    
    @Override
    public void setWeight(final double w) {
        this._weight = w;
    }
    
    @Override
    public String getInfo() {
        return this._msg;
    }
    
    @Override
    public void setInfo(final String s) {
        this._msg = s;
    }
    
    public void appendInfo(final String s) {
        this._msg = String.valueOf(this._msg) + s;
    }
    
    @Override
    public int getTag() {
        return this._tag;
    }
    
    @Override
    public void setTag(final int t) {
        this._tag = t;
    }
    
    @Override
    public String toString() {
        return this.getKey() + "," + this.getTag() + "," + this.getWeight() + ":" + this.getInfo();
    }
    
    public String toJSON() {
        String ans = "";
        ans = String.valueOf(ans) + "{id:" + this.getKey() + ",info:" + this.getInfo() + "}";
        return ans;
    }
    
    @Override
    public int getKey() {
        return this._id;
    }
    
    private void set_id(final int _id) {
        this._id = _id;
    }
}
