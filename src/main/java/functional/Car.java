package functional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


// 刚开始，从一个简单的类开始
// 一个 Car 类，代表小车
public class Car {
    private final int gasLevel;  // int 类型的 gasLevel 表示 汽油水平，即油量
    private final String color;  // String 类型的 color 表示 小车颜色，因为用 java.awt 里边的类没有什么卵用
    private final List<String> passengers; // 我们有一个乘客列表，String 类型的乘客名称
    private final List<String> trunkContents; // 然后潜在的车尾箱内容，booth 里边的东西，如果按我的说法的话


    // 一个私有的构造器，我们将会讲一下不同的构造器，已经构造器的替代。 这不是函数式编程的关键，但是一些函数式编程的风格的操作会用到工厂方法
    private Car(int gasLevel, String color, List<String> passengers, List<String> trunkContents) {
        this.gasLevel = gasLevel;
        this.color = color;
        this.passengers = passengers;
        this.trunkContents = trunkContents;
    }

    // 这是其中一个工厂方法，可以看到试用有意义名称的工厂方法，可以传递参数的名称，这是人们喜欢静态工厂的原因之一
    // 自从Java 8 发布以来，几乎所有新API都只使用静态工厂，而不是用公有构造器，异常除外
    public static Car withGasColorPassengers(int gas, String color, String... passengers) {
        // 可以看到乘客列表被包装成一个List，函数式编程一个喜好就是尽量使用不可修改数据（immutable data）
        // 通常更喜好创建一个新版本的某东西，而不是修改已存在的某东西，后面我们会讲多一点这方面的内容
        // 这里只是将乘客数组包装到一个不可以修改的列表里。👇
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        // 这意味着，当我们下面这里调用这个私有的构造器去创建Car对象之后，没人可以修改乘客列表。
        Car self = new Car(gas, color, p, null);
        return self;
        // 现在我们构建什么并不重要，但这种偏好不可修改数据的原则，是应该遵循的。
    }


    // 然后这里有另外一个版本的静态工厂，发现这跟上面的静态工厂的参数列表是一样的，如果是构造器，做不到这样。
    public static Car withGasColorPassengersAndTrunk(int gas, String color, String... passengers) {
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        // 不同的是，这个版本静态工厂方法，在调用私有构造器时添加了车尾箱的内容。
        Car self = new Car(gas, color, p, Arrays.asList("jack", "wrench", "spare wheel"));
        return self;
    }

    public int getGasLevel() {
        return gasLevel;
    }

    public String getColor() {
        return color;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public List<String> getTrunkContents() {
        return trunkContents;
    }

    @Override
    public String toString() {
        return "Car{" + "gasLevel=" + gasLevel + ", color=" + color + ", passengers=" + passengers
                + (trunkContents != null ? ", trunkContents=" + trunkContents : " no trunk") + '}';
    }


    public static Criterion<Car> getFourPassengerCriterion() {
        return car -> car.getPassengers().size() == 4;
    }

    public static Criterion<Car> getRedCarCriterion() {
//        return new RedCarCriterion();
        return RED_CAR_CRITERION;
    }

    private static final Criterion<Car> RED_CAR_CRITERION = car -> car.color.equals("Red");


//    private static final CarCriterion RED_CAR_CRITERION = new CarCriterion() {
//        @Override
//        public boolean test(Car car) {
//            return car.color.equals("Red");
//        }
//    };


//  private static final CarCriterion RED_CAR_CRITERION = new /*RedCarCriterion();
//
//    private static class RedCarCriterion implements */ CarCriterion() {
//    @Override
//    public boolean test(Car car) {
//      return car.color.equals("Red");
//    }
//  };

    public static Criterion<Car> getGasLevelCarCriterion(int threshold) {
        return new GasLevelCriterion(threshold);
    }

    private static class GasLevelCriterion implements Criterion<Car> {
        private int threshold;

        public GasLevelCriterion(int threshold) {
            this.threshold = threshold;
        }

        @Override
        public boolean test(Car car) {
            return car.gasLevel >= threshold;
        }
    }


    public static Comparator<Car> getCarGasComparator() {
        return gasComparator;
    }

    /*CarGasComparator();

    private static class CarGasComparator implements */
    private static final Comparator<Car> gasComparator = Comparator.comparingInt(Car::getGasLevel);

}
