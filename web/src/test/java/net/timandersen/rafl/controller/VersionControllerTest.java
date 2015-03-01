package net.timandersen.rafl.controller;

import net.timandersen.rafl.testhelper.WebApplicationContextTestCase;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class VersionControllerTest extends WebApplicationContextTestCase {

    @Test
    public void testShowVersion() throws Exception {
        MockHttpServletResponse actualResponse = mockMvc.perform(
                get("/version"))
                .andReturn()
                .getResponse();

        assertThat(actualResponse.getStatus(), is(MockHttpServletResponse.SC_OK));
    }


}