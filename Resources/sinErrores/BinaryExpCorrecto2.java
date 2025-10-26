///[SinErrores]

class A {
    int a1;

    void m1(int p1)
    {
        m1(2);
    }

    void m2()
    {}

    A m3(A a){
        return new A();
    }



}