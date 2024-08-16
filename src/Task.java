import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task implements Comparable<Object> {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }
    
    
    public boolean endsBefore(Task second) {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	LocalTime time1 = LocalTime.parse(this.getFinishTime(), formatter);
    	LocalTime time2 = LocalTime.parse(second.start, formatter);
    	
    	if (time1.isBefore(time2) || time1.equals(time2)) 
    		return true;
    	else 
    		return false;
    }

    
    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
    	String[] start = this.start.split(":");
    	int temp = Integer.parseInt(start[0]);
    	int end = ((temp+this.duration) >24) ? temp+this.duration-24 : temp+this.duration;
        return ((end < 10) ? "0" : "") + end + ":" + start[1];
    }

    
    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        return (this.importance * (this.urgent ? 2000 : 1)) / this.duration;
    }

    
    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
    	String[] first = this.getFinishTime().split(":");
    	String[] second = ((Task) o).getFinishTime().split(":");
    	
    	int[] times = {Integer.parseInt(first[0]), Integer.parseInt(first[1]), 
    			       Integer.parseInt(second[0]), Integer.parseInt(second[1])}; 
        
    	if (times[0] == times[2]) 
    		return times[1] - times[3];
    	else
    		return times[0] - times[2];
    }

    
	@Override
	public String toString() {
		return "\nAt " + start + ", " + name + ".";
	}
}
