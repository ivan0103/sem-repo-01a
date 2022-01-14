package nl.tudelft.sem.genericservicepost.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.services.EditGenericPostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/editgenericpost")
public class EditGenericPostController {

    private final transient String filterName = "postFilter";

    private final transient String userType = "user";

    transient EditGenericPostService moreService;
    /**
     * Edit post mapping jackson value.
     *
     * @param post   the post
     * @param postId the post id
     * @return the mapping jackson value
     */
    @PatchMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> editPost(
            @Valid @RequestBody GenericPost post,
            @RequestParam("genericPostId") String postId) {
        GenericPost editedPost = moreService.editGenericPost(post, postId);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(editedPost);
        mappingJacksonValue.setFilters(filterProvider);


        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(mappingJacksonValue);
    }
}