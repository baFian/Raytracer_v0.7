/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.material.Material;

import java.awt.Color;

/**
 *
 * @author Jafet
 */
public abstract class Object3D {  
    private Vector3D position;
    private Material material;

    public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Object3D(Vector3D position, Material material){
        setPosition(position);
        setMaterial(material);
    }
    
    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }


    
    public abstract Intersection getIntersection(Ray ray);
    
}
