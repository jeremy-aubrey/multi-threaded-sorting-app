import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class SortTask implements Callable <int[]> {
	
	int[] subList;
	int length;
	
	public SortTask(int[] unsorted) {
		
		this.subList = unsorted.clone();
		this.length = unsorted.length;
	}
	
	private void printArray(int[] array) {
		System.out.print("[");
		for(int i : array ) {
			System.out.print(" " + i + " ");
		}
		System.out.print("]\n");
	}
	
	public static void mergeSort(int[] arr, int length) {
		
	    if (length < 2) {
	        return;
	    }
	    int mid = length / 2;
	    int[] left = new int[mid];
	    int[] right = new int[length - mid];

	    for (int i = 0; i < mid; i++) {
	        left[i] = arr[i];
	    }
	    
	    for (int i = mid; i < length; i++) {
	        right[i - mid] = arr[i];
	    }
	    
	    mergeSort(left, mid);
	    mergeSort(right, length - mid);

	    merge(arr, left, right, mid, length - mid);
	}
	
	public static void merge(
			  int[] result, int[] leftArr, int[] rightArr, int left, int right) {
			 
			    int i = 0, j = 0, k = 0;
			    while (i < left && j < right) {
			        if (leftArr[i] <= rightArr[j]) {
			        	result[k++] = leftArr[i++];
			        }
			        else {
			        	result[k++] = rightArr[j++];
			        }
			    }
			    while (i < left) {
			    	result[k++] = leftArr[i++];
			    }
			    while (j < right) {
			    	result[k++] = rightArr[j++];
			    }
	}

	@Override
	public int[] call() throws Exception {
		
		mergeSort(subList, length);
		
		return subList;
	}
 	
}
