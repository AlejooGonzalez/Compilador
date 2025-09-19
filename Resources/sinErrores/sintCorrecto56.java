///[SinErrores]
// Uso de método estático dentro de una expresión aritmética

class A {
  static int g(int z) { return z; }
}
class B {
  void m() {
    var v = A.g(3) + 2;
  }
}
