package functional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarScratch {
  
  public static <E> void showAll(List<E> lc) {
    for (E c : lc) {
      System.out.println(c);
    }
    System.out.println("-------------------------------------");
  }
  
  public static <E> List<E> filter(Iterable<E> lc, Criterion<E> criterion) {
    List<E> rv = new ArrayList<>();
    for (E c : lc) {
      if (criterion.test(c)) {
        rv.add(c);
      }
    }
    return rv;
  }

  public static void main(String[] args) {
    List<Car> cars = Arrays.asList(
        Car.withGasColorPassengers(6, "Red", "Fred", "Jim", "Sheila"),
        Car.withGasColorPassengers(3, "Octarine", "Rincewind", "Ridcully"),
        Car.withGasColorPassengers(9, "Black", "Weatherwax", "Magrat"),
        Car.withGasColorPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
        Car.withGasColorPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
    );
    showAll(cars);
    
    showAll(filter(cars, Car.getRedCarCriterion()));
    
    showAll(filter(cars, Car.getGasLevelCarCriterion(7)));
    
    cars.sort(Car.getFuelComparator());
    showAll(cars);
    
    showAll(filter(cars, Car.getFourPassengerCriterion()));
    
    showAll(filter(cars, c -> c.getPassengers().size() < 3));
   
    List<String> words = Arrays.asList("LightCoral", "pink", "Orange", "Gold", "plum", "Blue", "limegreen");
    System.out.println("Long color names:");
    showAll(filter(words, w -> w.length() > 4));
    System.out.println("Capitalized color names:");
    showAll(filter(words, w -> Character.isUpperCase(w.charAt(0))));
    
    LocalDate today = LocalDate.now();
    List<LocalDate> appointments = Arrays.asList(today, today.plusDays(2), today.minusDays(4), today.plusMonths(1));
    System.out.println("All appointments");
    showAll(appointments);
    System.out.println("Future appointments after: " + today);
    showAll(filter(appointments, a -> a.isAfter(today)));
    
    System.out.println("Red Cars:");
    showAll(filter(cars, Car.getColorCriterion("Red")));

    System.out.println("Red or Black Cars:");
    showAll(filter(cars, Car.getColorCriterion("Red", "Black")));
    
    Criterion<Car> sevenPlus = Car.getGasLevelCarCriterion(7);
    System.out.println("Fuel >= 7");
    showAll(filter(cars, sevenPlus));
    System.out.println("Not Fuel >= 7");
    showAll(filter(cars, Criterion.negate(sevenPlus)));
    
    System.out.println("Four passengers or Black");
    showAll(filter(cars, Criterion.or(Car.getFourPassengerCriterion(), Car.getColorCriterion("Black"))));

    System.out.println("Four passengers and Red");
    showAll(filter(cars, Criterion.and(Car.getFourPassengerCriterion(), Car.getColorCriterion("Red"))));
  }
}
