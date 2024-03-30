import java.util.Objects;

public record Triangle(int a, int b, int c) {

    public Triangle {
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Стороны треугольника должны быть положительными");
        } else if (a + b < c || a + c < b || c + b < a) {
            throw new IllegalArgumentException("Сумма двух сторон треугольника должна быть больше третьей стороны");
        }
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double square() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (a == triangle.a && b == triangle.b && c == triangle.c) ||
                (a == triangle.a && b == triangle.c && c == triangle.b) ||
                (a == triangle.b && b == triangle.a && c == triangle.c) ||
                (a == triangle.c && b == triangle.a && c == triangle.b) ||
                (a == triangle.b && b == triangle.c && c == triangle.a) ||
                (a == triangle.c && b == triangle.b && c == triangle.a);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
