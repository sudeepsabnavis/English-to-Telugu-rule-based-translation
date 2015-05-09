import java.io.*;
import java.awt.*;        
import java.awt.event.*;  
import java.sql.*;
import java.util.*;

public class Home extends Frame implements ActionListener 
{
   private Label lblText;    
   private TextField tfEng;
   private Button btnTrans;  
   private TextArea taDisplay;     
   public Home() 
	   {
	   Frame f= new Frame();
      f.setLayout(new GridLayout(1,3));
	  Panel panel1 = new Panel( new FlowLayout ());
	  Panel panel2 = new Panel(new FlowLayout ());
	  Panel panel3 = new Panel(new FlowLayout ());
      lblText = new Label("Enter the text"); 
      panel1.add(lblText);                    
	  tfEng = new TextField(20); 
      tfEng.setEditable(true);      
      panel1.add(tfEng);                     
      btnTrans = new Button("Translate");   
      panel2.add(btnTrans);                    
	  taDisplay = new TextArea(5, 40); 
      panel3.add(taDisplay);
      btnTrans.addActionListener(this);
	  f.addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   System.exit(0);
	  	}}); 
        
	  f.add(panel1);
	  f.add(panel2);
      f.add(panel3);
      f.setTitle("Rule Based translation from English to Telugu");  
      f.setSize(1000, 200);       
      f.setVisible(true);         
      
       }
	public static void main(String[] args) 
		{
			Home app = new Home();
		}
   @Override
   public void actionPerformed(ActionEvent evt) 
	   {
			Translate t= new Translate();
			Vector<String>  result;
			result=t.translate(tfEng.getText());
			String[] strings = result.toArray(new String[result.size()]);
			StringBuilder sbStr = new StringBuilder();
			String op;
			for (int i = 0, il = strings.length; i < il; i++) 
			{
				if (i > 0)
				sbStr.append(" ");
				sbStr.append(strings[i]);
			}
			op=sbStr.toString();
			taDisplay.setText(op); 
		}
}

