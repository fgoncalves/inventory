package com.fred.inventory.domain.modules.qualifiers;

import java.lang.annotation.Documented;
import javax.inject.Qualifier;

/**
 * Qualifier for the db image to domain image dependency
 */
@Qualifier
@Documented
public @interface DBImageToImageTranslator {
}
