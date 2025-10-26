///[SinErrores]

class A {
    int a1;

    void m1(int p1)
    {
        m3();
    }

    void m2()
    {}

    A m3(){
        return new A();
    }



}