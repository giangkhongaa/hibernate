package com.giang.paging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.core.convert.support.ConfigurableConversionService;

public class Page {
	private double size;
	private int currentPosition;

	public double getSize() {
		return size;
	}

	public Page(int allRecords, int maxRecordPage, int currentPosition) {
		setSize(allRecords, maxRecordPage);
		if (size != 0) {
			this.currentPosition = currentPosition;
		} else {
			this.currentPosition = 0;
		}
	}

	public Page() {
	}

	// Can set the size for page
	public void setSize(int allRecords, int maxRecordPage) {
		this.size = Math.ceil((double) allRecords / maxRecordPage);
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	// get the value of the next page
	public int getNextPage() {
		if (currentPosition < size) {
			return currentPosition + 1;
		} else if (currentPosition == size) {
			return currentPosition;
		} else {
			return (int) size;
		}
	}

	// get the value of the prev page
	public int getPrevPage() {
		if (currentPosition > 1) {
			return currentPosition - 1;
		} else if (currentPosition == 1) {
			return currentPosition;
		} else {
			return 1;
		}
	}

	// get a list of page
	public List<Integer> getNumberPageArray() {
		List<Integer> numberPageArray = new ArrayList<Integer>();

		int start = 0;
		int end = 0;
		if (size < 5 && size > 0) {
			start = 1;
			end = (int) size;
		} else if (currentPosition == 1 && size >= 5) {
			start = 1;
			end = 5;
		} else if (currentPosition == size && size >= 5) {
			start = (int) size - 5;
			end = (int) size;
		} else if ((currentPosition - 2 >= 1) && (currentPosition + 2 <= size)) {
			start = currentPosition - 2;
			end = currentPosition + 2;
		} else if (currentPosition == 2) {
			start = 1;
			end = currentPosition + 3;
		} else {
			end = (int) size;
			start = currentPosition - 3;
		}
		for (int i = start; i <= end && size >0; i++) {
			numberPageArray.add(i);
		}

		return numberPageArray;
	}

	public int getEndPage() {
		return (int) size;
	}

	public int getFirstPage() {
		return 1;
	}

}
