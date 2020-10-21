/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;
import java.io.*;
import java.util.*;

public class Decrypt
{
	//Reflection function
	public byte[] refxy(byte[] oba)
	{
		byte[] quad_temp=new byte[oba.length];
		int i;
		for(i=0;i<oba.length;i++)
		quad_temp[i]=oba[oba.length-1-i];//along X axis reflection
		for(i=0;i<oba.length;i++)
			oba[i]=quad_temp[i];
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
		int temp_1=0,temp_3=0,temp_2=0;
		int temp_q=sum_of_ascii;
		while(temp_1<oba.length)
		{
		temp_2=0;	
		while((temp_2<oba.length)&&(temp_3<temp_q))
		{
			if((temp_2%temp_q)==temp_3)
				{
				quad_temp[temp_1]=oba[temp_2];
				//System.out.println(temp_1+" and "+temp_2);
				temp_1+=1;
				}
			temp_2+=1;
		}
		temp_3+=1;
		}
		for(int i=0;i<oba.length;i++)
			oba[i]=quad_temp[i];
		return(oba);
	}
public  String decrypt(String [] args)
{
	Decrypt test=new Decrypt();
	try
	{
	int i=0,j=0;
	//Scanner scanner=new Scanner(System.in);
	//Encrypted_Image_Computation
	//String path_rimage="encrypted.jpg";
	//System.out.println("Enter path of result img");
	//path_rimage=scanner.nextLine();
	//File ResultFile=new File(path_rimage);
        File ResultFile=new File(args[0]);
        byte [] rba=new byte[(int)ResultFile.length()];
        FileInputStream finr=new FileInputStream(ResultFile);
 	BufferedInputStream binr=new BufferedInputStream(finr);
	binr.read(rba,0,rba.length);
	
	//Dummy_Image_Computation
	//String path_dimage="logo.jpg";
	//System.out.println("Enter path of cover img");
	//path_dimage=scanner.nextLine();
	//File DummyFile=new File(path_dimage);
        File DummyFile=new File(args[1]);
    	byte [] dba=new byte[(int)DummyFile.length()];
    	FileInputStream find=new FileInputStream(DummyFile);
 	BufferedInputStream bind=new BufferedInputStream(find);
	bind.read(dba,0,dba.length);
	
	//Key_Image_Computation
	//String path_kimage="key.jpg";
	//System.out.println("Enter path of key img");
	//path_kimage=scanner.nextLine();
	File KeyFile=new File(args[2]);
    	byte [] val_x=new byte[(int)KeyFile.length()];
    	FileInputStream fink=new FileInputStream(KeyFile);
 	BufferedInputStream bink=new BufferedInputStream(fink);
	bink.read(val_x,0,val_x.length);
	int[] kba=new int[val_x.length];
	double[] val=new double[val_x.length];
	//Server key computation
	for(i=0;i<val_x.length;i++)
	{
	  double c=(double)val_x[i];
	  val[i]=c;
	}
	for(i=0;i<val_x.length;i++)
	{
		kba[i]=(int)(3*(Math.cbrt(val[i]*val[i])));
		//System.out.print(kba[i]+" ");
	}
        System.out.println("");
	//Third party key computation
	String key_p=args[4];
	//System.out.println("Enter third party key");
	//key_p=scanner.nextLine();
	int l_key_p=key_p.length();
	int[] sum_p=new int[l_key_p];
    	byte[][] k_p=new byte[l_key_p][];
	String[] key_p_temp=new String[l_key_p];
	for(i=0;i<key_p.length();i++)
	{
	StringBuilder str=new StringBuilder(key_p);
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

	byte[] oba=new byte[rba.length-dba.length];
	//Decrypting kba and oba
  	i=0;
	int a=0,l=0;
	while(true)  
    	{
	    j=0;
            l=sum_p[a];
	
	    while((j<sum_p[a])&&(i+l<oba.length)&&(i+j<oba.length))
	    {
		  oba[i+j]=(byte)( rba[(i+j+dba.length)]^kba[(i+l)%kba.length]);
		    j=j+1;
		    l=l-1;
	    }
       	  
	    if( i+sum_p[a]>=oba.length)break;
	    else i=i+sum_p[a];
	    a=(a+1)%l_key_p;
	}
	while(i<oba.length)
	{
		oba[i]=(byte)(rba[(i+dba.length)]^kba[i%kba.length]);i++;
	}

	
	//Mod sustitution calculation
	int sum_of_ascii=0;
	for(i=0;i<key_p.length();i++)  
    	{
		sum_of_ascii=sum_p[i]+sum_of_ascii;
	}
	oba=test.sub_byte(oba,sum_of_ascii);

	//Inverse reflection
	test.refxy(oba);
	
	//User key computation
	String key_u=args[3];
	//System.out.println("Enter user key");
	//key_u=scanner.nextLine();
	int cons=2;
	//System.out.println("Constant");
	//cons=Integer.parseInt(scanner.nextLine());
	byte[] eba=new byte[(oba.length/2)];

	for(i=0,j=0;i<oba.length;i=i+2,j=j+1)  
     	{
		int x,m;
		if(cons<0)cons=cons%128;
		else cons=cons%127;
		x=(int)key_u.charAt(j%key_u.length());
		if(x<0)x=x%128;
		else x=x%127;
		m=(int)((oba[i]<<8)|(oba[i+1]&0x00FF));		
		m=m-cons;
		int t=x+cons;
		m=m/t;
		eba[j]=(byte)m;
	}
	String home = System.getProperty("user.home");
	FileOutputStream fos=new FileOutputStream(home+"/Downloads/"+"decrypted.jpg");
	fos.write(eba,0,eba.length);
        return("Success");
	}
	catch(Exception e)
	{
           return( e.toString());
        }
	
	
}
}
