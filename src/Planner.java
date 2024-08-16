import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;
    

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        this. maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();

        
        calculateCompatibility();
        System.out.print("Calculating max array\n---------------------\n");
    	calculateMaxWeight(taskArray.length - 1);
    }

    
    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
    	
    	int low = 0;
    	int high = index - 1;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	LocalTime time1 = LocalTime.parse(this.taskArray[index].start, formatter);
    
    	
    	while (low <= high) {
    		int mid = (low+high) / 2;
    		LocalTime time2 = LocalTime.parse(this.taskArray[mid].getFinishTime(), formatter);
    		
    		if (time2.isBefore(time1) || time2.equals(time1)) {
    			LocalTime temp = LocalTime.parse(this.taskArray[mid+1].getFinishTime(), formatter);
    			
    			if (mid == index-1 || temp.isAfter(time1)) 
    				return mid;
    			else 
					low = mid + 1;
    		}
    		else {
				high = mid - 1;
			}
    	}
        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for (int i = 0; i < taskArray.length; i++) {
        	compatibility[i] = 	binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
    	
    	System.out.print("\nCalculating the dynamic solution\n--------------------------------\n");
    	solveDynamic(taskArray.length - 1);
    	
    	Collections.reverse(planDynamic);
    	System.out.print("\nDynamic Schedule\n----------------");
    	
    	for (Task task : planDynamic) {
			System.out.print(task.toString());
		}

        return planDynamic;
    }

    
    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
    	
        if (i == -1)
        	return;
        System.out.print("Called solveDynamic(" + i + ")\n");
        
        Double include;
        if (compatibility[i] == -1)
        	include = 0.0;
        else
        	include = maxWeight[compatibility[i]];
        
        Double exclude;
        if (i == 0)
        	exclude = 0.0;
        else
        	exclude = maxWeight[i-1];
        
        Double case1 = taskArray[i].getWeight() + include;
        Double case2 = exclude;
        
        if(case1 >= case2) {
        	planDynamic.add(taskArray[i]);
        	solveDynamic(compatibility[i]);
        }
        else {
        	solveDynamic(i-1);
        }
    }

    
    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
    	
    	System.out.print("Called calculateMaxWeight(" + i + ")\n");
    	
    	if (i == -1) {
    		return 0.0;
    	}
    	else if(maxWeight[i] != null && i != 0) {
    		return maxWeight[i];
    	}
    	else {
        	Double case1 = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
        	Double case2 = calculateMaxWeight(i-1);
    		maxWeight[i] = Math.max(case1, case2);
    	}
    	return maxWeight[i];
    }
    

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
    	
    	Task initial = taskArray[0];
    	planGreedy.add(initial);
    	
    	for (int i=1; i<taskArray.length; i++) {
    		
    		if(initial.endsBefore(taskArray[i])) {
    			initial = taskArray[i];
    			planGreedy.add(initial);
    		}
    	}
    	
    	System.out.print("\n\nGreedy Schedule\n---------------");
    	
    	for (Task task : planGreedy) {
			System.out.print(task.toString());
		}
        return planGreedy;
    }
}
