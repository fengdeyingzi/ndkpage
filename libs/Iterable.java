package java.lang;

public abstract interface Iterable<T extends java.lang.Object>
{
    public abstract java.util.Iterator<T> iterator();

    public void forEach(java.util.function.Consumer<? super T> p1) {}

    public java.util.Spliterator<T> spliterator() {}

}
