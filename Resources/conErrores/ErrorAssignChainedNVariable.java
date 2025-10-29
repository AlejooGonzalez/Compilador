//[Error:=|9]
class ErrorAssignChainedNVariable {
    int obtain() {
        return 12;
    }
    
    void method() {
        var a = new ErrorAssignChainedNVariable();
        a.obtain() = 2;
    }
}
