package fortumo.tests;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class ExampleTest extends TestCase {
	
    private MockMvc mockMvc;
    
/*	 @Autowired
	 MerchantMessageSender merchantMessageSender;
	 */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("Set Up Complete.");
    }
 
    @Test
    public void testMethod()  throws Exception{
  /*  	mockMvc.perform(get("/"))
        .andExpect(status().isOk());*/
        System.out.println("Sample test Successful");
    }
}
