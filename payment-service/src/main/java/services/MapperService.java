package services;

import org.modelmapper.ModelMapper;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */
@ApplicationScoped
public class MapperService extends ModelMapper {

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> super.map(element, targetClass))
                .collect(Collectors.toList());
    }

}