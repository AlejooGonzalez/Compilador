///[SinErrores]
// this, string literal, acceso variable vs llamada

class A {
  int x;
  void f() { }
  void m() {
    var t = this;
    var s = "hola";
    x;
    f();
  }
}
