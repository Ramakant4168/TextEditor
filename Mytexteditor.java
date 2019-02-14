import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

class Mytexteditor extends Frame implements ActionListener,KeyListener
 {  
    Frame f,f100;
 
    TextArea ta;    // textarea in main frame
   
    MenuBar mb;

    Menu m1,m2,m3;

    MenuItem nw,opn,sve,sves,ext,fin,rep,vwhlp;
   
    String path,name,source,source2,disp="";    // name of file opened in open menu
   
    File f1;               //  file object
   
    byte b[];

    FileInputStream  fis;							  
    FileOutputStream  fos;

    BufferedInputStream bis;

    BufferedOutputStream bos;

    FileDialog fd1,fd2;  // for open and save

    Dialog d,d1,d2,d3,d11;

    Windowcloser wc,wc1;       Subwindowcloser swc; //for closing window

    TextField t1,t2,t3;   // for find,replace

    Button  b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b100,b11;

    Panel p1,p2,p3,p4,p5a,p5b,p5,p6,p7;
  
    int flag,flag1,pos,flagCancel=0,flagSmall=0;
    
     boolean boolOpen=false; boolean boola=false;  boolean bool=false;	int check=0;int kpos,lpos;
    
    int nxtPo=0;int c=0,nxtln=0,position=0;

/* this is constructor for initializing all values */

    public Mytexteditor() throws Exception 
    {  f=new Frame("TextEditor");
       
       f.setSize(500,400);                /* main frame description */
       f.setLayout(new BorderLayout());
     
       wc=new Windowcloser(); swc=new Subwindowcloser(); 
       f.addWindowListener(wc);
 
      ta=new TextArea(null); ta.addKeyListener(this);  /* text area info */
      f.add(ta);

      mb=new MenuBar();       /* menu bar info */
      f.setMenuBar(mb);

      m1=new Menu("File");      /* menu1 info  */
      mb.add(m1);
      nw=new MenuItem("New");nw.addActionListener(this);
      m1.add(nw);

      d3=new Dialog(f,"Save Changes",true);                   /*  info of filedialog if new is clicked nd file doesnot exist */
     d3.addWindowListener(swc);
      
      d3.setLayout(new GridLayout(2,1));    
      d3.setSize(300,200);
      Label l6=new Label("Wants to save changes");
      d3.add(l6);
      p7=new Panel(new FlowLayout());d3.add(p7);
      b7=new Button("save");b7.addActionListener(this);
      b8=new Button("Don't save");b8.addActionListener(this);
      b9=new Button("cancel");
      b9.addActionListener(this);
      p7.add(b7);p7.add(b8);p7.add(b9);
      d3.setVisible(false);



      opn=new MenuItem("Open");opn.addActionListener(this);
      m1.add(opn);

      fd1=new FileDialog(f,"Open",FileDialog.LOAD);                /* filedialog for open option */
      fd1.setVisible(false);

        d2=new Dialog(f,"Not Found",true);                             /*  info of filedialog if opened file doesnot exist */
         d2.addWindowListener(swc);
        d2.setLayout(new GridLayout(2,1));    
        d2.setSize(300,200);
        Label l4=new Label("No such file exists Try again");
        d2.add(l4);
        p6=new Panel(new FlowLayout());
        b6=new Button("Ok");b6.addActionListener(this);
        p6.add(b6);d2.add(p6);
        d2.setVisible(false);
        

      sve=new MenuItem("Save");sve.addActionListener(this);       /* for saving to current opened file */
      m1.add(sve);
     

      sves=new MenuItem("SaveAs");sves.addActionListener(this);       /* for saving new file  */
      m1.add(sves);
     
      fd2=new FileDialog(f,"SaveAs",FileDialog.SAVE);                /* filedialog for saving file */
      fd2.setVisible(false); 
       
      m1.addSeparator();

      ext=new MenuItem("Exit");                                     /* exit from editor from file menu  */
      m1.add(ext);ext.addActionListener(this);

      m2=new Menu("Edit");                                       /*  info of edit menu   */
      mb.add(m2);
     
      fin=new MenuItem("Find");fin.addActionListener(this);
      m2.add(fin);

       d1=new Dialog(f,"Find",false);                    /* dialog box for finding words contained in find menu item of edit menu */
       d1.setLayout(new GridLayout(3,1));
        d1.addWindowListener(swc);
       d1.setSize(300,200);
       Label l3=new Label("Find..");
       d1.add(l3);
       p4=new Panel(new FlowLayout());
       t3=new TextField(15);p4.add(t3);d1.add(p4);
       p5=new Panel(new GridLayout(1,2));
       d1.add(p5);
       p5a=new Panel(new FlowLayout(FlowLayout.LEFT));p5.add(p5a);
       b4=new Button("find next");b4.addActionListener(this);
       p5a.add(b4);
       p5b=new Panel(new FlowLayout(FlowLayout.RIGHT));p5.add(p5b);
       b5=new Button("close");b5.addActionListener(this);
       p5b.add(b5);



      rep=new MenuItem("Replace");rep.addActionListener(this);    /* replace menu item of edit  */
      m2.add(rep);
      
     
      d=new Dialog(f,"Replace",false);     /* file dialog for replacing words in edit menu  */
      d.setLayout(new GridLayout(5,1));
      d.setSize(300,200); d.addWindowListener(swc);
      Label l1=new Label("Find..");
      d.add(l1);
      p1=new Panel(new FlowLayout(FlowLayout.LEFT));
      t1=new TextField(15);
      p1.add(t1);d.add(p1);
      Label l2=new Label("Replace with..");
      d.add(l2);
      p2=new Panel(new FlowLayout(FlowLayout.LEFT));
      t2=new TextField(15);
      p2.add(t2);d.add(p2);
      p3=new Panel();
      b1=new Button("find next");b1.addActionListener(this);
      b2=new Button("replace");b2.addActionListener(this);
       b0=new Button("Replace All");b0.addActionListener(this);
      b3=new Button("close");b3.addActionListener(this);
      p3.add(b1);p3.add(b2);p3.add(b0);p3.add(b3);d.add(p3);

      m3=new Menu("Help");mb.add(m3);
       vwhlp=new MenuItem("about");vwhlp.addActionListener(this);
       m3.add(vwhlp);
      





      f.setVisible(true);


     }      //  end of constructor
 
    





     public void actionPerformed(ActionEvent e)       /*  overriden method of ActionEvent class for specifying actions  */
      {     
          String str=e.getActionCommand();              /*  string holding info of component reffered ie button clicked   */
           
         try
           {

               if (str.equals("New"))            /* action generated for selecting new in menu file  */ 
               {    
                  if(flag==1)
                   {
                      d3.setVisible(true);
                   }
                 
                 name=null;path=null;
                   ta.setText("");
                    //flag=0;
                  

                }                                   
                  
            




              if(str.equals("Open"))                /* action generated for selecting open in menu file  */ 
                {
                   
                  
                   if(flag==1)
                   {  
                     d3.setVisible(true);
                      
                   }

                  
                   
                     fd1.setVisible(true);
                     if(name!=null)
                    {
                     ta.setText("");
                    }  
                      name=fd1.getFile();
                      path=fd1.getDirectory();
                      f1=new File(path,name);
                      if(!f1.exists())                          // if opened file doesnt exist  
                    {
                      d2.setVisible(true);
                    }

                     fis=new FileInputStream(f1);                                    
                     bis=new BufferedInputStream(fis);
                     int ch;boolOpen=true;
                     while((ch=bis.read())!=-1)
                     { String sp=(char)ch+"";
                       disp=disp+sp;
                                                                   //ta.appendText((char)ch+""); 
                     }
                       ta.setText(disp);
                      //bis.close();
                         disp="";
                      
                    
                   
                 
            } 

              
                  if(e.getSource()==b6)             /* pressing ok in d2 above ie open mode of non existin file closes dialog box */
                    { 
                       d2.setVisible(false);
                    }
                  

           
            if(str.equals("Save")||e.getSource()==b7)          /* action generated for selecting save in menu file  */
              { 
                  if(name!=null)
                   {   
                         source=ta.getText();
                         b=source.getBytes();
                         fos=new FileOutputStream(f1);                                    
                         bos=new BufferedOutputStream(fos);
                         for(int i=0;i<b.length;i++)
                         bos.write(b[i]);
                         bos.close();
                         
                   } 
                  else
                   {  fd2.setVisible(true);
                        name=fd2.getFile();
                        path=fd2.getDirectory();
                        f1=new File(path,name); 
                        source=ta.getText();
                        b=source.getBytes();
                        fos=new FileOutputStream(f1);                                    
                        bos=new BufferedOutputStream(fos);
                        for(int i=0;i<b.length;i++)
                        bos.write(b[i]);
                        bos.close(); flag=0;
                    }

                     if(e.getSource()==b7)
                        {  d3.setVisible(false);
                           flag=0;
                        } 
                      flag=0;
                     flagSmall=0;    

                }


              if(str.equals("Don't save"))
                  {   d3.setVisible(false);
                      ta.getText();
                      ta.setText("");
                      name=null;path=null;
                      flag=0; flagSmall=0; 
                  }  
                      

                if(e.getSource()==b9)                 /* exit option of save changes */
                  {
                     d3.setVisible(false);
                     if(flagCancel==1)
                      {flagCancel=2;}
                       
                      
                  }
                      


                     if(str.equals("SaveAs"))          /* action generated for selecting SaveAs in menu file  */
                      {  
                        fd2.setVisible(true);
                        name=fd2.getFile();
                        path=fd2.getDirectory();
                        f1=new File(path,name); 
                        source=ta.getText();
                        b=source.getBytes();
                        fos=new FileOutputStream(f1);                                    
                        bos=new BufferedOutputStream(fos);
                        for(int i=0;i<b.length;i++)
                        bos.write(b[i]);
                        bos.close();
                      }

             
                if(str.equals("Exit"))            /* action generated for selecting Exit in menu file  */
                {     if(flag==1)
                     d3.setVisible(true);
                     else{ flag=0;
                            System.exit(1);}
                 } 



               if(str.equals("Find"))                 /* action generated for selecting find in menu Edit  */
               {   
                  d1.setVisible(true);
                 /* String st1,st2;
                     st1=ta.getText();  System.out.println(st1);
                     st2=t3.getText();  System.out.println(st2); */
                  
                    
                  
               }

              


              /*  if(e.getSource()==b4)               //   action for    for  pressing find next in find dialog   old one 
               {
                     String st1,st2;
                     st1=ta.getText();  System.out.println(st1);
                     st2=t3.getText();  System.out.println(st2);
                 
                     Pattern p=Pattern.compile(st2);
                     Matcher m=p.matcher(st1);
                    // boolean b=m.find();
                     
                     while(m.find())
                    { int a1=m.start();
                      int a2=m.end();
                      System.out.print(a1+" ");                            
                      System.out.println(a2); 
                      ta.select(a1,a2);
                     }
                    

                  // int a;
                   pos=st1.indexOf(st2,pos);
                    
                   ta.select(pos,pos+st2.length());
                     ++pos;  }*/ 

             if(boolOpen==true)                 /*   dhar find */
		{
			
				
			if(str.equals("Find Next"))
			{
					
					String find=t3.getText();    //array OF WORD
					String fnxt=ta.getText();    //ARRAY OF TEXTAREA
					int ch,i=0;int j=0;
					position=fnxt.indexOf(find,position); System.out.println(position+"true");
					ta.select(position,position+find.length());++position;
	
					
			}
		}
		
            else
		if(e.getSource()==b4)
		{
			check=1;
			String s=t3.getText();
			String b=ta.getText();
			int position=b.indexOf(s,nxtPo);
			nxtln=b.indexOf("\n",nxtPo);
			System.out.println(position+"position");
			System.out.println(nxtln+"nxtln");
			System.out.println(nxtPo+"nxtPo");
			if(nxtln!=-1)	
				if(nxtln<=position)
					boola=true;			
				else
					boola=false;
			else boola=false;
			
			if(boola)
			{
				c++;
				findIt(nxtPo,c);
			}
			else
			{
				findIt(nxtPo,c);
			}
			nxtPo=position+s.length();
			System.out.println(nxtPo+"nxtPo 2");
		}



 
               if(e.getSource()==b1)               /*  replace vala find next   */
		{	
			check=2;
			String s=t1.getText();
			String b=ta.getText();
			int position=b.indexOf(s,nxtPo);
			nxtln=b.indexOf("\n",nxtPo);
			System.out.println(position+"position");
			System.out.println(nxtln+"nxtln");
			System.out.println(nxtPo+"nxtPo");
			if(nxtln!=-1)	
				if(nxtln<=position)
					boola=true;			
				else
					boola=false;
			else boola=false;
			
			if(boola)
			{
				c++;
				findIt(nxtPo,c);
			}
			else
			{
				findIt(nxtPo,c);
			}
			nxtPo=position+s.length();
			System.out.println(nxtPo+"nxtPo 2");
		}

      




            if(e.getSource()==b2)
		{
			if(bool==true)
			{
				String s=t2.getText();
				char ch=127;
				ta.replaceText(ch+"",kpos,lpos);	
				ta.replaceText(" ",kpos,kpos+1);	
				//ta.insertText("\b",kpos);
				ta.insertText(s,kpos);
			}
		}


            

               if(e.getSource()==b0)               /*   action for replace all    */ 
               {
                    String st11,st22,st44;
                    String st33=null;
                     st11=ta.getText();  
                     st22=t1.getText(); 
                     st44=t2.getText();
                      Pattern p=Pattern.compile(st22);
                     Matcher m=p.matcher(st11);
                      if(m.find())
                     st33=m.replaceAll(st44);
                     ta.setText(st33);                

                    
               }


              if(e.getSource()==b5)               /*   action for closing dialog box  of find   for  pressing close */ 
               {
                    d1.setVisible(false);
                     nxtPo=0;c=0;nxtln=0;
               }





            if(str.equals("Replace"))                /* action generated for selecting Replace  in menu Edit  */
              {    
                d.setVisible(true); 
              }
 
            if(e.getSource()==b3)               /*   action for closing dialog box  of replace  for  pressing close */ 
              {
                    d.setVisible(false);
                      nxtPo=0;c=0;nxtln=0;
               }


             if(e.getSource()==b100)               /*   action for closing dialog box  of replace  for  pressing close */ 
              {
                    f100.setVisible(false);
                      nxtPo=0;c=0;nxtln=0;
               }







          }
           catch(IOException exc)
           {
               System.out.println(exc.getMessage());
           }                                                            /* end of try catch */         
            
          

           System.out.println(str+" was clicked"); 
   
 

 }       /* end of method ActionPerformed */



 public void findIt(int nxtPo,int count)  
	 {
		String s="";
		if(check==1)
			s=t3.getText();
		if(check==2)
			s=t1.getText();
		String b=ta.getText();
		int position=b.indexOf(s,nxtPo);
		if(position==-1)
		{
			f100=new Frame();
			f100.setSize(100,100);
			Label l=new Label("End OF File Reached!!");
			b100=new Button("close");
			b100.addActionListener(this);
			f100.add(l,"North");	f100.add(b100,"South");
			f100.setVisible(true);
			return ;
		}
                
		nxtPo=position+s.length();
		System.out.println(count+"count"+nxtPo+"nxtPo");	
		ta.select(position-count,(position+s.length()-count));
                 ta.requestFocus();
		kpos=position-count;lpos=position+s.length()-count;
		bool=true;
	}

      public void keyTyped(KeyEvent e)
        {  
            
                 flag=1;
        }


        public void keyReleased(KeyEvent e)
          { flag=1;}

         public void keyPressed(KeyEvent e)
          {flag=1; } 
            
       


 class Windowcloser extends WindowAdapter
   {  public void windowClosing(WindowEvent e)
      {  

          if(flag==1)
          {  flagCancel=1;
            d3.setVisible(true);
          }
       else
       {
         if(flagSmall==0)        
 
         if(flagCancel!=2)  
          { Window w=e.getWindow();
           w.setVisible(false);
           w.dispose();
           System.exit(1);flagCancel=0;
          }
        }



   if(flagSmall==0)        
 
         if(flagCancel!=2)  
          { Window w=e.getWindow();
            w.setVisible(false);
            w.dispose();
            System.exit(1);
            flagCancel=0;
          }
     
        }
       
   }     


 class Subwindowcloser extends WindowAdapter
 {  public void windowClosing(WindowEvent e)
      {
         Window w=e.getWindow();
         w.setVisible(false);
         w.dispose();
         flagSmall=2;
       }
   }    

 /*  main method  */

     public static void main(String args[]) throws Exception
      { 
         Mytexteditor m=new Mytexteditor();
      }
 


 }      /*  end of class  */
     