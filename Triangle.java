// javaraytracer Triangle.java
// Date: 19 06 2020
// Author: arinaivanova
// Commentary: Triangle shape
class Triangle implements IShape{

	Vector vert0, vert1, vert2;
	Vector normal;

	public Triangle(final Vector vert0, final Vector vert1, final Vector vert2){
		this.vert0 = new Vector(vert0);
		this.vert1 = new Vector(vert1);
		this.vert2 = new Vector(vert2);
		// normal to plane for counter-clockwise triangle
		normal = vert2.sub(vert0).cross(vert1.sub(vert0)).normalize();
	}

	// returns distance to intersection point if ray intersects triangle
	public double rayIntersect(final Vector origin, final Vector dir){
		double dist = -(origin.sub(vert0).dot(normal)) / dir.dot(normal);
		// check that ray is not parallel to plane
		if (dist != 0){
			Vector intersect = origin.add(dir.mul(dist));
			// 2x2 determinant of entire triangle's sides
			double area = det2D(vert2, vert0, vert1);
			assert area != 0;
			// 2x2 determinants of sub-triangles (1 vertex replaced with POI) such that area >= 0 if POI is inside triangle
			double area0 = det2D(vert1, vert2, intersect);
			double area1 = det2D(vert0, vert1, intersect);
			double area2 = area - area0 - area1;
			if (area0 >= 0 && area1 >= 0 && area2 >= 0)
				return dist;
		}
		// no intersection: distance is infinite
		return Double.MAX_VALUE;
	}

	// returns 2x2 determinant of counter-clockwise triangle sides with vertices <p1,p0,p2>
	private double det2D(final Vector p0, final Vector p1, final Vector p2){
		Vector a = p0.sub(p1);
		Vector b = p2.sub(p1);
		return a.x * b.y - b.x * a.y;
	}
}
