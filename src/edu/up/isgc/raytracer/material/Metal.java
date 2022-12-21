/**
 * 
 */
package edu.up.isgc.raytracer.material;

import java.awt.Color;
import java.util.ArrayList;
import edu.up.isgc.raytracer.*;
import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;

/**
 * @author Admin
 *
 */
public class Metal extends Material {

	/**
	 * 
	 */
	public Metal(Color col, float intensity, float shiny, float diffuse) {
		super(col, intensity, shiny, diffuse);
		// TODO Auto-generated constructor stub
	}
	

}
