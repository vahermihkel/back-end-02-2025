package ee.mihkel.veebipood.annotations;

import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Log4j2
public class CurrentPersonArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info(parameter);
        return parameter.hasParameterAnnotation(CurrentPerson.class) &&
                parameter.getParameterType().equals(Person.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        log.info(personId);
        return personRepository.findById(personId).orElseThrow();
    }
}
