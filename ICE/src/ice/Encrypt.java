/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import java.io.*;
public class Encrypt
{
	//Reflection function
	public byte[] refxy(byte[] oba)
	{
		byte[] quad_temp=new byte[oba.length];
		int i;
		for(i=0;i<oba.length;i++)
		quad_temp[i]=oba[oba.length-1-i];//along X axis reflection
		for(i=0;i<oba.length;i++)
                {
                        oba[i]=quad_temp[i];
                }
		int quad_mid=(oba.length/2);
		if(oba.length%2==1)
		{
		for(i=0;i<quad_mid;i++)
		{
			quad_temp[i]=oba[quad_mid-1-i];
			quad_temp[i+quad_mid+1]=oba[oba.length-1-i];//along Y axis reflection
		}
		}
		else
		{
		for(i=0;i<quad_mid;i++)
		{
			quad_temp[i]=oba[quad_mid-1-i];
			quad_temp[i+quad_mid]=oba[oba.length-1-i];
		}
		}
		for(i=0;i<oba.length;i++)
			oba[i]=quad_temp[i];
		return(oba);
	}
	
      	
        //Sum function
	public int Ascii_val_poly(char[] k)
	{
	int sum=0;
	for(int j=0;j<k.length;j++)
	{ 
		 int t=(int)(k[j]);
 		 sum+=(j*power(t,j));
	}
	sum=(sum%k.length);
	if(sum<=0)sum=(sum+k.length);
	return (sum);
	}
	
