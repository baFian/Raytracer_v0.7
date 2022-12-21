/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer;

import edu.up.isgc.raytracer.objects.Sphere;
import edu.up.isgc.raytracer.objects.Triangle;
import edu.up.isgc.raytracer.tools.OBJReader;
import edu.up.isgc.raytracer.lights.DirectionalLight;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.material.Material;
import edu.up.isgc.raytracer.material.Metal;
import edu.up.isgc.raytracer.material.Smooth;
import edu.up.isgc.raytracer.material.Trans;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Jafet
 */
public class Raytracer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		System.out.println(new Date());

		Scene scene01 = new Scene();
		//scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 0.0f, 50f, new Metal(Color.green,13,11,22)));
		scene01.setCamera(new Camera(new Vector3D(0,0.5,-8),160,160, 1200, 1200, 0f, 50f));
		//scene01.addLight(new PointLight(new Vector3D(7.5,0.0, -7.5), Color.WHITE,1.75f));
		//scene01.addLight(new PointLight(new Vector3D(0.0,0.0,-10.0), Color.WHITE,1.75f));
		//scene01.addLight(new PointLight(new Vector3D(-7.5,1.0, -7.5), Color.WHITE,1.75f));
		//scene01.addLight(new PointLight(new Vector3D(0.0,5.0,0.0), Color.WHITE,1.75f));
		scene01.addLight(new PointLight(new Vector3D(3,2.0,0.0), new Smooth(Color.white,100,0,0)));
		scene01.addLight(new PointLight(new Vector3D(-5,2.0,0.0), new Smooth(Color.white,200,0,0)));
		//scene01.addLight(new DirectionalLight(new Vector3D(-5,2.0,0.0), new Vector3D(1,2,3), new Smooth(Color.white,200,0,0)));
		//scene01.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(0.0f, -2.0f, 0.0f), Color.red));
		scene01.addObject(OBJReader.GetPolygon("Platform.obj", new Vector3D(0.0f, -3.0f, 0.0f), new Metal(Color.magenta,700,700,700)));
		//scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-0.5f, -1.8f, 3f), new Smooth(Color.yellow,300,300,300)));
		//scene01.addObject(OBJReader.GetPolygon("smallTeapot.obj",new Vector3D(0.0f, -3.0f, 0.0f), Color.CYAN, new Metal(Color.black,12f,13f, 11)));
		//scene01.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(1f, -1.8f, 1.5f), new Metal(Color.magenta, 3f,200,0.1f)));
		//scene01.addObject(OBJReader.GetPolygon("ring.obj", new Vector3D(-2f, -1f, 5f), new Metal(Color.red, 0f,150f,0.1f)));
		//scene01.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(-1f, -1.7f, 1.5f), new Metal(Color.pink, 1f,150f,0.1f)));
		//scene01.addObject(new Sphere(new Vector3D(-0.5, -1.9, 3), 0.6, new Metal(Color.gray, 5, 05, 0.05f)));
		//scene01.addObject(new Sphere(new Vector3D(-1.5, 1, 0), 0.6, new Metal(Color.blue, 15, 150, 0.05f)));
		scene01.addObject(OBJReader.GetPolygon("Bunny.obj", new Vector3D(-3f, -2f, 2f), new Metal(Color.red, 100f,100f,100f)));
		scene01.addObject(OBJReader.GetPolygon("Bunny.obj", new Vector3D(0f, -2f, 3f), new Metal(Color.BLUE, 100f,100f,100f)));
		scene01.addObject(OBJReader.GetPolygon("Bunny.obj", new Vector3D(3f, -2f, 1f), new Metal(Color.MAGENTA, 100f,100f,100f)));
		//scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-2f, -1f, 5f), new Metal(Color.ORANGE, 2000,2000,2000)));
		//scene01.addObject(new Sphere(new Vector3D(-2, -1, 0), 0.6, new Metal(Color.blue, 15, 150, 0.05f)));
		//scene01.addObject(new Sphere(new Vector3D(2, -1, 0), 0.6, new Metal(Color.gray, 15, 150, 0.05f)));
		scene01.addObject(new Sphere(new Vector3D(0, -1, 3), 0.6, new Metal(Color.blue, 15, 150, 0.05f)));
		//scene01.addObject(new Sphere(new Vector3D(2, -1, 0), 0.6, new Trans(Color.gray, 15, 150, 0.05f, 1)));
		BufferedImage image = raytrace(scene01);
		File outputImage = new File("metal.png");
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
		Intersection closestIntersection = null;

		for (int k = 0; k < objects.size(); k++) {
			Object3D currentObj = objects.get(k);

			if (caster == null || !currentObj.equals(caster)) {
				Intersection intersection = currentObj.getIntersection(ray);
				if (intersection != null) {
					double distance = intersection.getDistance();

					if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance()) && (clippingPlanes == null
							|| (intersection.getPosition().getZ() >= clippingPlanes[0] && intersection.getPosition().getZ() <= clippingPlanes[1]))) {
						closestIntersection = intersection;
					}
				}
			}
		}

		return closestIntersection;
	}

	public static BufferedImage raytrace(Scene scene) {
		Camera mainCamera = scene.getCamera();
		ArrayList<Light> lights = scene.getLights();
		float[] nearFarPlanes = mainCamera.getNearFarPlanes();
		BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1], BufferedImage.TYPE_INT_RGB);
		ArrayList<Object3D> objects = scene.getObjects();

		Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
		for (int i = 0; i < positionsToRaytrace.length; i++) {
			for (int j = 0; j < positionsToRaytrace[i].length; j++) {
				double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
				double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
				double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
				Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));

				Intersection closestIntersection = raycast(ray, objects, null, new float[] {
						(float) mainCamera.getPosition().getZ() + nearFarPlanes[0], (float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });

				// Background color
				Color pixelColor = Color.BLACK;
				if (closestIntersection != null) {
					
					pixelColor = Color.BLACK;
					
					for (Light light : lights) {
						float[] colors = Material.colorFormula(light, closestIntersection, mainCamera, objects);
						
						if(closestIntersection.getObject().getMaterial() instanceof Metal)
						{
							
							//Intersection nInter = calculateReflection(closestIntersection, light, mainCamera, objects);
							Vector3D N = closestIntersection.getNormal();
							Vector3D I = Vector3D.substract(closestIntersection.getPosition(), mainCamera.getPosition());
							Vector3D R = Vector3D.add(I, Vector3D.scalarMultiplication(N, -2 * Vector3D.dotProduct(N, I)));
							Vector3D B = R;
							Ray reflectRay = new Ray(closestIntersection.getPosition(),B);
							
							Intersection refIntersection  =  Raytracer.raycast(reflectRay, objects, closestIntersection.getObject(), null);
										
							
							if(refIntersection != null)
							{
								
								Light nLight = null;
								if( light instanceof DirectionalLight)
								{
									 nLight =  new DirectionalLight(light.getPosition(),((DirectionalLight) light).getDirection(), new Smooth(closestIntersection.getObject().getMaterial().getCol(), light.getMaterial().getIntensity(),0,0));
								}
								else if(light instanceof PointLight)
								{
									 nLight = new PointLight (light.getPosition(), new Smooth(closestIntersection.getObject().getMaterial().getCol(), light.getMaterial().getIntensity(),0,0));
								}
								Intersection refIntersection11 = null;
								if(refIntersection.getObject().getMaterial() instanceof Metal)
								{
									Vector3D N1 = refIntersection.getNormal();
									Vector3D I1 = Vector3D.substract(refIntersection.getPosition(), mainCamera.getPosition());
									Vector3D R1 = Vector3D.add(I1, Vector3D.scalarMultiplication(N1, -2 * Vector3D.dotProduct(N1, I1)));
									
									Vector3D a = R1;
									Ray Rray = new Ray(refIntersection.getPosition(),a);
		
									 refIntersection11  =  Raytracer.raycast(Rray, objects, refIntersection.getObject(), null);
									
								}
								Light light2 = null;
								if(refIntersection11 != null)
								{
									
									
									if( light instanceof DirectionalLight)
									{
										light2 =  new DirectionalLight(light.getPosition(),((DirectionalLight) light).getDirection(), new Smooth(closestIntersection.getObject().getMaterial().getCol(), light.getMaterial().getIntensity(),0,0));
									}
									else if(light instanceof PointLight)
									{
										light2 = new PointLight (light.getPosition(), new Smooth(closestIntersection.getObject().getMaterial().getCol(), light.getMaterial().getIntensity(),0,0));
									}
									
									colors = Material.colorFormula(light2, refIntersection11, mainCamera, objects);
									
									Ray shadowRay = new Ray(refIntersection11.getPosition(),light2.getPosition());
									Intersection shadowIntersection = raycast(shadowRay,objects,refIntersection11.getObject(),null);
									Color diffuse = Color.black;
									if(shadowIntersection == null) {
										diffuse = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
									}
									
									pixelColor = addColor(pixelColor, diffuse);
								}else
								{
									colors = Material.colorFormula(nLight, refIntersection, mainCamera, objects);
									
									Ray shadowRay = new Ray(refIntersection.getPosition(),nLight.getPosition());
									Intersection shadowIntersection = raycast(shadowRay,objects,refIntersection.getObject(),null);
									Color diffuse = Color.black;
									if(shadowIntersection == null) {
										diffuse = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
									}
									
									pixelColor = addColor(pixelColor, diffuse);
								}
								
							}
							Color color1 = new Color (clamp(colors[0], 0, 1), clamp(colors[1], 0, 1),
									clamp(colors[2], 0, 1));
							pixelColor = addColor(pixelColor, color1);
						
								
							
						}
					}

				}
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}

		return image;
	}

	public static float clamp(float value, float min, float max) {
		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;
	}

	public static Color addColor(Color original, Color otherColor) {
		float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
		float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
		float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
		return new Color(red, green, blue);
	}

}
