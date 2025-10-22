class A {
    int x;
    A n() { return this; }
    int f(int v) { return v + 1; }
}

class Test {
    void m() {
        var a = new A();
        if (a != null) {
            var i = 0;
            while (i < 5) {
                a.x = a.f(i) * 2;
                while (a.x < 10) {
                    a = a.n();
                    i = i + 1;
                }
            }
        }
    }
}