//[Error:x|6]
// Error: intento de encadenar sobre tipo primitivo.
class E13 {
    int m() {
        var x = 3;
        var y = x.getX(); // error: int no tiene métodos
        return y;
    }
}
