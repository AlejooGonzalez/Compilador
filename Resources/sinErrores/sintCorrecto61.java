///[SinErrores]
// new con múltiples argumentos

class A {
  public A(int p, char q) { }
}
class B {
  void m() {
    var a = new A(1, 'z');
  }
}
