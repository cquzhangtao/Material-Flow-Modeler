package manu.product;

public interface TimeSlice {

	public abstract long getStartTime();

	public abstract long getEndtime();

	public abstract void setStartTime(long startTime);

	public abstract void setEndtime(long endtime);
	
	public abstract long getDuration();

}