/**
 * 
 */
package edu.up.isgc.raytracer.material;

import java.awt.Color;
import java.util.ArrayList;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;

/**
 * @author Admin
 *
 */
public abstract class Material {
	Color col;
	float intensity;
	/**
	 * @return the col
	 */
	public Color getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(Color col) {
		this.col = col;
	}

	/**
	 * @return the intensity
	 */
	public float getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity the intensity to set
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the shiny
	 */
	public float getShiny() {
		return shiny;
	}

	/**
	 * @param shiny the shiny to set
	 */
	public void setShiny(float shiny) {
		this.shiny = shiny;
	}

	/**
	 * @return the diffuse
	 */
	public float getDiffuse() {
		return diffuse;
	}

	/**
	 * @param diffuse the diffuse to set
	 */
	public void setDiffuse(float diffuse) {
		this.diffuse = diffuse;
	}

	float shiny;
	float diffuse;
	
	
	
	public Material(Color col, float intensity, float shiny, float diffuse)
	{
		setCol(col);
		setIntensity(intensity);
		setShiny(shiny);
		setDiffuse(diffuse);
	}
	
	public static float[] colorFormula(Light light, Intersection interseccion, Camera cam, ArrayList<Object3D> objects)
	{
		Color color1 = null;
		float nDotL = light.getNDotL(interseccion);
		float intensity = (float) light.getMaterial().getIntensity() * nDotL;
				if(light instanceof PointLight) {
			intensity /= Math.pow(Vector3D.magnitude(Vector3D.substract(interseccion.getPosition(),light.getPosition())),2);
		}
		Vector3D L = Vector3D.substract(light.getPosition(), interseccion.getPosition());
		Vector3D V = Vector3D.substract(cam.getPosition(), interseccion.getPosition());
		
		Vector3D H = Vector3D.normalize((Vector3D.add(L, V)));
		
		float[] colors = new float[] { 
				interseccion.getObject().getMaterial().getCol().getRed() / 255.0f,
				interseccion.getObject().getMaterial().getCol().getGreen() / 255.0f,
				interseccion.getObject().getMaterial().getCol().getBlue() / 255.0f };
		
		
		
		float specular = 1f;
		float ambient = 0.05f;
		float sm =interseccion.getObject().getMaterial().getDiffuse();
		
		
		float[] Colors = new float[] {(float)0.0,(float)0.0,(float)0.0};
		
		specular *= (float)Math.pow(Vector3D.dotProduct(interseccion.getNormal(),H), interseccion.getObject().getMaterial().getShiny());
		
		
		Colors[0] += colors[0] *= ambient;
		Colors[1] += colors[0] *= ambient;
		Colors[2] += colors[0] *= ambient;
		
		Colors[0] += colors[0] *= intensity * (light.getMaterial().getCol().getRed() / 255.0f) * sm;
		Colors[1] += colors[1] *= intensity * (light.getMaterial().getCol().getGreen() / 255.0f) * sm;
		Colors[2] += colors[2] *= intensity * (light.getMaterial().getCol().getBlue() / 255.0f) * sm;
		
		Colors[0] += colors[0] *= intensity * (light.getMaterial().getCol().getRed() / 255.0f) * specular;
		Colors[1] += colors[1] *= intensity * (light.getMaterial().getCol().getGreen() / 255.0f) * specular;
		Colors[2] += colors[2] *= intensity * (light.getMaterial().getCol().getBlue() / 255.0f) * specular;
		

		
		return Colors;
		
	}
	
	
	
}
