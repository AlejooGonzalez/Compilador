//[SinErrores]

class TestChained {
    void met() {
        var calc = new Calculator();
        var resultado = calc.add(1).add(2).obt();
        calc.add(11).add(22);
    }
}
class Calculator {
    int value;

    Calculator add(int x) {
        value = value + x;
        return this;
    }

    int obt() {
        return value;
    }
}