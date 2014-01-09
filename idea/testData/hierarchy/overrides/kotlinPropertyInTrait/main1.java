interface A extends T {
    public String getFoo();
    public void setFoo(String value);
}

class B implements T {
    public String getFoo() {

    }

    public void setFoo(String value) {

    }
}

class C implements A {
    public String getFoo() {

    }

    public void setFoo(String value) {

    }
}

class D extends Z {
    public String getFoo() {

    }

    public void setFoo(String value) {

    }
}