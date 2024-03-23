public class Main {
    public static void main(String[] args) {

        Triangle triangle = new Triangle(3, 4, 5);

        System.out.println("Периметр треугольника равен = " +  triangle.perimeter());
        System.out.println("Площадь треугольника = " + triangle.square());

    }
}
