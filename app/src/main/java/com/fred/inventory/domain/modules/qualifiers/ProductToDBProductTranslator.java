package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the domain product to db product dependency
 */
@Qualifier
@Documented
public @interface ProductToDBProductTranslator {
}
