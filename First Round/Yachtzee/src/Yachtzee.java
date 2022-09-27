import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class Yachtzee {
	
	public static void main(String[] args){
		try{
		int T=0;
		int[] Laundry;
		int[] NoOfWashMechine;
		long[] Dry;
		long[] DryTime;		
		long[] count;
		
		
		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter( new File("output.txt"));

		System.out.println("Started!");
		
		T=fr.nextInt();
		Laundry=new int[T];
		NoOfWashMechine=new int[T];
		Dry=new long[T];
		DryTime=new long[T];
		
		count=new long[T];
		Yachtzee b = new Yachtzee();
		int[] D;
		//Reading
		for(int i=0;i<T;i++){
			count[i]=0;			
			Laundry[i]=fr.nextInt();
			NoOfWashMechine[i]=fr.nextInt();
			Dry[i]=fr.nextInt();
			DryTime[i]=fr.nextInt();
			long[]WMachines=new long[NoOfWashMechine[i]];
			for(int j=0;j<NoOfWashMechine[i];j++){
				WMachines[j]=fr.nextInt();												
			}
			//Processing
			count[i]=b.getMinimumTime(Laundry[i],Dry[i],DryTime[i],WMachines);
			
			fw.write("Case #"+(i+1)+": "+count[i] +"\n");
			//System.out.println("Case #"+(i+1)+": "+count[i]);
			
		}		
		fw.close();							
		System.out.println("Completed");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private long getMinimumTime(int L,long D,long DT,long[] WM){
			
		//Sort
		for(int i=0;i<WM.length-1;i++){
			for(int j=i+1;j<WM.length;j++){
				if(WM[i]>WM[j]){
					long t=WM[i];
					WM[i]=WM[j];
					WM[j]=t;
				}
			}
		}
		
		//Processing
		//First value initial value
		long[] sum= new long[WM.length];
		int index=0;
		sum[index]=WM[index];
		
		long[] dry= new long[(int)D];
		int smallestIn=0;			
		dry[smallestIn]=sum[index]+DT;
		
		for(int i=1;i<L;i++){
			boolean found=false;
			for(int j=0;j<WM.length-1;j++){
				if((sum[j]+WM[j])<=(sum[j+1]+WM[j+1])){
					sum[j]+=WM[j];
					index=j;
					found=true;
					
					smallestIn=smallestValueIndex(dry);
					dry[smallestIn]=(dry[smallestIn]>=sum[j]?dry[smallestIn]+DT:sum[j]+DT);
					break;
				}
			}
			if(!found){
				sum[WM.length-1]+=WM[WM.length-1];
				index=WM.length-1;
				
				smallestIn=smallestValueIndex(dry);
				dry[smallestIn]=(dry[smallestIn]>=sum[WM.length-1]?dry[smallestIn]+DT:sum[WM.length-1]+DT);
			}
		}
		//sum[index]
		return dry[biggestValueIndex(dry)];
	}
	
	private int smallestValueIndex(long[] dry){
		int small=0;
		for(int i=1;i<dry.length;i++){
			if(dry[small]>dry[i]){
				small=i;
			}
		}
		return small;
	}
	
	private int biggestValueIndex(long[] dry){
		int small=0;
		for(int i=1;i<dry.length;i++){
			if(dry[small]<dry[i]){
				small=i;
			}
		}
		return small;
	}
}
