package services;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 */
public class MapperService extends ModelMapper {

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> super.map(element, targetClass))
                .collect(Collectors.toList());
    }

}