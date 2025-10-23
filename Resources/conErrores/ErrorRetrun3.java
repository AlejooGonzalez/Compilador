///[Error:return|9]

class A { }

class B extends A { }

class C {
    B m() {
        return new A();
    }
}