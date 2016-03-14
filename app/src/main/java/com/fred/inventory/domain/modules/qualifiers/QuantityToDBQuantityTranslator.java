package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the domain quantity to db quantity dependency
 */
@Qualifier
@Documented
public @interface QuantityToDBQuantityTranslator {
}
