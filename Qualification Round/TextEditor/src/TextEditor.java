import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextEditor {

	public static void main(String[] args) throws IOException {
		int T = 0;
		int[] N, K;
		ArrayList<ArrayList<String>> allWords = new ArrayList<>();
		int count[];

		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter(new File("output.txt"));

		System.out.println("Started!");

		T = fr.nextInt();
		N = new int[T];
		K = new int[T];
		count = new int[T];
		// System.out.println(T);
		for (int i = 0; i < T; i++) {
			N[i] = fr.nextInt();
			// System.out.println(N[i]);
			K[i] = fr.nextInt();
			// System.out.println(K[i]);

			ArrayList<String> words = new ArrayList<String>();
			for (int j = 0; j < N[i]; j++) {
				words.add(fr.next());
				// System.out.println(words.get(j));
			}
			allWords.add(words);
		}

		// Process
		TextEditor tx = new TextEditor();
		// Sort every words list according to their size
		for (int i = 0; i < T; i++) {
			ArrayList<String> words = allWords.get(i);
			tx.sortList(allWords.get(i));
		}

		// Create Cost Table
		ArrayList<ArrayList<Data>> tables = new ArrayList<>();
		for (int i = 0; i < allWords.size(); i++) {
			ArrayList<Data> table = new ArrayList<>();
			for (int j = 0; j < allWords.get(i).size(); j++) {
				table.add(tx.createActionCostRow(allWords.get(i).get(j), j,
						allWords.get(i)));
			}
			tables.add(table);
		}

		// find counts of actions
		for (int i = 0; i < tables.size(); i++) {
			count[i] = tx.countActions(tables.get(i), K[i]);
		}

		//for (int i = 0; i < T; i++) {
		//	System.out.println((i + 1) + " " + count[i]);
		//}

		// write the output to file
		for (int i = 0; i < T; i++) {
			fw.write("Case #" + (i + 1) + ": " + count[i] + "\n");
		}
		fw.close();

		System.out.println("Completed");

	}

	private int countActions(ArrayList<Data> words, int limit) {
		boolean[] checked = new boolean[words.size()];
		int K = limit;
		int count = 0;
		// Find First string in the sequence		
		int index = 0;
		int lowestNo = words.get(index).cost[index];
		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < words.get(i).cost.length; j++) {
				if (i != j
						&& (words.get(i).cost[j] < lowestNo || lowestNo == 0)) {
					index = i;
					lowestNo = words.get(i).cost[j];
				}
			}
		}

		// First cost is the length of the first string
		count = words.get(index).word.length();
		--limit;
		checked[index]=true;

		while (limit > 0) {
			// Find Lowest cost of current string
			int lastIndex=index;
			lowestNo = words.get(index).cost[index];
			for (int j = 0; j < words.get(index).cost.length; j++) {
				if (index != j
						&& (words.get(index).cost[j] < lowestNo) && !checked[j]) {
					lastIndex = j;
					lowestNo = words.get(index).cost[j];
				}
			}
			
			//get the cost of the string
			count+=words.get(index).cost[lastIndex];
			
			//Change index to new string
			index=lastIndex;
			
			--limit;
			checked[index]=true;
		}

		// Last cost is the length of the last string
		//add K for print cost
		count += words.get(index).word.length()+K;

		return count;
	}

	
	private Data createActionCostRow(String word, int wordIndex,
			ArrayList<String> wordList) {
		int biggestNo = 0;

		Data d = new Data(word, new int[wordList.size()]);
		for (int i = 0; i < wordList.size(); i++) {
			if (i == wordIndex) {
				// Skip checking same word
				d.cost[i] = 0;
				continue;
			}

			boolean bothWordsEqual = true;
			if (word.length() <= wordList.get(i).length()) {
				// if word size is smaller or equal than the other word
				for (int j = 0; j < word.length(); j++) {
					if (word.charAt(j) != wordList.get(i).charAt(j)) {
						// check for character not matching
						d.cost[i] = (word.length() - j)
								+ (wordList.get(i).length() - j);

						biggestNo = biggestNo + d.cost[i];
						bothWordsEqual = false;
						break;
					}
				}
			} else {
				// if the other word is bigger
				for (int j = 0; j < wordList.get(i).length(); j++) {
					if (word.charAt(j) != wordList.get(i).charAt(j)) {
						// check for character not matching
						d.cost[i] = (word.length() - j)
								+ (wordList.get(i).length() - j);

						biggestNo = biggestNo + d.cost[i];
						bothWordsEqual = false;
						break;
					}
				}
			}

			if (bothWordsEqual) {
				d.cost[i] = Math
						.abs((wordList.get(i).length() - word.length()));
				biggestNo = biggestNo + d.cost[i];
			}
		}
		// Entering biggest number in unused same index cell
		d.cost[wordIndex] = biggestNo;

		return d;
	}

	private ArrayList<String> sortList(ArrayList<String> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).length() > list.get(j).length()) {
					String temp = list.remove(i);
					list.add(i, list.get(j - 1));
					list.remove(j);
					list.add(j, temp);
				}
			}
		}
		return list;
	}

	private class Data {
		public String word;
		public int[] cost;

		public Data(String word, int[] cost) {
			this.word = word;
			this.cost = cost;
		}

		public Data() {
		}

	}

}
