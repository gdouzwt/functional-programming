package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class PassengerCountOrder implements Comparator<Car> {
    @Override
    public int compare(Car o1, Car o2) {
        return o1.getPassengers().size() - o2.getPassengers().size();
    }
}

@FunctionalInterface
interface CarCriterion {
    boolean test(Car car);
}

class RedCarCriterion implements CarCriterion {
    @Override
    public boolean test(Car car) {
        return car.getColor().equals("Red");
    }
}

public class CarScratch {
    public static void showAll(List<Car> lc) {
        for (Car c : lc) {
            System.out.println(c);
        }
        System.out.println("-------------------------------------");
    }

    public static List<Car> getColoredCars(Iterable<Car> in, String color) {
        List<Car> output = new ArrayList<>();

        for (Car c : in) {
            if (c.getColor().equals(color)) {
                output.add(c);
            }
        }
        return output;
    }

    public static List<Car> getCarsByGasLevel(Iterable<Car> in, int gasLevel) {
        List<Car> output = new ArrayList<>();

        for (Car c : in) {
            if (c.getGasLevel() >= gasLevel) {
                output.add(c);
            }
        }
        return output;
    }

    // 实际这里边包含两个过程，其一是在一个Iterable里边找东西，并放到另一个列表里。 第二步骤是如何判断是否放入。
    // - how to make a sublist
    // - what to put in a sublist
    // 行为作为方法的参数

    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                Car.withGasColorPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                Car.withGasColorPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                Car.withGasColorPassengers(9, "Black", "Weatherwax", "Magrat"),
                Car.withGasColorPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                Car.withGasColorPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
        );
        showAll(cars);
        showAll(getColoredCars(cars, "Black"));

        cars.sort(new PassengerCountOrder());

        showAll(cars);
    }
}
