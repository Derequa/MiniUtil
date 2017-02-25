package derek.util;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

	@SuppressWarnings("unchecked")
	public static void sort(List<? extends Comparable> l) {
		ArrayList<Comparable> original = new ArrayList<Comparable>(l);
		sort((List<Comparable>) original, (List<Comparable>) l, 0, original.size() - 1);
	}
	
	private static void sort(List<Comparable> original, List<Comparable> ordered, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			sort(original, ordered, low, mid);
			sort(original, ordered, mid + 1, high);
			merge(original, ordered, low, mid, high);
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void merge(List<Comparable> original, List<Comparable> ordered, int low, int mid, int high) {
		for (int i = low ; i <= high ; i++)
			original.set(i, ordered.get(i));
		
		int iLow = low;
		int iHigh = mid + 1;
		int pos = low;
		for (; iLow <= mid && iHigh <= high ; pos++) {
			if (((Comparable) original.get(iLow)).compareTo(original.get(iHigh)) <= 0)
				ordered.set(pos, original.get(iLow++));
			else
				ordered.set(pos, original.get(iHigh++));
		}
		
		int remaining = mid - iLow;
		for (int i = 0; i <= remaining ; i++)
			ordered.set(pos + i, original.get(iLow + i));
		
	}
}