class Translate 
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/nlp";
    static final String USER = "root";
    static final String PASS = "";
	String parse,trim,ew,tw,nextw,prevw,startw,ps,gender,ps1;
	Vector<String> eword=new Vector<String>();
	Vector<String> tword=new Vector<String>();
	Vector<String> pos=new Vector<String>();
	Vector<String> out=new Vector<String>();
	ResultSet rs,rs1,rs2,rs3;
	String past,pre,adv,sing_plu="s",prep,person="3";
	int count=0,i=0;
	boolean comp1=false,comp2=false,comp3=false;
	Vector<String> translate(String str)
	{
	   Connection conn = null;
       PreparedStatement stmt = null;
       Vector<String> v= new Vector<String>();
	   try
		{
		  Class.forName("com.mysql.jdbc.Driver");
		  conn = DriverManager.getConnection(DB_URL,USER,PASS);
		  StringTokenizer st = new StringTokenizer(str, " ");
		  while(st.hasMoreTokens()) 
			{ 
				parse=st.nextToken();
				v.addElement(parse.toLowerCase()); 
			}	
		  startw=v.get(0);
		  if(startw.equals("i")||startw.equals("he")||startw.equals("she")||startw.equals("you"))
			{
				sing_plu="s";
			}
		  if(startw.equals("we")||startw.equals("they"))
			{
				sing_plu="p";
			}
		  if(startw.equals("i"))
			{
			  person="1";
			}
			if(startw.equals("you")||startw.equals("we"))
			{
				person="2";
			}
		  for(i=0;i<v.size();i++)
			{
			  parse=v.get(i);
			  count=parse.length();
			  if(count>=3)
				{
					 past= parse.substring(count-2,count);
					 pre= parse.substring(count-3,count);
					 adv= parse.substring(count-2,count);
					comp1= past.equals("ed");
					comp2= pre.equals("ing");
					comp3=adv.equals("ly");
				}
			  if(comp3==true)
				{
					trim=parse.substring(0,count-2);
					stmt=conn.prepareStatement("select * from dict where eword=? and pos='adv'");
					stmt.setString(1,trim);
					rs=stmt.executeQuery();
					if(rs.next())
					{
					tw=rs.getString("tword");
					ew=rs.getString("eword");
					ps=rs.getString("pos");
					eword.addElement(ew);
					tword.addElement(tw);
					pos.addElement(ps);
					}
				}
			   if(comp1==true)
				{
					trim=parse.substring(0,count-2);
				    stmt=conn.prepareStatement("select gender from dict where eword=? ");
					stmt.setString(1,startw);
					rs1=stmt.executeQuery();
					if(rs1.next())
					{
						gender=rs1.getString("gender");
					}
					stmt=conn.prepareStatement("select * from dict where eword=? and sing_plu=? and person=?");
					stmt.setString(1,trim);
					stmt.setString(2,sing_plu);
					stmt.setString(3,person);
					rs2=stmt.executeQuery();
					while(rs2.next())
					{
						if(gender.equals(rs2.getString("gender")))
						{
							tw=rs2.getString("past");
							break;
						}
					}
				    ps=rs2.getString("pos");
					eword.addElement(parse);
					tword.addElement(tw);
					pos.addElement(ps);
				}
              else if (comp2==true)
              {
				    trim=parse.substring(0,count-3);
					stmt=conn.prepareStatement("select * from dict where eword=? ");
					stmt.setString(1,startw);
				    rs1=stmt.executeQuery();
					if(rs1.next())
				    {
						gender=rs1.getString("gender");
					}
					stmt=conn.prepareStatement("select * from dict where eword like ? and sing_plu=? and person=?");
					stmt.setString(1,trim+"%");
					stmt.setString(2,sing_plu);
					stmt.setString(3,person);
				    rs2=stmt.executeQuery();
					while(rs2.next())
					{
						if(gender.equals(rs2.getString("gender")))
						{
							tw=rs2.getString("present");
							break;
						}
					}
					
					ps=rs2.getString("pos");
					eword.addElement(parse);
					tword.addElement(tw);
					pos.addElement(ps);

			  }  
			  else if(parse.equals("is")||parse.equals("was")|| parse.equals("are"))
				{
				  
				  stmt=conn.prepareStatement("SELECT * FROM dict where eword=?");
				  stmt.setString(1,"is");
				  rs=stmt.executeQuery();
				  if(rs.next())
					{
						ps1=rs.getString("pos");
						tw=rs.getString("tword");
						eword.addElement(parse);
						tword.addElement(tw);
						pos.addElement(ps1);
					}
				}
				else if(parse.equals("will"))
				{
					stmt=conn.prepareStatement("select * from dict where eword=? ");
					stmt.setString(1,startw);
				    rs1=stmt.executeQuery();
					if(rs1.next())
				    {
						gender=rs1.getString("gender");
					}
					stmt=conn.prepareStatement("select * from dict where eword=? and sing_plu=? and person=?");
					stmt.setString(1,startw);
					stmt.setString(2,sing_plu);
					stmt.setString(3,person);
				    rs2=stmt.executeQuery();
					while(rs2.next())
					{
						if(gender.equals(rs2.getString("gender")))
						{
							tw=rs2.getString("future");
							break;
						}
					}
					
					ps=rs2.getString("pos");
					eword.addElement(parse);
					tword.addElement(tw);
					pos.addElement(ps);

				}

			 else
				{
					stmt=conn.prepareStatement("SELECT * FROM dict where eword=?");
					stmt.setString(1,parse);
					rs=stmt.executeQuery();
					if(rs.next())
					{
						ew=rs.getString("eword");
						tw=rs.getString("tword");
						ps=rs.getString("pos");
					}
					if(ew.equals(""))
					{
						eword.addElement(parse);
						tword.addElement(parse);
						pos.addElement("n");
					}
					else if (parse.equals(v.get(0)))
					{
						eword.addElement(rs.getString("eword"));
						tword.addElement(rs.getString("tword"));
						pos.addElement(rs.getString("pos"));
					}
					else
					{
						startw=v.get(0);
						if(ps.equals("adj"))
						{
							stmt=conn.prepareStatement("select * from dict where eword=?");
							stmt.setString(1,startw);
							rs1=stmt.executeQuery();
							stmt=conn.prepareStatement("select * from dict where eword=?");
							stmt.setString(1,parse);
							rs2=stmt.executeQuery();
							rs3=rs2;
							if(rs3.next())
							{
								tw=rs3.getString("tword");
								ps=rs3.getString("pos");
							}

							if(rs1.next())
							{
								gender=rs1.getString("gender");
							}
							while(rs2.next())
							{
								if(gender.equals(rs2.getString("gender")))
								{
									tw=rs2.getString("tword");
									break;
								 }
							}
						}
						tword.addElement(tw);
						eword.addElement(parse);
						pos.addElement(ps);
					}
				}
			}
			StringBuilder sbStr1 = new StringBuilder();
			String[] strings1 = pos.toArray(new String[pos.size()]);
			String imp;
			for (int j = 0, il = strings1.length; j < il; j++) 
				{
					if (j > 0)
					sbStr1.append(" ");
					sbStr1.append(strings1[j]);
				}
			imp=sbStr1.toString();
			System.out.println(eword);
			System.out.println(tword);
			System.out.println(pos);
			System.out.println(imp);
			//String nn=Integer.toString(0x0c28);
			//String ee=Integer.toString(0x0c3f);
			if (pos.size()==3)
			{
				if(imp.equals("n v n")==true||imp.equals("n v pro")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(2)+"\u0c28"+"\u0c3f");
					out.addElement(tword.get(1));
				}
				if(imp.equals("pro v v")==true||imp.equals("n v v")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(2));
					out.addElement(tword.get(1));
				}
				/*if(imp.equals("pro v adj")==true|| imp.equals("n v adj")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(1));
				}*/
				if(imp.equals("pro v n")==true||imp.equals("pro v pro")==true)
				{
					
						out.addElement(tword.get(0));
						out.addElement(tword.get(2)+"\u0c28"+"\u0c3f");
						out.addElement(tword.get(1));

				}
			   if(imp.equals("n v adv")== true||imp.equals("pro v adv")==true)
					{
						out.addElement(tword.get(0));
						out.addElement(tword.get(2)+"\u0c17"+"\u0c3e");
						out.addElement(tword.get(1));
					}
			}	
		   if(pos.size()==4)
			{
				/*if(imp.equals("n v prep n")==true||imp.equals("pro v prep n")==true)
				{
					prep=eword.get(2);
					if(prep.equals("to"))
					{
						out.addElement(tword.get(0));
						out.addElement(tword.get(3)+"\u0c15"+"\u0c3f");
						out.addElement(tword.get(1));
					}
					if(prep.equals("with"))
					{
						out.addElement(tword.get(0));
						out.addElement(tword.get(3)+"\u0c15"+"\u0c3f");
						out.addElement(tword.get(2));
					}
					if(prep.equals("at")||prep.equals("in"))
					{
						out.addElement(tword.get(0));
						out.addElement(tword.get(3)+"\u0c32"+"\u0c4a");
						out.addElement(tword.get(2));
					}

				}*/
				if(imp.equals("n v v n")==true||imp.equals("pro v v n")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(3)+"\u0c28"+"\u0c3f");
					out.addElement(tword.get(2));
				}
				if(imp.equals("pro v det n")==true|| imp.equals("n v det n"))
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(2));
					out.addElement(tword.get(3));
					out.addElement(tword.get(1));
				}
				if(imp.equals("pro v v adv")==true||imp.equals("n v v adv")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(3)+"\u0c17"+"\u0c3e");
					out.addElement(tword.get(2));
					out.addElement(tword.get(1));
				}
			}
		   if (pos.size()==5)
			{
				if(imp.equals("n v n prep n")==true||imp.equals( "pro v n prep n")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(2)+"\u0c28"+"\u0c3f");
					out.addElement(tword.get(4));
					out.addElement(tword.get(3));
					out.addElement(tword.get(1));
				}
				if(imp.equals("n v v det n")==true||imp.equals("pro v v det n")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(3));
					out.addElement(tword.get(4)+"\u0c28"+"\u0c3f");
					out.addElement(tword.get(2));
					out.addElement(tword.get(1));
				}
				/*if(imp.equals("pro v prep det n")==true||imp.equals(" n v prep det n")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(4));
					out.addElement(tword.get(2));
					out.addElement(tword.get(1));
					out.addElement(tword.get(3));
				}
				if(imp.equals("pro v v prep n")==true||imp.equals("n v v prep n")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(4));
					out.addElement(tword.get(3));
					out.addElement(tword.get(2));
					out.addElement(tword.get(1));
				}
				
				if(imp.equals("pro v def n adv")==true||imp.equals("n v def n adv")==true)
				{
					out.addElement(tword.get(0));
					out.addElement(tword.get(2));
					out.addElement(tword.get(3));
					out.addElement(tword.get(4));
					out.addElement(tword.get(1));
				}*/
			}
			stmt.close();
			conn.close();
			return out;
		}
		catch(Exception e)
		{
          e.printStackTrace();
		  return out;
		}
	}
}




	
		
	
	