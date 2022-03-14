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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		ExecutorService pool = Executors.newFixedThreadPool(2); // sorting threads
		int count; // number of integers to process
		
		/* Continue to perform calculations until 
		 * the user enters a 0 to quit.*/
		do {
			count = obj.getCount();
			System.out.println("RECIEVED: " + count);
			List<Integer> intList = obj.getRandomIntList(count);
			System.out.println(intList);
			
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
					System.out.println("Number must be greater than 0");
				}
				
			} catch (NumberFormatException e) {
				
				System.out.println("Must enter a number");
			}
		}
		
		return count;
	
	}//end getCount method
	
	public List<Integer> getRandomIntList(int count) {
		
		List<Integer> randomInts = new ArrayList<Integer>();
		if(count > 0) {
			
			for(int i = 0; i < count; i++) {
				randomInts.add(getRandomInt());
			}
		}
		
		return randomInts;

	}
	
	public int getRandomInt() {
		
		return (int) (Math.random() * 100);
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
