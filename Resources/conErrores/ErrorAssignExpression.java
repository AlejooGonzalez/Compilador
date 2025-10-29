//[Error:=|6]
class ErrorAssignExpression {
    void met() {
        var x = 1;
        var y = 2;
        (x + y) = 3;
    }
}
