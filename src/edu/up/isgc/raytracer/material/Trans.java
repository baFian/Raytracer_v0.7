/**
 * 
 */
package edu.up.isgc.raytracer.material;

import java.awt.Color;
import java.util.ArrayList;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Raytracer;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;
import edu.up.isgc.raytracer.objects.Sphere;

/**
 * @author Admin
 *
 */
public class Trans extends Material {
	
	
	float ref = (float) 0.0;
	
	public float getRef() {
		return ref;
	}
	public void setRef(float ref) {
		this.ref = ref;
	}
	/**
	 * 
	 */
	public Trans(Color col, float intensity, float shiny, float diffuse, float ref) {
		super(col, intensity, shiny, diffuse);
		setRef(ref);
		// TODO Auto-generated constructor stub
	}
	
	public static Intersection calculateRefraction(Intersection interseccion, Light light, ArrayList<Object3D> objects, Camera mainCam)
	{
		float fresnel = 0;
		Vector3D Hola = Vector3D.ZERO();
	 Intersection lastInter = null;
	 float val =(float) 1.0;
	 float I =0;
	 float Tt=1;
	 float T1  =0;
	 float CosT=0;
	 float past =0;
	 Vector3D collide = null;
	 float D = 1;
	 Vector3D Created = null;
	 Vector3D A = interseccion.getNormal();
	 float IoR  = ((Trans) interseccion.getObject().getMaterial()).getRef();
	 if(interseccion.getObject() instanceof Sphere)
	 {
		 Vector3D Am = Vector3D.scalarMultiplication(A,val);
		 fresnel = (float) (Math.pow((IoR-1),2) / Math.pow((IoR+1), 2));
		 
		 if(fresnel <0)
		 {
			  collide = Vector3D.substract(interseccion.getPosition(), Am);
		 }
		 else
		 {
			  collide = Vector3D.add(interseccion.getPosition(), Am);
		 }
		 
	 }
	 else
	 {
		  collide = interseccion.getPosition();
	 }
	 
	Vector3D P = Vector3D.substract(collide, mainCam.getPosition());
	I = (float) Vector3D.dotProduct(P, A);
	
	T1 = IoR;
	
	CosT = Raytracer.clamp((float)-1.0, (float)1.0, I);
	Vector3D N = null;
	
	if(CosT >0)
	{
		past = CosT;
		Tt = T1;
		T1 = past;
		N = Vector3D.scalarMultiplication(A, -1);
		
	}if(CosT<0) {
		CosT = CosT * -1;
	}
	
	Tt /= T1;
	
	D -= Math.pow(Tt, 2) * (1- Math.pow(CosT, 2));
	
	if(Tt >=0)
	{
		Hola = Vector3D.add(Vector3D.scalarMultiplication(P, Tt), Vector3D.scalarMultiplication(N, ((Tt * CosT)-Math.sqrt(D))));
	}
	
	Created = Hola;
	
	Ray nRay = new Ray(collide, Created);
	
	Intersection Refraccion  = Raytracer.raycast(nRay, objects, interseccion.getObject(), null);
	
	return Refraccion;
	
	
		
	}


}
