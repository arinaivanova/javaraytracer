# Basic Java Raytracer
A basic **raytracer in Java (JDK 14)** from scratch for a school task.

See [source code](https://github.com/arinaivanova/javaraytracer/tree/master)
## Sample input and usage
```java
	static IShape[] shapes;		// Geometric shapes in the scene
	static Vector[] colors;		// Color of a shape
	static Light light;		// Light source in the scene
	// ...
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
	light = new Light(new Vector(0,0.7,-0.2)
			  new Vector(1,1,1),
			  0.2, 1.1, 64);
```
## Sample output
![](https://github.com/arinaivanova/javaraytracer/blob/master/image.png)
