import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Boomerang {
	
	public static void main(String[] args){
		try{
		int T=0;
		int[]N;
		
		
		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter( new File("output.txt"));

		System.out.println("Started!");
		
		T=fr.nextInt();
		N= new int[T];
		
		Boomerang b = new Boomerang();
		//Reading
		for(int i=0;i<T;i++){
			N[i]=fr.nextInt();
			int[][]x = new int[N[i]][N[i]]; 
			for(int j=0;j<N[i];j++){
				for(int k=0;k<N[i];k++){
					x[j][k]=fr.nextInt();
				}												
			}
			//Processing
			b.traverseInAlPossibleWay(x);
			
			//fw.write("Case #"+(i+1)+": "+count[i] +"\n");
			//System.out.println("Case #"+(i+1)+": "+count[i]);
			
		}		
		fw.close();							
		System.out.println("Completed");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private void traverseInAlPossibleWay(int[][]x){
		
		
	}	
	
	private void traverse(int size){
		
		if(size==1){
			
			System.out.print(0+" ");
			return;
		}
		
		for(int i=0;i<size;i++){			
			traverse(size-1);			
		}
	}
}
