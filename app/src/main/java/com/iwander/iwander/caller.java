package com.iwander.iwander;

import java.io.IOException;


public class caller {
	public  static void main(String args[]) {
		int resofAlgo1=0,resofAlgo2=0;
		double age= 70;               //your input
		double timeofday=22;            // your input (select only current hour as system input)
		 if(timeofday>=22 )//scale time
			 timeofday=80;
			 if(timeofday<=6 )
				 timeofday=90;
		System.out.println("start");
		keith k = new keith(age,timeofday);
		double avg= k.average();
	
		
		
		
		double temperature,levelofdementia;
		temperature=40;                 //your input in degree celsius
		temperature=temperature*2;
		levelofdementia=4;            //your input on scale of 1-4
		levelofdementia = levelofdementia*10*2;
		 k = new keith(temperature,levelofdementia);
			double avg1= k.average();
			temperature=temperature/2;//scale back for next algo
			
		if(((avg1+avg)/2)>40)
		{
			System.out.println("send warning message");//your Toast this message
			resofAlgo1=1;// 
		}
		
		//naive bayes
	 String age1="",inc1="",time1="",lod1="";
	 if(age<=30)
		 age1="<=30";
	 if(age>30 && age<45)
		 age1="31-45";
	 if(age>=45 && age<=60)
		 age1="45-60";
	 if(age>60)
		 age1=">=60";
	 
	 if(temperature<=20)
		 inc1="10";
	 if(temperature>10 && temperature<=20)
		 inc1="20";
	 if(temperature>20 && temperature<=25)
		 inc1="25";
	 if(temperature>25 && temperature<=30)
		 inc1="30";
	 if(temperature>30 && temperature<=90)
		 inc1="40";
	 
	 if(timeofday>=22)
		 time1="n";
	 if(timeofday<=7)
		 time1="n";
	 if(timeofday >7 && timeofday<22)
		 time1="d";
	 
	if( levelofdementia ==20)
		
		lod1="1";
if( levelofdementia ==40)
		
		lod1="2";
if( levelofdementia ==60)
	
	lod1="3";
if( levelofdementia ==80)
	
	lod1="4";


	
	 
	 
	 
		 
	 
		Naive27 n = new Naive27(age1,inc1,time1,lod1);
		String[] args1={};
		try{
		n.main(args1);
		}
		
		catch(IOException e){}
		String result2 = n.display();
		System.out.println(result2);
		if(result2.equals("yes"))
				{
			
			System.out.println("send 2nd warning message");//your Toast this message
			resofAlgo2=1;// 
				}
		if(resofAlgo1==1 && resofAlgo2==1)
			System.out.println("you r warned twicee!!");// your Toast this message
		
	}

}
