class B extends A { }

class A{
    int v1;
    boolean a1;
    B a2;

void m1(int p1)
{
    v1 = m2(p1+(v1-10), a2, a2);
}

void m3(B p1){
    //a1 = 3;
    new B();
}

void m4(int p1){
    v1 = m5(null,null);
}

int m5(B p2, Object p3){ }

int m2(int p1,B p2, A p3 ){ return 10;} }