package manu.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manu.factory.Resource;

import basic.volume.TimeVolume;

public class ProcessingTime implements Serializable{
	private ProcessingTimeLogic processingTimeLogic;
	private TimeVolume processingTime;
	private List<ProcessingTimeElement> processingTimeMap;
	private List<ProcessingTimeElement> processingTimeMapResAmountDirectProportion;
	private List<ProcessingTimeElement> processingTimeMapResAmount;
	
	public ProcessingTime(){
		processingTimeLogic=ProcessingTimeLogic.Resources_Independent;
		processingTime=new TimeVolume();
		processingTimeMap=new ArrayList<ProcessingTimeElement> ();
		processingTimeMapResAmountDirectProportion=new ArrayList<ProcessingTimeElement> ();
		processingTimeMapResAmount=new ArrayList<ProcessingTimeElement> ();
	}
	public List<Resource> getAllPossibleResources(){
		List<Resource> rs=new ArrayList<Resource>();
		for(ProcessingTimeElement ele:processingTimeMap){
			rs.add(ele.getResource());
		}
		return rs;
	
	}
	
	public ProcessingTimeLogic getProcessingTimeLogic() {
		return processingTimeLogic;
	}
	public TimeVolume getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTimeLogic(ProcessingTimeLogic processingTimeLogic) {
		this.processingTimeLogic = processingTimeLogic;
	}
	public void setProcessingTime(TimeVolume processingTime) {
		this.processingTime = processingTime;
	}


	public TimeVolume getProcessingTime(Resource res){
		for(ProcessingTimeElement ele:processingTimeMap){
			if(ele.getResource()==res){
				return ele.getTime();
			}
		}
		return null;
	}
	public TimeVolume getProcessingTime(Resource res,int amount){
		for(ProcessingTimeElement ele:processingTimeMapResAmount){
			if(ele.getResource()==res&&ele.getAmount()==amount){
				return ele.getTime();
			}
		}
		return null;
	}
	public TimeVolume getProcessingTimePerResource(Resource res){
		for(ProcessingTimeElement ele:processingTimeMapResAmountDirectProportion){
			if(ele.getResource()==res){
				return ele.getTime();
			}
		}
		return null;
	}

	public List<ProcessingTimeElement> getProcessingTimeMap() {
		return processingTimeMap;
	}

	public List<ProcessingTimeElement> getProcessingTimeMapResAmountDirectProportion() {
		return processingTimeMapResAmountDirectProportion;
	}

	public List<ProcessingTimeElement> getProcessingTimeMapResAmount() {
		return processingTimeMapResAmount;
	}

	public void setProcessingTimeMap(List<ProcessingTimeElement> processingTimeMap) {
		this.processingTimeMap = processingTimeMap;
	}

	public void setProcessingTimeMapResAmountDirectProportion(
			List<ProcessingTimeElement> processingTimeMapResAmountDirectProportion) {
		this.processingTimeMapResAmountDirectProportion = processingTimeMapResAmountDirectProportion;
	}

	public void setProcessingTimeMapResAmount(
			List<ProcessingTimeElement> processingTimeMapResAmount) {
		this.processingTimeMapResAmount = processingTimeMapResAmount;
	}
}
