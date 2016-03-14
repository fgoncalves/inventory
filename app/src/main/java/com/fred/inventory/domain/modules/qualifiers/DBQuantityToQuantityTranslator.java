package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the db quantity to domain quantity dependency
 */
@Qualifier
@Documented
public @interface DBQuantityToQuantityTranslator {
}
