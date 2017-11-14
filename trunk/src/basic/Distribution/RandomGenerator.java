package basic.Distribution;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RandomGenerator implements Serializable{
	private Map<DistributionParameterEnum,DistributionParameter> parameters;
	private Distribution distribution;
	private int seed;
	public RandomGenerator() {
	
		parameters=new HashMap<DistributionParameterEnum,DistributionParameter> ();
		distribution=Distribution.Normal;
		parameters.put(DistributionParameterEnum.Min, new DistributionParameter(DistributionParameterEnum.Min,0.0));
		
		parameters.put(DistributionParameterEnum.Max, new DistributionParameter(DistributionParameterEnum.Max,0.0));
		parameters.put(DistributionParameterEnum.Mu, new DistributionParameter(DistributionParameterEnum.Mu,0.0));
		
	}
	public String getString(){
		String str="";
		for(DistributionParameter pa:parameters.values()){
			str+=pa.getName().name()+": "+String.format("%.2f",pa.getValue())+"; ";
		}
		if(str.equals("")){
			str="No parameters; ";
		}
		return "Distribution: "+distribution.name()+"; Seed: "+seed+"; "+str;
	}
	
	public String getShortString(){
		String str="";
		for(DistributionParameter pa:parameters.values()){
			str+=String.format("%.2f",pa.getValue())+", ";
		}
		if(!str.isEmpty()){
			str=str.substring(0, str.length()-2);
		}
		
		return distribution.name()+" ( "+str+" )";
	}
	public int nextInt(){
		return nextValue().intValue();
	}
	public long nextLong(){
	
		return nextValue().longValue();
	}
	public double nextDouble(){

		return nextValue().doubleValue();
	}
	
	private Number nextValue(){
		//TODO
		return 0;
	}

	public void setParameter(DistributionParameterEnum paraName,Double value){
		parameters.put(paraName, new DistributionParameter(paraName,value));
	}

	public Distribution getDistribution() {
		return distribution;
	}



	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	public Map<DistributionParameterEnum, DistributionParameter> getParameters() {
		return parameters;
	}
	public void setParameters(Map<DistributionParameterEnum, DistributionParameter> parameters) {
		this.parameters = parameters;
	}
}
