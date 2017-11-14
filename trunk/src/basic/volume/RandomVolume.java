package basic.volume;

import basic.Distribution.RandomGenerator;
import basic.unit.IUnit;
import basic.unit.UnitEnum;

public class RandomVolume<T extends IUnit> extends Volume<T>{
	private RandomGenerator randomGenerator;
		
	public RandomVolume(){
		super();
		randomGenerator=new RandomGenerator();
		
	}
	
	public String getString(){
		return randomGenerator.getString()+" Unit: "+getUnit().getName();
	}
	
	public String getShortString(){
		return randomGenerator.getShortString();
	}
	@Override
	public Number getValue(){
		Class<?> c=getUnitValueClass(getUnit());
		if(c==Long.class)
		return randomGenerator.nextLong();
		else if(c==Double.class)
			return randomGenerator.nextDouble();
		else if(c==Integer.class)
			return randomGenerator.nextInt();
		return null;
	}

	@Override
	public void setValue(Number v){
		//do nothing
	}

	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}



	public void setRandomGenerator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
	
	private Class<?> getUnitValueClass(T unit){
		if(equalTo(unit,UnitEnum.box))
			return Integer.class;
		else if(equalTo(unit,UnitEnum.m))
			return Double.class;
		else if(equalTo(unit,UnitEnum.second))
			return Long.class;
		
		return null;
			
	}
	private boolean equalTo(T unit,UnitEnum u){
		return unit.getName().equals(u.name());
	}
}
