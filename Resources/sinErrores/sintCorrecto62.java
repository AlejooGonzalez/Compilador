///[SinErrores]
// Bloque con varias sentencias y expresiones compuestas

class A {
  int x;
  void m() {
    { ; var y = 1 + 2; x = y * 3; return; }
  }
}
