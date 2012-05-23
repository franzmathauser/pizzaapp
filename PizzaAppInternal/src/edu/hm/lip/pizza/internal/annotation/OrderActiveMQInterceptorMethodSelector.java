package edu.hm.lip.pizza.internal.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation welche an Methoden angebracht wird die vom
 * {@link edu.hm.lip.pizza.internal.interceptor.OrderActiveMQInterceptor} berücksichtigt werden sollen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( value = ElementType.METHOD )
public @interface OrderActiveMQInterceptorMethodSelector
{

}
