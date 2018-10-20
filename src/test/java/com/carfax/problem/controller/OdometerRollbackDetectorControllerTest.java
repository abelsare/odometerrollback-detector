package com.carfax.problem.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.carfax.problem.service.OdometerRollbackDetectorServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link OdometerRollbackDetectorController}
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OdometerRollbackDetectorController.class)
public class OdometerRollbackDetectorControllerTest {
	
	private static final String URL = "/carfax/analyze/odometer-rollback?vin=";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OdometerRollbackDetectorServiceImpl odometerRollbackDetectorService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetOkResponse() throws Exception {
		String requestUrl = URL + "123";
		
		mockMvc.perform(get(requestUrl))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testResponseBlankVin() throws Exception {
		mockMvc.perform(get(URL))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testResponseNoVin() throws Exception {
		mockMvc.perform(get("/carfax/analyze/odometer-fallback"))
			.andExpect(status().isNotFound());
	}

}
