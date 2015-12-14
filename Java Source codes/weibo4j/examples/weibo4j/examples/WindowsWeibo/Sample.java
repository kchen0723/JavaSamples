package weibo4j.examples.WindowsWeibo;

class   Sample 
{ 
static   String   a   =   "car "; 

static   String[]   b   ={ "car ", "plane ", "boat "}; 

static   boolean   ainb   (String   a,String[]   b) 
{ 
      for   (int   i=0;i <b.length;i++) 
            if   (a==b[i]) 
                      return   true; 
      return   false; 
} 
public   static   void   main(String[]   args) 
{ 
if   (ainb(a,b)) 
System.out.println( "ok "); 
else 
System.out.println( "not "); 
} 
} 