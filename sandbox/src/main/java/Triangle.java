public record Triangle(int a, int b, int c) {

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double square() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }


}
