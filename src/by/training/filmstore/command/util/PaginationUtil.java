package by.training.filmstore.command.util;

import java.util.ArrayList;
import java.util.List;

public final class PaginationUtil {
	
	public static List<Integer> makeListIndex(int page, int startIndex, int countPages, int countRefPerPage) {
		List<Integer> listIndexs = new ArrayList<>(countRefPerPage);

		if (page != 1) {
			int middle = startIndex + countRefPerPage / 2;
			if (page >= middle) {

				if ((startIndex + countRefPerPage - 1) >= countPages) {
					fillListIndex(startIndex, countRefPerPage, countPages, listIndexs);
				} else {
					startIndex = startIndex + countRefPerPage / 2;
					fillListIndex(startIndex, countRefPerPage, countPages, listIndexs);
				}
			} else {
				fillListIndex(startIndex, countRefPerPage, countPages, listIndexs);
			}
		} else {
			startIndex = 1;
			fillListIndex(startIndex, countRefPerPage, countPages, listIndexs);
		}
		return listIndexs;
	}

	private static void fillListIndex(int startIndex, int countRefPerPage, int countPages, List<Integer> listIndexs) {
		for (int i = 0; i < countRefPerPage; i++) {
			if ((startIndex + i) > countPages) {
				break;
			}
			listIndexs.add(startIndex + i);
		}
	}
}
