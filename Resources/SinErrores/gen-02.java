///1234&33&exitosamente

class A{
    int x;
    
   
      void mc(){
        Object.debugPrint(1234);
        x = 33;
        Object.debugPrint(x);
      }
}


class Init{
    static void main()
    { 
        var a = new A();
        a.mc();
        
    }
}


