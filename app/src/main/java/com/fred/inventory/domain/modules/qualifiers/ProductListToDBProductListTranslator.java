package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the domain product lists to db product lists dependency
 */
@Qualifier
@Documented
public @interface ProductListToDBProductListTranslator {
}
