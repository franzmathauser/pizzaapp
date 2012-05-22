package edu.hm.lip.pizza.internal.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Franz Mathauser, Stefan Wörner
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( value = ElementType.METHOD )
@Documented
public @interface DriverGPSActiveMQInterceptorMethodSelector
{

}
