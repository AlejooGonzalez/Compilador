///[SinErrores]

class A { }

class B extends A { }

class C {
    A m() {
        return new B();
    }
}