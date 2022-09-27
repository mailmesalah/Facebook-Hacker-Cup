import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class BoomerangV1 {

	public static void main(String[] args) throws IOException {
		int T=0;
		int[] N= new int[50];
		int[] count = new int[50];
		int[] index = new int [51];
		int[] X= new int[100000];
		int[] Y= new int[100000];
		int i=0,j,k,l;
		int pro1,pro2;
		double root1,root2;
		
		Scanner fr = new Scanner(new File("input.txt"));
		FileWriter fw = new FileWriter( new File("output.txt"));

		System.out.println("Started!");
		
		T=fr.nextInt();
		index[0]=0;
		for(;i<T;i++){
			count[i]=0;			
			N[i]=fr.nextInt();
			for(j=0;j<N[i];j++){				
				X[index[i]+j]=fr.nextInt();
				Y[index[i]+j]=fr.nextInt();						
			}
			index[i+1]=index[i]+N[i];
		}
		
		System.out.println("Started and nights are "+T);
		for(i=0;i<T;i++){		
			for(j=0;j<N[i];j++){
				for(k=0;k<N[i]-1;k++){
					if(k!=j){
						for(l=k;l<N[i];l++){
							if(l!=j && l!=k){
								//finding Distance between first two points from the three points
								pro1=X[index[i]+j]-X[index[i]+k];
								pro1=pro1*pro1;
								pro2=Y[index[i]+j]-Y[index[i]+k];
								pro2=pro2*pro2;
								root1=Math.sqrt(pro1+pro2);
								//finding Distance of the second
								pro1=X[index[i]+j]-X[index[i]+l];
								pro1=pro1*pro1;
								pro2=Y[index[i]+j]-Y[index[i]+l];
								pro2=pro2*pro2;
								root2=Math.sqrt(pro1+pro2);
								
								if(root1==root2){
									count[i]++;
									//System.out.println(i+" night, Matching Points are ("+X[index[i]+k]+" "+Y[index[i]+k]+"-"+X[index[i]+j]+" "+Y[index[i]+j]+") and ("+X[index[i]+j]+" "+Y[index[i]+j]+"-"+X[index[i]+l]+" "+Y[index[i]+l]+")\n");
								}
							}			
						}
					}
				}
			}		

		}
		
		//write the output to file
		for(i=0;i<T;i++){
			fw.write("Case #"+(i+1)+": "+count[i] +"\n");
		}
		fw.close();
		
		System.out.println("Completed");

	}

}
