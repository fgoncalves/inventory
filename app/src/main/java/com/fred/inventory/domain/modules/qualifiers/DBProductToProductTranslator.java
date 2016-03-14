package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the db product to domain product dependency
 */
@Qualifier
@Documented
public @interface DBProductToProductTranslator {
}
