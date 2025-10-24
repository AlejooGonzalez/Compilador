//[Error:t|13]

class A {
    int a1;
    int b1;

    void m1(int p) {
        var e = 1;
        if(true) {
            var t2 = 1;
            if(true) {
                var t = 1;
                var t = 2;
            }
        }
        if(true){
            var q = 0;
        }
    }
}