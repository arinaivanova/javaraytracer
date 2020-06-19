// javaraytracer Sphere.java
// Date: 19 06 2020
// Author: arinaivanova
// Commentary: Sphere shape
import java.lang.Math;

class Sphere implements IShape{

	Vector center;
	double radius;

	public Sphere(Vector center, float radius){
		this.center = center;
		this.radius = radius;
	}

	// returns distance to first intersection point if ray has at least one intersection with sphere
	public double rayIntersect(final Vector origin, final Vector dir){ // (6)
		Vector disp = origin.sub(center);
		// projection of ray onto sphere's displacement
		double rayDispProj = dir.dot(disp);
		boolean ok = true;
		// check that ray points towards sphere
		ok &= rayDispProj < 0;
		double d = rayDispProj*rayDispProj - disp.dot(disp) + radius*radius;
		ok &= d >= 0;
		if (ok){
			d = Math.sqrt(d);
			double poi1 = -rayDispProj + d;
			double poi2 = -rayDispProj - d;
			if (poi1 < poi2 && poi1 > 0)
				return poi1;
			if (poi2 > 0)
				return poi2;
		}
		// no intersection: distance is infinite
		return Double.MAX_VALUE;
	}
}
