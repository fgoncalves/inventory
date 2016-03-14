package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the db product lists to domain product lists dependency
 */
@Qualifier
@Documented
public @interface DBProductListToProductListTranslator {
}
