//[Error:this|5]
// Error: uso de 'this' en metodo estático.
class E22 {
    static void m() {
        var x = this;
    }
}
