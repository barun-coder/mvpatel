package com.viscus.explore;

public class ArrayUtil {
	public static void main(String[] args) {
		ArrayUtil arrayUtil = new ArrayUtil();
		int temp1[] = { 1, 4, 3, 2 };
		int temp2[] = { 11, 14, 13, 12 };
		int temp[] = arrayUtil.arrayCopy(temp1, temp2, 0, 10);
		arrayUtil.pirntArray(temp1);
		arrayUtil.pirntArray(temp2);
		arrayUtil.pirntArray(temp);
	}

	private int[] arrayCopy(int[] toArray, int[] fromArray, int currentPage, int pageSize) {
		if (null == fromArray || fromArray.length < 1) {
			return toArray;
		}

		if (null == toArray || toArray.length < 1) {
			return fromArray;
		}

		if (currentPage == 0) {
			int temp[] = new int[toArray.length + fromArray.length];
			for (int i = 0; i < toArray.length; i++) {
				temp[i] = toArray[i];
			}
			for (int i = 0; i < fromArray.length; i++) {
				temp[toArray.length + i] = fromArray[i];
			}
			return temp;
		} else {
			if(toArray.length >= fromArray.length+(currentPage*pageSize));
		}

		return null;
	}

	private void pirntArray(int[] arrayToPrint) {
		if (null != arrayToPrint) {
			System.out.println("\n-------------- Printing Array --------------");
			for (int i = 0; i < arrayToPrint.length; i++) {
				System.out.print(arrayToPrint[i] + ", ");
			}
		}
	}
}
