package basic.unit;


public enum TimeUnitEnum implements IUnit{
	Year,Day,Month,Week,Hour,Minute,Second,Millisecond;

	@Override
	public Number convert(Number number, IUnit fromUnit, IUnit toUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name();
	}

	@Override
	public Object[] getAllUnits() {
		Object[] possibleValues = this.getDeclaringClass().getEnumConstants();
		return possibleValues;
	}

}
