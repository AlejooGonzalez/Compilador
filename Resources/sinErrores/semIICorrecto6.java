class A {
    A n() { return this; }
    int f() { return 1; }
}

class Test {
    void m() {
        var a = new A();
        a.n().n().f();
    }
}