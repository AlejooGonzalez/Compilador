//[Error:noExiste|13]
// Error: llamada encadenada inválida, metodo inexistente en tipo retornado.
class A {
    B getB() {
        return new B();
    }
}
class B {
    int val;

    int test() {
        var a = new A();
        var n = a.getB().noExiste(); // metodo noExiste no existe en Breturn n;
    }
}


