class Test {
    void m() {
        var x = 0;
        if (x < 10 && true) x = x + 1;
        else x = x + 2;
    }
}