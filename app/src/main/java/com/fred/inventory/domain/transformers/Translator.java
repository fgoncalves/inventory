package com.fred.inventory.domain.transformers;

/**
 * Transform a model A into a model B.
 */
public interface Translator<A, B> {
  /**
   * Transform a model A into a model B
   *
   * @param model The model to be translated
   * @return The tranlated B model
   */
  B translate(A model);
}
