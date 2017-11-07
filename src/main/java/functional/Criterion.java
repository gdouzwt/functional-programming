package functional;

@FunctionalInterface
public interface Criterion<E> {
  boolean test(E c);  

  public static <E> Criterion<E> negate(Criterion<E> crit) {
    return x -> !crit.test(x);
  }
  
  public static <E> Criterion<E> and(Criterion<E> first, Criterion<E> second) {
    return x -> first.test(x) && second.test(x);
  }
  
  public static <E> Criterion<E> or(Criterion<E> first, Criterion<E> second) {
    return x -> first.test(x) || second.test(x);
  }
}

