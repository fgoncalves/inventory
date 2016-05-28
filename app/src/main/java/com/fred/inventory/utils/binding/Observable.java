package com.fred.inventory.utils.binding;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that can be observed using {@link Observer}
 * <p/>
 * Created by fred on 28.05.16.
 */
public class Observable<T> {
  private final List<Observer<T>> observers = new ArrayList<>();
  private T value;

  private Observable() {
  }

  /**
   * Create an observable object for a given type
   *
   * @return An observable ready to observe.
   */
  public static <X> Observable<X> create() {
    return new Observable<>();
  }

  /**
   * Bind the observer to this observable
   *
   * @param observer The observer to add
   * @return The updated observable with the bound observer
   */
  public Observable<T> bind(Observer<T> observer) {
    this.observers.add(observer);
    return this;
  }

  /**
   * Unbind the observer from this observable
   *
   * @param observer The observer to unbind
   * @return The observable with the observer unbound
   */
  public Observable<T> unbind(Observer<T> observer) {
    observers.remove(observer);
    return this;
  }

  /**
   * Set the value of this observable and notify it's observers.
   *
   * @param value The value to set
   */
  public void set(T value) {
    this.value = value;
    notifyObservers();
  }

  /**
   * Notify all observers of the new value
   */
  private void notifyObservers() {
    for (Observer<T> observer : observers)
      observer.update(value);
  }
}
