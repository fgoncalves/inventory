package com.fred.inventory.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Yet another lightweight version of guava's collection methods. This class implements functions
 * like filter and transform. Naturally some compromises were made and the lazy evaluation from
 * guava was not ported.
 * <p/>
 * Created on 26/11/15.
 */
public class IterableUtils {

  /**
   * Define an interface for the filtering methods to be used with the filter function
   *
   * @param <T> The object type of the elements in the collection being filtered
   */
  public interface Filter<T> {
    /**
     * Apply a filter to an element in a collections. If the element passes the filter than
     * it's included in the final collection.
     *
     * @param element The element to apply the filter
     * @return True if the element is to be included in the final collection. False otherwise
     */
    boolean filter(final T element);
  }

  /**
   * Define a mapping function from an object of type A to an object of type B
   *
   * @param <A> The type of the object to map from
   * @param <B> The type of the object to map to
   */
  public interface Mapper<A, B> {
    /**
     * Map the object A to an object B
     *
     * @param element The element to be mapped
     * @return The result of mapping the object
     */
    B map(final A element);
  }

  /**
   * Define a reducing function for an iterable of objects of type T
   *
   * @param <T> The type of the objects contained in the iterable
   */
  public interface Reducer<T> {
    /**
     * Apply the reducing function to the given arguments
     *
     * @param one The previous element found in the iterable
     * @param two The current element found in the iterable
     * @return The element to be considered in the next step of reducing
     */
    T reduce(final T one, final T two);
  }

  /**
   * Filter a list with the given filter function. If all elements are filtered out, then the
   * result list will be empty. This method doesn't mess with the order of the elements.
   *
   * @param toFilter The list to be filtered
   * @param <T> The object type of the elements in the list
   * @return The filtered list.
   */
  public static <T> List<T> filter(List<T> toFilter, Filter<T> filterFunction) {
    ArrayList<T> filtered = new ArrayList<>();
    for (T element : toFilter) {
      if (filterFunction.filter(element)) filtered.add(element);
    }
    return filtered;
  }

  /**
   * Map the given list of elements of type A to a list of elements of type B. This method
   * doesn't change the order of the elements.
   *
   * @param toMap The list to map
   * @param mappingFunction The mapping function
   * @param <A> The type of the object being mapped
   * @param <B> The type of the object to map to
   * @return A list with all the elements mapped
   */
  public static <A, B> List<B> map(List<A> toMap, Mapper<A, B> mappingFunction) {
    List<B> mapped = new ArrayList<>();
    for (A element : toMap)
      mapped.add(mappingFunction.map(element));
    return mapped;
  }

  /**
   * Reduce the given list to an element
   *
   * @param toReduce The list to be reduced
   * @param reducingFunction The reducing function
   * @param <T> The type of the elements in the list to be reduced
   * @return The element resulting of the reduce operation
   * @throws IllegalArgumentException If the list to be reduce is empty
   */
  public static <T> T reduce(List<T> toReduce, Reducer<T> reducingFunction) {
    if (toReduce.isEmpty()) throw new IllegalArgumentException("Trying to reduce an empty list");

    if (toReduce.size() == 1) return toReduce.get(0);

    T result = toReduce.get(0);
    List<T> subList = toReduce.subList(1, toReduce.size());
    for (T element : subList) {
      result = reducingFunction.reduce(result, element);
    }

    return result;
  }
}
