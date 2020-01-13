// 
// Decompiled by Procyon v0.5.36
// 

package dataStructure;

import utils.Point3D;

public interface node_data
{
    int getKey();
    
    Point3D getLocation();
    
    void setLocation(final Point3D p0);
    
    double getWeight();
    
    void setWeight(final double p0);
    
    String getInfo();
    
    void setInfo(final String p0);
    
    int getTag();
    
    void setTag(final int p0);
}
