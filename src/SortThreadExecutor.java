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
//  Description:   A multi-threaded sorting application that generates a 
//                 random array of integers based on user input (a count)
//                 and distributes the sorting and merging work to separate
//                 threads.
//
//*********************************************************************

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
	
public class SortThreadExecutor {
	
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
		
		SortThreadExecutor obj = new SortThreadExecutor();
		obj.developerInfo();
		obj.printInstructions();
		
		int count; // number of integers to process
		ExecutorService pool = Executors.newFixedThreadPool(3); // (2) sorting threads, (1) final merging thread
		
		// continue to perform calculations until user quits
		do {
			
			count = obj.getCount(); // get count from user
			int[] unsortedInts = obj.getRandomArray(count); // get random int array
			int[] sortedInts = obj.sort(unsortedInts, pool); // send to sort
			obj.displayResults(unsortedInts, sortedInts); // display results 
			
		} while(count > 0); // 0 is sentinel value
		
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
		
		System.out.println("\nMulti-threaded Sorting Application");
		System.out.println("----------------------------------------------");
		System.out.println("Enter the number of integers to sort");
		System.out.println("Enter 0 to quit");
		System.out.println("----------------------------------------------");	
		
	}// end printInstructions method
	
    //***************************************************************
    //
    //  Method:       displayResults (Non Static)
    // 
    //  Description:  Helper method to print the sorted and unsorted 
    //                arrays.
    //
    //  Parameters:   int[] (unsorted), int[] (sorted)
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public void displayResults(int[] unsorted, int[] sorted) {
		
		if(unsorted.length != 0) {
			System.out.print("Unsorted: ");
			printArray(unsorted);
			System.out.print("Sorted: ");
			printArray(sorted);
		}
	}// end displayResults method
	
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
    //  Description:  Attempts to get an integer count from the user.
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
				if(temp >= 0) { //validate user input
					count = temp;
				} else {
					System.out.println("Number must be positive.");
				}
				
			} catch (NumberFormatException e) {
				
				System.out.println("Must enter a number.");
			}
		}
		
		return count;
	
	}// end getCount method
	
    //***************************************************************
    //
    //  Method:       getRandomArray (Non Static)
    // 
    //  Description:  Creates and returns an array of randomly generated
    //                integers.
	//
    //  Parameters:   int (count) 
    //
    //  Returns:      int[]
    //
    //***************************************************************
	public int[] getRandomArray(int count) {
		
		int[] randomArray = null;
		if(count > 0) { // validate 
			randomArray = new int[count];
			for(int i = 0; i < count; i++) {
				randomArray[i] = getRandomInt();
			}
		} else {
			randomArray = new int[0]; // empty array for invalid count
		}
		
		return randomArray;
		
	}// end getRandomArray
	
    //***************************************************************
    //
    //  Method:       getRandomInt (Non Static)
    // 
    //  Description:  Generates a random integer from 1 to 99.
	//
    //  Parameters:   None
    //
    //  Returns:      int
    //
    //***************************************************************
	public int getRandomInt() {		
		
		int min = 1; 
		int max = 100; // exclusive so 1 - 99
		Random random = new Random();
		return random.nextInt(max - min) + min;
		
	}// end getRandomInt
	
    //***************************************************************
    //
    //  Method:       sort (Non Static)
    // 
    //  Description:  Sorts an array of integers using the threads 
    //                available in the provided thread pool.
	//
    //  Parameters:   int[], ExecutorService (thread pool)
    //
    //  Returns:      int[] (sorted array)
    //
    //***************************************************************
	public int[] sort(int[] unsortedArray, ExecutorService pool) {
		
		int length = unsortedArray.length;
		
	    if (length < 2) {
	        return unsortedArray; // return if no sorting necessary
	    }
	    
	    // create 2 sub arrays
	    int mid = length / 2;
	    int[] leftHalf = new int[mid]; // first half (up to mid point)
	    int[] rightHalf = new int[length - mid]; // second half (mid point to end)

	    // populate left sub array
	    for (int i = 0; i < mid; i++) {
	    	leftHalf[i] = unsortedArray[i];
	    }
	    
	    // populate right sub array
	    for (int i = mid; i < length; i++) {
	    	rightHalf[i - mid] = unsortedArray[i];
	    }
	    
	    // divide sorting and merging among separate threads
	    Future<int[]> sortedLeft = pool.submit(new SortTask(leftHalf));
	    Future<int[]> sortedRight = pool.submit(new SortTask(rightHalf));
	    Future<int[]> merged = pool.submit(new MergeTask(sortedLeft, sortedRight));
	    int[] results = null; // to hold results
	    
		try { // attempts to get array from Future object
			results = merged.get();
		} catch (InterruptedException | ExecutionException e) {

			e.printStackTrace();
		}
	    
	    return results;
	    
	}// end sort method
	
    //***************************************************************
    //
    //  Method:       printArray (Non Static)
    // 
    //  Description:  Helper method to print arrays of integers.
	//
    //  Parameters:   int[] (to be printed)
    //
    //  Returns:      N/A
    //
    //***************************************************************
	public void printArray(int[] arr) {
		
		System.out.print("[");
		if(arr != null) {
			for(int i : arr ) {
				System.out.print(" " + i + " ");
			}
		}
		System.out.print("]\n");
		
	} // end printArray method
	
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