//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     ThreadExecutor.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      03/13/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       4
//
//  Description:   
//
//*********************************************************************

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
	
public class ThreadExecutor {
	
	private Scanner userIn = new Scanner(System.in);
	
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the project
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args) {
		
		ThreadExecutor obj = new ThreadExecutor();
		obj.developerInfo();
		obj.printInstructions();
		
		int count; // number of integers to process
		ExecutorService pool = Executors.newFixedThreadPool(3); // fixed at 3
		
		/* Continue to perform calculations until 
		 * the user enters a 0 to quit.*/
		do {
			count = obj.getCount();
			int[] unsortedInts = obj.getRandomArray(count);
			System.out.print("Unsorted: ");
			obj.printArray(unsortedInts);
			int[] sortedInts = obj.sort(unsortedInts, pool);
			System.out.print("Sorted: ");
			obj.printArray(sortedInts);
			
		} while(count > 0);
		
		System.out.println("Goodbye");

	}// end main method

    //***************************************************************
    //
    //  Method:       printInstructions (Non Static)
    // 
    //  Description:  Displays simple instructions to the user on how
    //                the use the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public void printInstructions() {
		
		System.out.println("\nMultithreaded Sorting Application");
		System.out.println("----------------------------------------------");
		System.out.println("Enter the number of integers to sort");
		System.out.println("Enter 0 to quit");
		System.out.println("----------------------------------------------");	
		
	}// end printInstructions method
	
    //***************************************************************
    //
    //  Method:       getInput (Non Static)
    // 
    //  Description:  Gets input from the user.
    // 
    //  Parameters:   None
    //
    //  Returns:      String 
    //
    //**************************************************************
	public String getInput () {
		
		String input = userIn.nextLine();
		return input;
		
	}// end getInput method
	
    //***************************************************************
    //
    //  Method:       getCount (Non Static)
    // 
    //  Description:  Attempts to get an integer count from the user
	//
    //  Parameters:   None
    //
    //  Returns:      int (count)
    //
    //***************************************************************
	public int getCount() {
		
		int count = -1;
		
		while(count < 0) {
			
			try {
				
				System.out.print("Enter a number: "); // prompt user for input
				int temp = Integer.parseInt(getInput());
				if(temp >= 0) { //validate 
					count = temp;
				} else {
					System.out.println("Number must be between 1 and 99");
				}
				
			} catch (NumberFormatException e) {
				
				System.out.println("Must enter a number");
			}
		}
		
		return count;
	
	}//end getCount method
	
	public int[] getRandomArray(int count) {
		
		int[] randomArray = new int[count];
		if(count > 0) {
			
			for(int i = 0; i < count; i++) {
				randomArray[i] = getRandomInt();
			}
		}
		
		return randomArray;
	}
	
	public int getRandomInt() {
		
		return (int) (Math.random() * 100);
	}
	
	public int[] sort (int[] unsortedArray, ExecutorService pool) {
		
		int length = unsortedArray.length;
		
	    if (length < 2) {
	        return unsortedArray;
	    }
	    
	    int mid = length / 2;
	    int[] leftHalf = new int[mid];
	    int[] rightHalf = new int[length - mid];

	    for (int i = 0; i < mid; i++) {
	    	leftHalf[i] = unsortedArray[i];
	    }
	    for (int i = mid; i < length; i++) {
	    	rightHalf[i - mid] = unsortedArray[i];
	    }
	    
	    Future<int[]> sortedLeft = pool.submit(new SortTask(leftHalf));
	    Future<int[]> sortedRight = pool.submit(new SortTask(rightHalf));
	    Future<int[]> merged = pool.submit(new MergeTask(sortedLeft, sortedRight));
	    int[] results = null;
	    
		try {
			results = merged.get();
		} catch (InterruptedException | ExecutionException e) {

			e.printStackTrace();
		}
	    
	    return results;
	}
	
	public void printArray(int[] arr) {
		System.out.print("[");
		if(arr != null) {
			for(int i : arr ) {
				System.out.print(" " + i + " ");
			}
		} 
		System.out.print("]\n");
	}
	
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //                It must be included in every program you write
    //                in this course.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: 4");

    }// end developerInfo method
    
}// end ThreadExecutor class
