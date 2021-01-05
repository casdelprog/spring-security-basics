package guru.sfg.brewery.security.perms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeerOrderPermissions {

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('order.create') OR " +
	        "hasAuthority('customer.order.create') " +
	        " AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId )")
	@interface CreateEntity {
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('order.read') OR " +
	        "hasAuthority('customer.order.read') " +
	        " AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId )")
	@interface ReadEntity {
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('order.pickup') OR " +
	        "hasAuthority('customer.order.pickup') " +
	        " AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId )")
	@interface Pickup {
	}
}
