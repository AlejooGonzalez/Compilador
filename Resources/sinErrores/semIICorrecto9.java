class C9 {
    void v() { return; }
    int caller() {
        v();
        var x = 0;
        return x;
    }
}
