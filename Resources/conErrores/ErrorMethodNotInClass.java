//[Error:abc|9]
class Class {
    int a;
}

class ErrorMethodNotInClass {
    void met() {
        var obj = new Class();
        obj.abc();
    }
}
