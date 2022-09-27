import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Boomerang {

	public class PlaceHolder{
		int x;
		int y;
		double[] distance;
		
		public PlaceHolder(int x, int y, double[] distance) {
			super();
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
			
	}
	
	public static void main(String[] args){
		try{
		int T=0;
		int[] N;
		int[] count;
		
		
		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter( new File("output.txt"));

		System.out.println("Started!");
		
		T=fr.nextInt();
		N=new int[T];
		count=new int[T];
		Boomerang b = new Boomerang();
		
		//Reading
		for(int i=0;i<T;i++){
			count[i]=0;			
			N[i]=fr.nextInt();
			ArrayList<PlaceHolder> stars = new ArrayList<>();
			for(int j=0;j<N[i];j++){
				int x=fr.nextInt();
				int y=fr.nextInt();
				stars.add(b.new PlaceHolder(x, y, new double[N[i]]));									
			}
			//Processing
			b.createDistanceRow(stars);
			count[i]=b.countBoomerang(stars);
			fw.write("Case #"+(i+1)+": "+count[i] +"\n");
		}		
		fw.close();							
		System.out.println("Completed");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private int countBoomerang(ArrayList<PlaceHolder> stars){
		int count=0;
		for(int i=0;i<stars.size();i++){
			for(int j=0;j<stars.size();j++){
				for(int k=j+1;k<stars.size();k++){
					if(i!=j && j!=k && k!=i && (stars.get(i).distance[j]==stars.get(i).distance[k])){
						++count;
					}
				}				
			}
		}
		
		return count;
	}
	
		
	private void createDistanceRow(ArrayList<PlaceHolder> stars){
		for(int i=0;i<stars.size();i++){
			for(int j=0;j<stars.size();j++){
				if(i!=j){
					int pro1=stars.get(i).x-stars.get(j).x;
					pro1=pro1*pro1;
					int pro2=stars.get(i).y-stars.get(j).y;
					pro2=pro2*pro2;					
					stars.get(i).distance[j]=Math.sqrt(pro1+pro2);
				}else{
					stars.get(i).distance[j]=0;
				}
			}
		}
	}
	
	

}
