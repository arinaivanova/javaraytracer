// javaraytracer Light.java
// Date: 19 06 2020
// Author: arinaivanova
// Commentary: Directional light source
class Light{

	Vector dir, specRgb;
	double ambient, intensity;
	int specExp;
	
	public Light(final Vector dir, final Vector specRgb,
	             double ambient, double intensity, int specExp){
		this.dir = new Vector(dir).normalize();
		this.specRgb = new Vector(specRgb);
		this.ambient = ambient;
		this.intensity = intensity;
		this.specExp = specExp;
	}
}
