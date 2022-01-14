package nl.tudelft.sem.genericservicepost.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Collection;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;



@Service
public class GenericPostHelper {
    /**
     * Helper method for filtering out a specific usertype when serializing.
     *
     * @param filterName the name of the filter
     * @param userType the user type you want to filter away
     * @return a SimpleFilterProvider with the specific filter applied
     */
    public FilterProvider filter(String filterName, String userType) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept(userType);
        return new SimpleFilterProvider()
                .addFilter(filterName, simpleBeanPropertyFilter);
    }

    /**
     * Helper method for mapping a post to Jackson value.
     *
     * @param post the post
     * @param filterName the name of the filter
     * @param userType the user type you want to filter away
     * @return the corresponding Jackson value
     */
    public MappingJacksonValue mappingJacksonPost(
            GenericPost post,
            String filterName,
            String userType) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(post);
        mappingJacksonValue.setFilters(filter(filterName, userType));
        return mappingJacksonValue;
    }

    /**
     * Helper method for mapping a post collection to Jackson value.
     *
     * @param collection the collection
     * @param filterName the name of the filter
     * @param userType the user type you want to filter away
     * @return the corresponding Jackson value
     */
    public MappingJacksonValue mappingJacksonCollection(
            Collection<GenericPost> collection,
            String filterName,
            String userType) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(collection);
        mappingJacksonValue.setFilters(filter(filterName, userType));
        return mappingJacksonValue;
    }
}