package com.iwander.iwander;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.Scanner;


public class keith {
	static double avg;
	
	keith( double x1,double y1){
		 double x,y;
		 x= x1;
		 y=y1;
	
				
		 Scanner sc = new Scanner(System.in);
			//System.out.println("Enter age");
			// x = sc.nextDouble();
			double sd=0;
			if (x<=50)
			{ sd=(50-x)/50;
			}
			double md=0;
			if ((x>0) && (x<=100))
			{
				if (x<=50)
			md=x/50;
			else
			md=(100-x)/50;
			}
		
			double  ld= 0;
			 if ((x>50) && (x<=100))
			    ld=(x-50)/50;
			 
			
			// System.out.println(" Enter time of day as 24 hour clock");
			// y = sc.nextDouble();
			
			double sg=0;
			if (y<=50)
			  sg=(50-y)/50;
			
			double mg=0;
			if ((y>0) && (y<=100))
			if (y<=50)
			mg=( y)/50;
			else
			mg=(100-y)/50;
			
			double lg= 0;
			 if ((y>50) && (y<=100))
			    lg=(y-50)/50;
			
			 //strength of rules
			 double vst,m,l,s,m1,l1,l2,v1,m2;
			 vst = Math.min(sd,sg);
			 m=Math.min(sd,mg);
			 l=Math.min(sd,lg);
			 s=Math.min(md,sg);
			 m1=Math.min(md,mg);
			 l1=Math.min(md,lg);
			 m2=Math.min(ld,sg);
			 l2=Math.min(ld,mg);
			 v1=Math.min(ld,lg);
			double temp[]={vst,m,l,s,m1,l1,m2,l2,v1};
			double max=0;
			for(int i = 0; i < temp.length; i++){
				if(i == 0)
				max = i;
				else{
				if(temp[i] > max)
				max = temp[i]; 
				}
				}
			double z = max;
			 System.out.println(max);
			 
			 double res=0 ,res1=0;
			 
			 
			 if(z==m || z==m1)
			    
			 {   res=15*z+10;
			   res1=-15*z+40;
			 System.out.println(res);
			 System.out.println(res1);
			     
			   
			 }
			 
			  if(z==l || z==l1 || z==l2)
			     
			  {    res=15*z+25;
			     res1=-20*z+60;
			     System.out.println(res);
			    System.out.println(res1);
			     
			   
		}
			if(z==v1)
			{   res=20*z+40;
			      System.out.println(res);
			  
			}
			 
			if(z==vst)
				{  res=-10*z+10;
			      System.out.println(res);
			   
				}
			 
			if(z==s)
			{  res=10*z;
			  res1=-15*z+25;
			     System.out.println(res);
			    System.out.println(res1);
			}
			 
			 avg=res;
			 if(res1!=0)
			avg=(res+res1)/2;
			  System.out.println("wander probality");
			  System.out.println(avg);
			 
		
	}
	public static void main(String args[])
	{
		
		  

		
	}
	double average(){
		
		return avg;
	}

}
