package manu.product;

import java.io.Serializable;

import basic.volume.TimeVolume;
import manu.factory.Resource;

public class ProcessingTimeElement implements Serializable{
	private Resource resource;
	private int amount=1;
	private TimeVolume time;
	
	public ProcessingTimeElement(){
		time=new TimeVolume();
	}
	public ProcessingTimeElement(Resource resource){
		this();
		this.resource=resource;
	}
	public TimeVolume getTime() {
		return time;
	}
	public void setTime(TimeVolume time) {
		this.time = time;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAmount() {
		return amount;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Resource getResource() {
		return resource;
	}
}
