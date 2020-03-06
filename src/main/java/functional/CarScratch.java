package functional;

import java.time.LocalDate;
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
interface Criterion<E> {
    boolean test(E car);
}


public class CarScratch {
    public static <E> void showAll(List<E> lc) {
        for (E c : lc) {
            System.out.println(c);
        }
        System.out.println("-------------------------------------");
    }

    public static <E> List<E> getByCriterion(Iterable<E> in, Criterion<E> crit) {
        List<E> output = new ArrayList<>();

        for (E c : in) {
            if (crit.test(c)) {
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
        showAll(getByCriterion(cars, Car.getRedCarCriterion()));
        showAll(getByCriterion(cars, Car.getGasLevelCarCriterion(6)));
//        cars.sort(new PassengerCountOrder());
        showAll(cars);
        cars.sort(Car.getCarGasComparator());
        showAll(cars);

        showAll(getByCriterion(cars, car -> car.getPassengers().size() == 2));
        showAll(getByCriterion(cars, Car.getFourPassengerCriterion()));

        List<String> colors = Arrays.asList("LightCoral", "pink", "Orange", "Gold", "plum", "Blue", "limegreen");

        showAll(getByCriterion(colors, st -> st.length() > 4));
        showAll(getByCriterion(colors, st -> Character.isUpperCase(st.charAt(0))));

        LocalDate today = LocalDate.now();
        List<LocalDate> dates = Arrays.asList(today, today.plusDays(1), today.plusDays(7), today.minusDays(1));

        showAll(getByCriterion(dates, ld -> ld.isAfter(today)));
    }
}
