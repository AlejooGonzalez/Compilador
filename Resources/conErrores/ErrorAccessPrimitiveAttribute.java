//[Error:x|5]
class ErrorAccessPrimitiveAttribute {
    void met() {
        var x = 42;
        var y = x.st;
    }
}
