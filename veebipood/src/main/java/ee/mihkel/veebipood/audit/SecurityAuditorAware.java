package ee.mihkel.veebipood.audit;

import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class SecurityAuditorAware implements AuditorAware<Person> {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Optional<Person> getCurrentAuditor() {
        try {
            Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            return personRepository.findById(personId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
