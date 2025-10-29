//[Error:=|8]
class ErrorAssignMethod {
    int ob() {
        return 42;
    }
    
    void method() {
        ob() = 10;
    }
}
