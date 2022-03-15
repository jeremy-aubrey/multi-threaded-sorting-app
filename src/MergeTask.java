//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     MergeTask.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      03/13/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       4
//
//  Description:   A class that implements the Callable interface
//                 allowing values to be returned from its call method 
//                 which is executed in a seperate thread. This class
//                 accepts two int arrays and returns a single sorted 
//                 array of the elements from the provided arrays.
//
//*********************************************************************

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MergeTask implements Callable<int[]> {
	
	int[] merged; // to hold result
	int[] firstArray;
	int[] secondArray;
	
	// constructor 
	public MergeTask(Future<int[]> array1, Future<int[]> array2) {

		try {
			
			firstArray = array1.get().clone(); // copy array1 
			secondArray = array2.get().clone(); // copy array2 
			merged = new int[firstArray.length + secondArray.length]; // results array to be returned
			
		} catch (InterruptedException | ExecutionException e) {
			
			e.printStackTrace();
		}
	}// end constructor
	
    //***************************************************************
    //
    //  Method:       merge (Non Static)
    // 
    //  Description:  Iterates through left and right array and fills
    //                target array with elements (from left and right 
    //                array) in sorted order (selects smaller elements
    //                first).
	//
    //  Parameters:   int[] (result), int[] (source array 1), int[] (source array 2), int (array 1 len), int (array 2 len)
    //
    //  Returns:      N/A 
    //
    //***************************************************************
	public static void merge(
			  int[] targetArr, int[] leftArr, int[] rightArr, int leftArrLen, int rightArrLen) {
			 
			    int i = 0; // left array index
			    int j = 0; // right array index
			    int k = 0; // target array (merged result) index
			    
			    while (i < leftArrLen && j < rightArrLen) { // while left and right still both have elements to compare 
			        if (leftArr[i] <= rightArr[j]) { // if left element is smaller right
			        	targetArr[k++] = leftArr[i++]; // set as current element in target array, increment
			        }
			        else {
			        	targetArr[k++] = rightArr[j++]; // set right element as current element in target array, increment
			        }
			    }
			    
			    while (i < leftArrLen) { // right array finished, iterate through left array
			    	targetArr[k++] = leftArr[i++]; // set current left element as current in target array, increment
			    }
			   
			    while (j < rightArrLen) { // left array finished, iterate through right array
			    	targetArr[k++] = rightArr[j++]; // set current right element as current in target array, increment
			    }
			    
	}// end merge method
	
    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  Invokes merge method and returns a new sorted 
    //                integer array from the provided arrays.
    //
    //  Parameters:   None
    //
    //  Returns:      int[]
    //
    //**************************************************************
	@Override
	public int[] call() throws Exception {
		
		int firstArrayLength = firstArray.length;
		int secondArrayLength = secondArray.length;
		
		// merged is instantiated with 0's at this point, elements will be overwritten
		// with elements from firstArray and secondArray
		 merge(merged, firstArray, secondArray, firstArrayLength, secondArrayLength);
		
		return merged; // returns a new sorted int[] from arrays
		
	}// end call method

}// end MergeTask class