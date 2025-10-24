//[Error:t|12]

class A {
    int a1;
    int b1;

    void m1(int p) {
        var e = 1;
        if(true) {
            var t = 1;
            {
                var t = 1;
            }
        }
        if(true){
            var q = 0;
        }
    }
}