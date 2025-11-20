///33&exitosamente

class A{
  
  
    
      void m1(int p1){
        Object.debugPrint(p1);
    }
    
      int m2(){
        
        return 33;
    }
}


class Init{
    static void main()

    { 
        
        var x = new A();
        x.m2();
        Object.debugPrint(x.m2());
    }
}


