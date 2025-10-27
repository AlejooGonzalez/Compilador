// Uso correcto de 'this' en metodo no estático.
class C4 {
    int a;
    public C4(int z) { this.a = z; }
    int useThis() {
        var x = this.a + 2;
        return x;
    }
}
