//[Error:==|9]
class ClassA {}
class ClassB {}

class ErrorEquality {
    void met() {
        var a = new ClassA();
        var b = new ClassB();
        var x = a == b;
    }
}
