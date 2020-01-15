package gameClient;

import utils.Point3D;

public class Robot {
	int src; // the source of the robot.
	int dest; // the dest of the robot.
	int id; // the id of the robot.
	Point3D pos; //3D pos of robot.

	public Robot(int id, int src, int dest,Point3D pos) {
		this.id=id;
		this.src=src;
		this.dest=dest;
		this.pos=pos;
	}
	
	public Robot() {
		
	}

}