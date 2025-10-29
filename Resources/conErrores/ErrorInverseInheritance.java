//[Error:=|8]
class Animal {}
class Dog extends Animal {}

class ErrorInverseInheritance {
    void metodo() {
        var dog = new Dog();
        dog = new Animal();
    }
}
