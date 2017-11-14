package manu.product;

import java.io.Serializable;


public class ActivityStartCondition implements Serializable{
	private Activity relatedActivty;
	private ActivitiyStartLogic startLogic;
	private double value;
	public ActivityStartCondition(){
		startLogic=ActivitiyStartLogic.AFTER_START;
	}
	public Activity getRelatedActivty() {
		return relatedActivty;
	}
	public ActivitiyStartLogic getStartLogic() {
		return startLogic;
	}
	public double getValue() {
		return value;
	}
	public void setRelatedActivty(Activity relatedActivty) {
		this.relatedActivty = relatedActivty;
	}
	public void setStartLogic(ActivitiyStartLogic startLogic) {
		this.startLogic = startLogic;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}


