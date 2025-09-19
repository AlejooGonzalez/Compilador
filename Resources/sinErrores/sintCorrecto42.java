///[SinErrores]
// new, método estático y referencia encadenada

class A {
  A next;
  public A() { }
  A idMetVar() { return this; }
  static int g(int z) { return z; }
}

class B {
  void m() {
    var a = new A();
    var v = A.g(3);
    var r = a.idMetVar().next;
  }
}
