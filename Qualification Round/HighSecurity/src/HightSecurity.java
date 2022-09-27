import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HightSecurity {

	public static void main(String[] args) throws IOException {
		int T = 0;
		int[] N;
		char[][][] Grids;
		int count[];

		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter(new File("output.txt"));

		System.out.println("Started!");

		T = fr.nextInt();
		N = new int[T];
		count = new int[T];
		Grids = new char[T][][];
		for (int i = 0; i < T; i++) {
			N[i] = fr.nextInt();
			Grids[i] = new char[2][N[i]];
			// Read first row			
			Grids[i][0]= fr.next().toCharArray();
			// Read second row			
			Grids[i][1] = fr.next().toCharArray();		
		}

		HightSecurity hs = new HightSecurity();
		// Process
		for (int i = 0; i < T; i++) {
			 count[i]=hs.getGaurdCount(Grids[i]);
		}

		// write the output to file
		for (int i = 0; i < T; i++) {
			fw.write("Case #" + (i + 1) + ": " + count[i] + "\n");
		}
		fw.close();

		System.out.println("Completed");

	}

	private int getGaurdCount(char[][] faculty) {
		int count = 0;
		ArrayList<Data> row0 = new ArrayList<Data>();
		ArrayList<Data> row1 = new ArrayList<Data>();

		// Sort two rows separately
		int indexLimit0 = 0;
		int index0 = 0;
		int indexLimit1 = 0;
		int index1 = 0;
		for (int i = 0; i < faculty[0].length; i++) {
			// Finding adjescent dots for row 0
			if (faculty[0][i] == '.') {
				if (indexLimit0 == 0) {
					index0 = i;
				}
				indexLimit0++;

			} else {
				if (indexLimit0 > 0) {
					Data d = new Data(index0, indexLimit0);
					row0.add(d);

					indexLimit0 = 0;
				}
			}

			// Finding adjescent dots for row 1
			if (faculty[1][i] == '.') {
				if (indexLimit1 == 0) {
					index1 = i;
				}
				indexLimit1++;

			} else {
				if (indexLimit1 > 0) {
					Data d = new Data(index1, indexLimit1);
					row1.add(d);

					indexLimit1 = 0;
				}
			}
		}
		// Last one if any
		if (indexLimit0 > 0) {
			Data d = new Data(index0, indexLimit0);
			row0.add(d);
		}

		// Last one if any
		if (indexLimit1 > 0) {
			Data d = new Data(index1, indexLimit1);
			row1.add(d);
		}

		// Sorting
		row0 = sortList(row0);
		row1 = sortList(row1);

		// Processing
		while (!row0.isEmpty() || !row1.isEmpty()) {
			if (!row0.isEmpty() && !row1.isEmpty()) {
				if (row0.get(0).limit > row1.get(0).limit) {
					for (int i = row1.size() - 1; i >= 0; i--) {

						if ((row0.get(0).index + (row0.get(0).limit - 1)) >= (row1
								.get(i).index + (row1.get(i).limit - 1))
								&& (row0.get(0).index <= (row1.get(i).index + (row1
										.get(i).limit - 1)))) {
							// last Index is within
							row1.get(i).limit = row1.get(i).limit - 1;

							row0.remove(0);
							if (row1.get(i).limit == 0) {
								row1.remove(i);
							}else{
								// Sorting								
								row1 = sortList(row1);
							}
							// Increment Count
							count++;

							break;
						} else if ((row1.get(i).index >= row0.get(0).index)
								&& (row1.get(i).index <= (row0.get(0).index + (row0
										.get(0).limit - 1)))) {
							// First Index inside
							row1.get(i).index = row1.get(i).index + 1;
							row1.get(i).limit = row1.get(i).limit - 1;

							row0.remove(0);
							if (row1.get(i).limit == 0) {
								row1.remove(i);
							}else{
								// Sorting								
								row1 = sortList(row1);
							}
							// Increment Count
							count++;

							break;
						}
						
						if(i==0){
							//Remove loner
							row0.remove(0);
							// Increment Count
							count++;
						}
					}					
					
				} else {
					for (int i = row0.size() - 1; i >= 0; i--) {

						if ((row1.get(0).index + (row1.get(0).limit - 1)) >= (row0
								.get(i).index + (row0.get(i).limit - 1))
								&& (row1.get(0).index <= (row0.get(i).index + (row0
										.get(i).limit - 1)))) {
							// last Index is within
							row0.get(i).limit = row0.get(i).limit - 1;

							row1.remove(0);
							if (row0.get(i).limit == 0) {
								row0.remove(i);
							}else{
								// Sorting								
								row0 = sortList(row0);
							}
							// Increment Count
							count++;

							break;
						} else if ((row0.get(i).index >= row1.get(0).index)
								&& (row0.get(i).index <= (row1.get(0).index + (row1
										.get(0).limit - 1)))) {
							// First Index inside
							row0.get(i).index = row0.get(i).index + 1;
							row0.get(i).limit = row0.get(i).limit - 1;

							row1.remove(0);
							if (row0.get(i).limit == 0) {
								row0.remove(i);
							}else{
								// Sorting								
								row0 = sortList(row0);
							}
							// Increment Count
							count++;

							break;
						}
						
						if(i==0){
							//Remove loner
							row1.remove(0);
							// Increment Count
							count++;
						}
					}					
				}
			} else if (!row0.isEmpty()) {
				count=count+row0.size();
				row0.clear();
			} else if (!row1.isEmpty()) {
				count=count+row1.size();
				row1.clear();
			}
		}

		return count;
	}

	private ArrayList<Data> sortList(ArrayList<Data> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i+1; j < list.size(); j++) {
				if (list.get(i).limit < list.get(j).limit) {
					Data temp = list.remove(i);
					list.add(i, list.get(j-1));
					list.remove(j);
					list.add(j, temp);
				}
			}

		}
		return list;
	}

	private class Data {
		public Data(int index, int limit) {
			this.index = index;
			this.limit = limit;
		}

		public int index = 0;
		public int limit = 0;
	}
}
