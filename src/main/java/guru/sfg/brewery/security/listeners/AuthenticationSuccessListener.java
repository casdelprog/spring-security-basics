package guru.sfg.brewery.security.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import guru.sfg.brewery.domain.security.User;

@Slf4j
@Component
public class AuthenticationSuccessListener {

	@EventListener
	public void listen(AuthenticationSuccessEvent event) {
		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
			if (token.getPrincipal() instanceof User) {
				User loggedUser = (User) event.getAuthentication().getPrincipal();
				log.debug("Logged with user: {}.", loggedUser.getUsername());
			}
			if (token.getDetails() instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();

				log.debug("Source IP: " + details.getRemoteAddress());
			}
		}
	}
}