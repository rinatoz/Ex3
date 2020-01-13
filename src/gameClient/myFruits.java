package gameClient;


import utils.Point3D;

public class myFruits {
	double value;
	Point3D pos;
	boolean whichfruit;
	
	
	public myFruits(double value, int type, Point3D pos) {
		this.value=value;
		this.pos=pos;
		if (type==-1)
		{
		this.whichfruit=true; //banana
		}
		else
		{
			this.whichfruit=false; //apple
		}
	     
	}
	public myFruits() {
		
	}

}