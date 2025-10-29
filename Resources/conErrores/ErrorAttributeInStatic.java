//[Error:a|6]
class ErrorAttributeInStatic {
    int a;
    
    static void method() {
        var x = a;
    }
}
