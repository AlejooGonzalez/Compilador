///1234&2468&hola&4000&55&exitosamente
// 

class A{
    int a1;
    int a2;
    String a3;
    
    
      void setall(int p1, int p2, int p3){
        a1 = p1;
        a2 = a1*2;
          {
              var x = 0;
              var y = 1;
              {
                  var z = 0;
                  {
                      var f = 69;
                  }
              }
          }
        a3 = "hola";
    }
    
      void m1(){
          Object.debugPrint(a1);
        Object.debugPrint(a2);
        System.printSln(a3);
        
    }
}

class B extends A{
     int a4;
     int a5;
    
    
      void seta4(){
        a4 = 4000;
        a5 = 55;
    }
    
      void m2(){
          Object.debugPrint(a4);
          Object.debugPrint(a5);
    }
}


class Init{
    static void main()
    
    {
        
        var x = new B();
        x.seta4();
        x.setall(1234,1,1);
        x.m1();
        x.m2();
    }
}


