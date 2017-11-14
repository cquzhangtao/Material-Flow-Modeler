package manu.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import manu.factory.Resource;
import manu.others.Skill;
import basic.entity.GraphicNetworkTreeNodeEntity;
import basic.entity.TreeNode;
import basic.volume.QuatityVolume;

public class Activity extends GraphicNetworkTreeNodeEntity implements TimeSlice{
	private static int count=0;
	private long startTime;
	private long endtime;
	private Skill skill;
	private QuatityVolume volume;
	private List<ActivityStartCondition>startConditions;
	private ProcessingTime processingTime;
	private ActivityType activityType;
	private List<InvovedMaterialIn> invovedMaterialIn;
	private List<InvovedMaterialOut> invovedMaterialOut;
	public Activity(){
		super();
		setName("Activity"+(++count));
		startConditions=new ArrayList<ActivityStartCondition>();
		setProcessingTime(new ProcessingTime());
		volume=new QuatityVolume();
		Random rnd=new Random();
		startTime=rnd.nextInt(80);
		endtime=startTime+10+rnd.nextInt(20);
		activityType=ActivityType.Normal;
		setInvovedMaterialIn(new ArrayList<InvovedMaterialIn>());
		invovedMaterialOut=new ArrayList<InvovedMaterialOut>();
	}
	public void createProcessingTime(){
		List<Resource>resouces=getResourcesWithSkill();
		processingTime.getProcessingTimeMap().clear();
		processingTime.getProcessingTimeMapResAmountDirectProportion().clear();
		for(Resource res:resouces){
			processingTime.getProcessingTimeMap().add(new ProcessingTimeElement(res));
			processingTime.getProcessingTimeMapResAmountDirectProportion().add(new ProcessingTimeElement(res));
		}
	}
	public List<Resource>getResourcesWithSkill(){
		List<TreeNode> nodes = getNodesWithClass(Resource.class);
		List<Resource> res=new ArrayList<Resource>();
		for(TreeNode node:nodes){
			if(((Resource)node).getSkills().contains(getSkill())){
				res.add((Resource) node);
			}
		}
		return res;
	}
	public List<ActivityStartCondition> getStartConditions() {
		return startConditions;
	}

	public void setStartConditions(List<ActivityStartCondition> startConditions) {
		this.startConditions = startConditions;
	}

	public Skill getSkill() {
		return skill;
	}

	public QuatityVolume getVolume() {
		return volume;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public void setVolume(QuatityVolume volume) {
		this.volume = volume;
	}

	/* (non-Javadoc)
	 * @see manu.product.TimeSlice#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return startTime;
	}

	/* (non-Javadoc)
	 * @see manu.product.TimeSlice#getEndtime()
	 */
	@Override
	public long getEndtime() {
		return endtime;
	}

	/* (non-Javadoc)
	 * @see manu.product.TimeSlice#setStartTime(long)
	 */
	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/* (non-Javadoc)
	 * @see manu.product.TimeSlice#setEndtime(long)
	 */
	@Override
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	@Override
	public long getDuration() {
		// TODO Auto-generated method stub
		return endtime-startTime;
	}

	public void setProcessingTime(ProcessingTime processingTime) {
		this.processingTime = processingTime;
	}

	public ProcessingTime getProcessingTime() {
		return processingTime;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setInvovedMaterialIn(List<InvovedMaterialIn> invovedMaterial) {
		this.invovedMaterialIn = invovedMaterial;
	}

	public List<InvovedMaterialIn> getInvovedInMaterialIn() {
		return invovedMaterialIn;
	}

	public void setInvovedMaterialOut(List<InvovedMaterialOut> invovedMaterialOut) {
		this.invovedMaterialOut = invovedMaterialOut;
	}

	public List<InvovedMaterialOut> getInvovedMaterialOut() {
		return invovedMaterialOut;
	}
}
