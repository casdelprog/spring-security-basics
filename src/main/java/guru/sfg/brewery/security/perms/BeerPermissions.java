package guru.sfg.brewery.security.perms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeerPermissions {

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('beer.create')")
	@interface CreateEntity {
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('beer.read')")
	@interface ReadEntity {
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('beer.update')")
	@interface UpdateEntity {
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('beer.delete')")
	@interface DeleteEntity {
	}
}
