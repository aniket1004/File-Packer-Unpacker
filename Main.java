import java.lang.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Login
{
	static int count = 3;
	JFrame f;
	public Login()
	{
		f = new JFrame("Packer-Unpacker");
		JButton submit = new JButton("Submit");
		JTextField userText = new JTextField();
		JPasswordField passText = new JPasswordField(15);
		JLabel userName = new JLabel("UserName");
		JLabel passWord = new JLabel("Password");
		JLabel warning = new JLabel(count+" attempt to login");
		ImageIcon img = new ImageIcon("images/loginimg.jpg");
		JLabel im = new JLabel(img);

		f = new JFrame("Packer-Unpacker");
		f.setSize(500,500);

		userName.setBounds(100,120,100,40);
		passWord.setBounds(100,200,100,40);
		userText.setBounds(300,120,150,30);
		passText.setBounds(300,200,150,30);
		warning.setBounds(100,250,250,40);
		submit.setBounds(100,300,100,40);
		im.setBounds(0,0,500,500);

		f.add(im);
		im.add(userName);
		im.add(passWord);
		im.add(userText);
		im.add(passText);
		im.add(warning);
		im.add(submit);

		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String user = userText.getText();
				String pass = passText.getText();
				if(user.equals("Aniket") && pass.equals("Aniket@123"))
				{
					f.setVisible(false);
					new PackUnpack();
				}
				else if(count == 1)
				{
					f.setVisible(false);
				}
				else
				{
					count--;
					f.setVisible(false);
					new Login();	
				}
			}
		});
	}
}
class PackUnpack
{
	public PackUnpack()
	{
		JFrame f = new JFrame("Packer-Unpacker");
		JButton pb = new JButton("Pack");
		JButton ub = new JButton("UnPack");
		JButton bb = new JButton("Back");
		ImageIcon img = new ImageIcon("images/loginimg.jpg");
		JLabel im = new JLabel(img);


		pb.setBounds(200,100,150,30);
		ub.setBounds(200,200,150,30);
		bb.setBounds(200,300,150,30);
		im.setBounds(0,0,500,500);
		f.setSize(500,500);

		f.add(im);
		im.add(pb);
		im.add(ub);
		im.add(bb);

		f.setLayout(null);	
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				f.setVisible(false);
				new PackFrame();
				}
		});	

		ub.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				f.setVisible(false);
				new UnpackFrame();
			}
		});

		bb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				f.setVisible(false);
				new Login();
			}
		});
	}
}
class PackFrame 
{
	public PackFrame()
	{
		JFrame f = new JFrame("Packer-Unpacker");
		JButton packButton = new JButton("Pack");
		JTextField fileField = new JTextField();
		JTextField folderField = new JTextField();
		JLabel l1 = new JLabel("File Name");
		JLabel l2 = new JLabel("Folder Name");
		ImageIcon img = new ImageIcon("images/loginimg.jpg");
		JLabel im = new JLabel(img);


		f.setSize(500,500);
		l1.setBounds(100,100,100,40);
		l2.setBounds(100,200,100,40);
		fileField.setBounds(300,100,150,30);
		folderField.setBounds(300,200,150,30);
		packButton.setBounds(100,300,150,30);
		im.setBounds(0,0,500,500);

		f.add(im);
		im.add(packButton);
		im.add(fileField);
		im.add(folderField);
		im.add(l1);
		im.add(l2);

		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		packButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				Packer P = new Packer(fileField.getText(),folderField.getText());
				f.setVisible(false);
				new PackUnpack();
			}
		});
	}
}

class UnpackFrame
{
	public UnpackFrame()
	{
		JFrame f = new JFrame("Packer-Unpacker");
		JTextField fileField = new JTextField();
		JLabel l1 = new JLabel("File Name");
		JButton unpackButton = new JButton("Unpack");
		ImageIcon img = new ImageIcon("images/loginimg.jpg");
		JLabel im = new JLabel(img);

		f.setSize(500,500);
		l1.setBounds(100,100,100,30);
		fileField.setBounds(200,100,150,40);
		unpackButton.setBounds(100,200,150,40);
		im.setBounds(0,0,500,500);
		
		f.add(im);
		im.add(unpackButton);
		im.add(fileField);
		im.add(l1);

		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		unpackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				UnPack P = new UnPack(fileField.getText());
				f.setVisible(false);
				new PackUnpack();
			}
		});
	}
}
class Packer
{
	int count = 0;
	FileOutputStream FOS = null;
	public Packer(String fname,String dname)
	{
		try
		{
			File file = new File(fname);
			FOS = new FileOutputStream(file);
			travelFolder(dname);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public void travelFolder(String folder)
	{
		File path = new File(folder);
		File farr[] = path.listFiles();
		for(File name : farr)
		{	
			if(name.getName().endsWith(".txt"))
			{
				count++;
				pack(name.getAbsolutePath());
			}
		}
	}
	public void pack(String filePath)
	{
		byte[] header = new byte[100];
		byte[] data = new byte[1024];
		int len = 0;
		byte KEY = 10; 

		File fobj = new File(filePath);
		FileInputStream FIS = null;

		String temp = filePath+" "+fobj.length();

		for(int i = temp.length();i < 100;i++)
		{
			temp += " ";
		}
		header = temp.getBytes();

		try
		{
			int i = 0;
			FIS = new FileInputStream(filePath);
			FOS.write(header,0,header.length);
			while((len = FIS.read(data)) > 0)
			{
				while(i < len)
				{
					data[i] = (byte)(data[i] ^ KEY);
					i++;
				}
				FOS.write(data,0,len);
			}
			FIS.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}

class UnPack
{
	FileInputStream FIS = null;
	FileOutputStream FOS = null;
	public UnPack(String fileName)
	{
		try
		{
			File file = new File(fileName);
			FIS = new FileInputStream(file);
			unpack(file);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public void unpack(File file)
	{
		byte[] header = new byte[100];
		int hlen = 0,dlen = 0,KEY =10; 
		try
		{
			while((hlen = FIS.read(header,0,100)) > 0)
			{
				String str = new String(header);
				
				String name = str.substring(str.lastIndexOf("/")+1);
				StringTokenizer st = new StringTokenizer(name," ");

				while(st.hasMoreTokens())
				{
					String fileName = st.nextToken();
					int fileLength = Integer.parseInt(st.nextToken());

					byte[] data = new byte[fileLength];

					FIS.read(data,0,fileLength);

					FOS = new FileOutputStream(fileName);
					int i = 0;
					while(i < fileLength)
					{
						data[i] = (byte)(data[i] ^ KEY);
						i++;
					}
					FOS.write(data,0,fileLength);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);			
		}
	}
}

class Main
{
	public static void main(String[] args) 
	{
		new Login();
	}
}
