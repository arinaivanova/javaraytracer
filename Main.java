// javaraytracer Main.java
// Date: 19 06 2020
// Author: arinaivanova
// Commentary: Basic raytracer in Java (JDK 14)
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;

class Main{

	static IShape[] shapes;
	static Vector[] colors;
	static Light light;
	
	public static void main(String args[]){
		final int w = 640, h = 480;
		BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

		shapes = new IShape[]{
			new Sphere(new Vector(-3,0,-10),5),
			new Triangle(new Vector(3,3,-12),
			             new Vector(8,4,-7),
			             new Vector(4,-8,-12))
		};
		colors = new Vector[]{
			new Vector(0.5,0,0),
			new Vector(0,0,0.5)
		};

		light = new Light(new Vector(0,0.7,-0.2),
		                  new Vector(1,1,1),
		                  0.2, 1.1, 64);

		final Vector origin = new Vector(0,0,0);

		final double fov = 1.57;
		// total width of half of screen
		final double total = Math.tan(fov*0.5);
		final double aspect = 1.0*w/h;
		
		for (int i = 0; i < h; ++i){
			for (int j = 0; j < w; ++j){
				// ray is cast in -Z direction in right-handed coordinate system
				Vector rayDir = new Vector(0,0,-1);
				// convert image pixel coordinate to world coordinate
				rayDir.x = total * (2*(j + 0.5)/w - 1) * aspect;
				rayDir.y = total * (1 - 2*(i + 0.5)/h);
				rayDir = rayDir.normalize();

				// cast ray for current pixel and return color of first object it intersects
				Vector rgb = trace(new Vector(origin), rayDir, 4);
				
				// convert image pixel color and write to image pixel
				bi.setRGB(j,i,packRgb(rgb));
			}
		}
		
		try{
			File outputfile = new File("image.png");
			ImageIO.write(bi, "png", outputfile);
		}catch (IOException e){
			System.err.println(e.getMessage());
		}
	}

	// casts ray and returns color of first interesected obejct, otherwise background color
	private static Vector trace(Vector origin, Vector rayDir, int depth){
		IShape shape = null;
		Vector color = null;
		// minimum distance to an intersected object
		double dist = Double.MAX_VALUE;
		for (int i = 0; i < shapes.length; ++i){
			double curDist = shapes[i].rayIntersect(origin, rayDir);
			if (curDist < dist){
				dist = curDist;
				shape = shapes[i];
				color = colors[i];
			}
		}
		// no intersection: return background color
		if (dist == Double.MAX_VALUE)
			return new Vector(0,0.4,0.7);
		
		Vector intersect = origin.add(rayDir.mul(dist));
		Vector norm;
		if (shape instanceof Sphere)
			norm = intersect.sub(((Sphere)shape).center).normalize();
		else
			norm = ((Triangle)shape).normal;
		
		// reflected direction (light direction points towards object, so subtract from light direction)
		Vector r = light.dir.sub(norm.mul(light.dir.dot(norm)*2));
		// diffuse
		double shade = light.ambient + Math.max(0.0, rayDir.dot(light.dir)) * light.intensity;
		// specular
		double spec = Math.pow(Math.max( rayDir.dot(r), 0.0 ), light.specExp);

		return color.mul(light.specRgb.mul(spec).add(shade));
	}

	// converts color RGB in range [0,1] to 32-bit image pixel color
	private static int packRgb(final Vector color){
		// clamp to range [0,1]
		color.x = Math.max(0.0, Math.min(1.0, color.x));
		color.y = Math.max(0.0, Math.min(1.0, color.y));
		color.z = Math.max(0.0, Math.min(1.0, color.z));
		// each R,G,B converted to range [0, 2^8] and shifted to its bit position in 32-bit integer
		return (int)(color.x * 255) << 16 | (int)(color.y * 255) << 8 | (int)(color.z * 255);
	}
}
