// javaraytracer Vector.java
// Date: 19 06 2020
// Author: arinaivanova
// Commentary: 3D vector class
import java.lang.Math;

class Vector{

	double x, y, z;

	public Vector(){
		x = y = z = 0.0;
	}

	public Vector(double X, double Y, double Z){
		x = X;
		y = Y;
		z = Z;
	}

	public Vector(final Vector v){
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Vector add(final Vector v){
		return new Vector(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector sub(final Vector v){
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

	public Vector mul(final Vector v){
		return new Vector(x * v.x, y * v.y, z * v.z);
	}

	public Vector add(double d){
		return new Vector(x + d, y + d, z + d);
	}
	
	public Vector mul(double d){
		return new Vector(x * d, y * d, z * d);
	}
	
	public Vector div(double d){
		assert d != 0;
		return new Vector(x / d, y / d, z / d);
	}

	public double dot(final Vector v){
		return x * v.x + y * v.y + z * v.z;
	}

	public Vector cross(final Vector v){
		return new Vector(y * v.z - v.y * z,
		                  x * v.z - v.x * z,
		                  x * v.y - v.x * y);
	}

	public double mag(){
		assert x >= 0 && y >= 0 && z >= 0;
		return Math.sqrt(this.dot(this));
	}

	public Vector normalize(){
		return this.div(this.mag());
	}
}
