package manu.factory;

import java.util.ArrayList;
import java.util.List;

import manu.others.Property;

import basic.entity.SpatialEntity;
import basic.entity.TreeNodeEntity;
import basic.unit.UnitSystem;
import basic.volume.RandomVolume;

public class Material extends TreeNodeEntity {

	private List<Property> properties;
	private static int count = 0;

	public Material() {
		super();
		setName("Material" + (++count));
		properties=new ArrayList<Property>();
		Property prop = new Property(UnitSystem.Weight);
		
		properties.add(prop);
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
