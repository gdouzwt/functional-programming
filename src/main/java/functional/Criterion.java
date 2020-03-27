package functional;

@FunctionalInterface
interface Criterion<E> {
    boolean test(E e);

    default Criterion<E> negate() {
        return x -> !this.test(x);
    }

    default Criterion<E> and(Criterion<E> second) {
        return x -> this.test(x) && second.test(x);
    }

    default Criterion<E> or(Criterion<E> second) {
        return x -> this.test(x) || second.test(x);
    }

//    static <E> Criterion<E> negate(Criterion<E> crit) {
//        return x -> !crit.test(x);
//    }
//
//    static <E> Criterion<E> and(Criterion<E> first, Criterion<E> second) {
//        return x -> first.test(x) && second.test(x);
//    }
//
//    static <E> Criterion<E> or(Criterion<E> first, Criterion<E> second) {
//        return x -> first.test(x) || second.test(x);
//    }
}