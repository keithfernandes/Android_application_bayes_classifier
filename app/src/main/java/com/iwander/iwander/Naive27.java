package com.iwander.iwander;

import java.io.*;
import java.util.*;
public class Naive27
{
	static String age1,inc1,time1,wander1,lod1;
	static  double pyes,pno;
	Naive27(String age,String inc,String time,String lod)
	{
		
		age1= age;
		inc1=inc;
		time1=time;
		lod1=lod;
		
	}
    public static void main(String args[])throws IOException
    {
    	
         int i,j;
         Scanner sc=new Scanner(System.in);
         
         String age[]={"<=30","<=30","<=30","31-45","31-45","31-45","45-60","45-60","45-60","45-60","45-60","45-60",">=60",">=60",">=60",">=60",">=60",">=60",">=60"};
         String temperature[]={"40","20","10","40","30","25","10","30","25","20","40","30","10","30","40","10","20","30","40"};
         String time[]={"d","n","n","d","d","d","d","n","d","n","n","n","d","d","d","n","n","n","n"};
         String lod[]={"1","3","4","1","3","4","1","1","2","3","3","4","1","2","3","1","2","3","4"};
         String wander[]={"no","yes","yes","no","yes","yes","no","no","no","yes","yes","yes","no","yes","yes","yes","yes","yes","yes"};
         
         System.out.println("Given Data Set is: ");
         System.out.println("age   temperature   time   lod		wander");

       	 for ( i=0;i<19;i++)
         System.out.println(age[i]+"\t"+temperature[i]+"\t"+time[i]+"\t"+lod[i]+"\t"+wander[i]);

         System.out.println();
         
        /* //String age1,inc1,time1,wander1,lod1;
         System.out.println("Enter the data to be sampled");
         System.out.print("Age= ");
         age1=sc.next();
         System.out.print("\ntemperature= ");
         inc1=sc.next();
         System.out.print("\ntime= ");
         time1=sc.next();
         System.out.print("\nlod= ");
         lod1=sc.next();
         
        */
         double n=0,y=0;
         for(i=0;i<19;i++)
         {
             if(wander[i]=="yes")
             y++;
         else n++;
		}
         
        
         System.out.println("P(wander=yes) "+y+"/19");
         System.out.println("P(wander=no) "+n+"/19");
          System.out.println();
         System.out.println("The data to be sampled is");
         System.out.println("X=(age "+age1+" temperature= "+inc1+" time= "+time1+"lod"+lod1+")");
         
         double agey=0,agen=0,incy=0,incn=0,timey=0,timen=0,lody=0,lodn=0;
         for(j=0;j<19;j++)
         {
             if(age[j].contentEquals(age1))
             {
                 if(wander[j].contentEquals("yes"))
                     agey++;
                 else
                     agen++;
             }
         }
         
         System.out.println("P(age|yes)="+agey+"/"+y);
         System.out.println("P(age|no)="+agen+"/"+n);
         
          System.out.println();
         for(j=0;j<19;j++)
         {
             if(temperature[j].contentEquals(inc1))
             {
                 if(wander[j].contentEquals("yes"))
                     incy++;
                 else
                     incn++;
             }
         }
         
         System.out.println("P(income|yes)="+incy+"/"+y);
         System.out.println("P(income|no)="+incn+"/"+n);
          System.out.println();
          for(j=0;j<19;j++)
         {
             if(time[j].contentEquals(time1))
             {
                 if(wander[j].contentEquals("yes"))
                     timey++;
                 else
                     timen++;
             }
         }
         
         System.out.println("P(student|yes)="+timey+"/"+y);
         System.out.println("P(student|no)="+timen+"/"+n);
          System.out.println();
          
          for(j=0;j<19;j++)
          {
              if(lod[j].contentEquals(lod1))
              {
                  if(wander[j].contentEquals("yes"))
                      lody++;
                  else
                      lodn++;
              }
          }
          
          System.out.println("P(student|yes)="+timey+"/"+y);
          System.out.println("P(student|no)="+timen+"/"+n);
           System.out.println();
           
         
        
         pyes=(incy/y)*(timey/y)*(agey/y)*(y/19);
         pno=(incn/n)*(timen/n)*(agen/n)*(n/19);
         
         System.out.println("P(h|D)yes="+pyes);
         System.out.println("P(h|D)no="+pno);
          System.out.println();
         if(pyes>pno)
	System.out.println("data is sampled to buy YES");
          else
	System.out.println("data is sampled to buy NO");	
    }
   String display()
   {
	   if(pyes>pno)
		   return "yes";
			//System.out.println("data is sampled to buy YES");
		          else
		        	  return "no" ;	  
			//System.out.println("data is sampled to buy NO");	
   }
}

/*output:

Given Data Set is: 
age   temperature   time   lod		wander
<=30	40	d	1	no
<=30	20	n	3	yes
<=30	10	n	4	yes
31-45	40	d	1	no
31-45	30	d	3	yes
31-45	25	d	4	yes
45-60	10	d	1	no
45-60	30	n	1	no
45-60	25	d	2	no
45-60	20	n	3	yes
45-60	40	n	3	yes
45-60	30	n	4	yes
>=60	10	d	1	no
>=60	30	d	2	yes
>=60	40	d	3	yes
>=60	10	n	1	yes
>=60	20	n	2	yes
>=60	30	n	3	yes
>=60	40	n	4	yes

Enter the data to be sampled
Age= <=30

temperature= 20

time= n

lod= 3
P(wander=yes) 13.0/19
P(wander=no) 6.0/19

The data to be sampled is
X=(age <=30 income= 20 student= n)
P(age|yes)=2.0/13.0
P(age|no)=1.0/6.0

P(income|yes)=3.0/13.0
P(income|no)=0.0/6.0

P(student|yes)=9.0/13.0
P(student|no)=1.0/6.0

P(student|yes)=9.0/13.0
P(student|no)=1.0/6.0

P(h|D)yes=0.016817190906259733
P(h|D)no=0.0

data is sampled to buy YES

*/