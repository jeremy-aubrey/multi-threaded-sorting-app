import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MergeTask implements Callable<int[]> {
	
	int[] merged;
	int[] firstArray;
	int[] secondArray;
	
	public MergeTask(Future<int[]> array1, Future<int[]> array2) {
		

		try {
			
			firstArray = array1.get().clone();
			secondArray = array2.get().clone();
			merged = new int[firstArray.length + secondArray.length];
			
		} catch (InterruptedException | ExecutionException e) {
			
			e.printStackTrace();
		}
		
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
		
		 merge(merged, firstArray, secondArray, firstArray.length, secondArray.length);
		
		return merged;
	}

}
