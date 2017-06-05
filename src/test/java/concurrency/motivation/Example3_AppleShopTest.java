package concurrency.motivation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by tom on 03.06.2017.
 */
@WebMvcTest(controllers = {Example3_AppleShop.class})
@RunWith(SpringRunner.class)
public class Example3_AppleShopTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void buyingApples() throws Exception {
        mvc.perform(post("/appleShop/grannySmith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("gs_1")));
    }

}