	 //Power function
	public long power(int num,int p)
	{
	int temp;
	temp=num;
    	for(int t=0;t<p;t++)
	    temp=temp*num;
	return temp;
	}
	//Mod sub
	public byte[] sub_byte(byte[] oba,int sum_of_ascii)
	{
		
		byte[] quad_temp=new byte[oba.length];
		int temp_1=0,temp_2=0,temp_3=0;
		int temp_q=oba.length/sum_of_ascii;
		int temp_m=oba.length%sum_of_ascii;
		while(temp_1<oba.length)
		{
			int q=0,m=0;
			m=temp_m;
			temp_2=temp_3;
			while((temp_2<oba.length)&&(temp_1<oba.length))
			{
				if(m>0){q=temp_q+1;m=m-1;}
				else {q=temp_q;}
				quad_temp[temp_1]=oba[temp_2];
				//System.out.println(temp_1+" and "+temp_2);
				temp_1+=1;
				temp_2+=q;
				
				
			}
			
		 temp_3+=1;
			
		}
		for(int i=0;i<oba.length;i++)
			oba[i]=quad_temp[i];
		return(oba);
		
	}

public  String encrypt(String [] args)
{
	Encrypt test=new Encrypt();
	try
	{
	int i=0,j;
	//Scanner scanner=new Scanner(System.in); 
	
	//Original_Image_Computation
	String path_oimage=args[0];
	//System.out.println("Enter path of original img");
	//path_oimage=scanner.nextLine();
	File OriginalFile=new File(path_oimage);
    	byte [] oba=new byte[(int)OriginalFile.length()];
    	FileInputStream fino=new FileInputStream(OriginalFile);
 	BufferedInputStream bino=new BufferedInputStream(fino);
	bino.read(oba,0,oba.length);
	
	
	//Dummy_Image_Computation
	String path_dimage=args[1];
	//System.out.println("Enter path of cover img");
	//path_dimage=scanner.nextLine();
	File DummyFile=new File(path_dimage);
    	byte [] dba=new byte[(int)DummyFile.length()];
    	FileInputStream find=new FileInputStream(DummyFile);
 	BufferedInputStream bind=new BufferedInputStream(find);
	bind.read(dba,0,dba.length);

	
	//Server key+Key Image  computation
	FileReader fr = new FileReader(args[2]); 
	StringBuilder str=new StringBuilder();
	int k=0,key_length=0;
  	 
	while ((i=fr.read()) != -1) 
	{
	if((char)i!=' ')
	{
      	   str.append((char)i);
	}
	else
	{
	    int temp=Integer.parseInt(str.toString());
	    key_length=temp;
	    str.replace(0,str.length(),"");
	    break;
	}
	}
	double[] val=new double[key_length];
	int[] kba=new int[key_length];
    	while ((i=fr.read()) != -1) 
	{
	if((char)i!=' ')
	{
      	   str.append((char)i);
	}
	else
	{
	    double temp=Double.parseDouble(str.toString());
	    val[k]=temp;
	    kba[k]=(int)(3*(val[k]*val[k]));
	    //System.out.print(kba[k]+" ");
	    k=k+1;
	    str.replace(0,str.length(),"");
	}
	}
	System.out.println("");
        
	//User key computation
	String key_u;
	
	//System.out.println("Enter user key");
	//key_u=scanner.nextLine();
	
        key_u=args[3];
	int cons=2;
	//System.out.println("Constant");
	//cons=Integer.parseInt(scanner.nextLine());
	byte[] oba_n=new byte[cons*oba.length];
	for(i=0,j=0;j<oba.length;i=i+2,j=j+1)   
     	{
		
		int x,m;
		if(cons<0)cons=cons%128;
		else cons=cons%127;
		
		x=(int)key_u.charAt(j%key_u.length());
		if(x<0)x=x%128;
		else x=x%127;
		m=oba[j];
		m=(m*x)+((m+1)*cons);
		oba_n[i+1]=(byte)(m&0x00FF);
		oba_n[i]=(byte)((m&0xFF00)>>8);
		//int temp2=(int)(oba_n[i]<<8);
		//int temp1=(int)(oba_n[i+1]);	
	}
	oba=new byte[oba_n.length];
	for(i=0;i<oba_n.length;i++)  
     	{
		oba[i]=oba_n[i];
	}


	//Making cover
	byte[] rba=new byte[dba.length+oba.length];
	for(i=0;i<dba.length;i++)  
     	{
		rba[i]=dba[i];
	}

	
	//Third party key computation
	String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String key_p="";
	for (i = 0; i <key_u.length(); i++ ) {
        if (i==0)key_p=Character.toString(characters.charAt((int)Math.floor(Math.random() * characters.length())));
        else
        key_p +=characters.charAt((int)Math.floor(Math.random() * characters.length()));
        }
	//FileWriter tpw = new FileWriter("Temp.txt");
	//tpw.write(key_p);
	//tpw.close();
	int l_key_p=key_p.length();
	int[] sum_p=new int[l_key_p];
    	byte[][] k_p=new byte[l_key_p][];
	String[] key_p_temp=new String[l_key_p];
	for(i=0;i<key_p.length();i++)
	{
	str=new StringBuilder(key_p);
		for(j=0;j<key_p.length();j++)
		{
 		int temp=((int)key_p.charAt(i)*(int)key_p.charAt(j));
		temp=temp%256;
		char c=(char)temp;
		str.setCharAt(j,c);
		}
	key_p_temp[i]=str.toString();
	sum_p[i]=test.Ascii_val_poly(key_p_temp[i].toCharArray());
	}
	for(i=0;i<key_p.length();i++)
	{
	    k_p[i]=key_p_temp[i].getBytes();
	    
	}
	
	//Reflection
	test.refxy(oba);

	//Mod sustitution calculation
	int sum_of_ascii=0;
	for(i=0;i<key_p.length();i++)  
    	{
		sum_of_ascii=sum_p[i]+sum_of_ascii;
	}
	oba=test.sub_byte(oba,sum_of_ascii);
	
	//Encrypting kba and oba
	i=0;
	int a=0,l;
	while(true)  
    	{
	    j=0;
            l=sum_p[a];
	
	    while((j<sum_p[a])&&(i+l<oba.length)&&(i+j<oba.length))
	    {
		    rba[(i+j+dba.length)]=(byte)(oba[i+j]^kba[(i+l)%kba.length]);
		    j=j+1;
		    l=l-1;
	    }
       	  
	    if( i+sum_p[a]>=oba.length)break;
	    else i=i+sum_p[a];
	    a=(a+1)%l_key_p;
	}
	while(i<oba.length)
	{
		rba[(i+dba.length)]=(byte)(oba[i]^kba[i%kba.length]);i++;
	}
        String home = System.getProperty("user.home");
	FileOutputStream fos=new FileOutputStream(home+"/Downloads/"+"encrypted.jpg");
	fos.write(rba,0,rba.length);

	//fos=new FileOutputStream("encrypted_img.jpg");
	//fos.write(rba,dba.length,rba.length-dba.length);
         return key_p;
	}
	catch(Exception e)
	{
            e.printStackTrace();
            return(e.toString());
        }
	
}
}