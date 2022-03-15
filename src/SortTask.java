//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     SortTask.java
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
//                 accepts an int array, performs merge sort, and returns 
//                 a new sorted int array.
//
//*********************************************************************

import java.util.concurrent.Callable;

public class SortTask implements Callable <int[]> {
	
	int[] subList;
	int length;
	
	// constructor
	public SortTask(int[] unsorted) {
		
		this.subList = unsorted.clone(); // copy array 
		this.length = unsorted.length;
		
	}// end constructor
	
    //***************************************************************
    //
    //  Method:       mergeSort (Non Static)
    // 
    //  Description:  Splits array in half recursively and invokes merge
    //                to combine each split result in sorted order.
	//
    //  Parameters:   int[] (array to be sorted), int (array length)
    //
    //  Returns:      N/A 
    //
    //***************************************************************
	public static void mergeSort(int[] targetArr, int length) {
		
	    if (length < 2) {
	        return; // return if no sorting necessary
	    }
	    
	    // create 2 sub arrays
	    int mid = length / 2;
	    int[] leftHalf = new int[mid];
	    int[] rightHalf = new int[length - mid];

	    // populate left sub array
	    for (int i = 0; i < mid; i++) {
	    	leftHalf[i] = targetArr[i];
	    }
	    
	    // populate right sub array
	    for (int i = mid; i < length; i++) {
	    	rightHalf[i - mid] = targetArr[i];
	    }
	    
	    mergeSort(leftHalf, mid); // recurse with left sub array, pass new length
	    mergeSort(rightHalf, length - mid); // recurse with right sub array, pass new length

	    merge(targetArr, leftHalf, rightHalf, mid, length - mid); // merge left and right in sorted order
	    
	}// end mergeSort method
	
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
	public static void merge(int[] targetArr, int[] leftArr, int[] rightArr, int leftArrLen, int rightArrLen) {
	 
		int i = 0; // left array index
	    int j = 0; // right array index
	    int k = 0; // target array index
	    
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
    //  Description:  Invokes mergeSort and returns the new, sorted array.
    //
    //  Parameters:   None
    //
    //  Returns:      int[]
    //
    //**************************************************************
	@Override
	public int[] call() throws Exception {
		
		mergeSort(subList, length); 
		
		return subList; // returns a sorted copy of the original array
		
	}// end call method
 	
}// end SortTask class