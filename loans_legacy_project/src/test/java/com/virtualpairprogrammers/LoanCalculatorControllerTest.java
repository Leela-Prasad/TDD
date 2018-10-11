package com.virtualpairprogrammers;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class LoanCalculatorControllerTest {

	@Spy
	private LoanApplication loanApplication;
	
	private LoanRepository data;
	private RestTemplate restTemplate;
	private JavaMailSender mailSender;
	@Before
	public void setUp() throws RestClientException, URISyntaxException {
		loanApplication = spy(new LoanApplication());
		
		data = mock(LoanRepository.class);
		restTemplate = mock(RestTemplate.class);
		when(restTemplate.postForLocation(eq("http://loans.virtualpairprogrammers.com/loanApplication"),any(LoanApplication.class)))
		.thenReturn(new URI("FakeLocation"));
		mailSender = mock(JavaMailSender.class);
	}
	
	@Test
	public void test1YearLoanWholePounds() {
		
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(12);
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		LoanCalculatorController controller = new LoanCalculatorController();
		controller.setData(data);
		controller.setRestTemplate(restTemplate);
		controller.setMailSender(mailSender);
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(110) , loanApplication.getRepayment());
		
	}
	
	@Test
	public void test2YearLoanWholePounds() {
		
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(24);
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		LoanCalculatorController controller = new LoanCalculatorController();
		controller.setData(data);
		controller.setRestTemplate(restTemplate);
		controller.setMailSender(mailSender);
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(60) , loanApplication.getRepayment());
		
	}
	
	@Test
	public void test5YearLoanWithRounding() {
		
		loanApplication.setPrincipal(5000);
		loanApplication.setTermInMonths(60);
		doReturn(new BigDecimal(6.5)).when(loanApplication).getInterestRate();
		
		LoanCalculatorController controller = new LoanCalculatorController();
		controller.setData(data);
		controller.setRestTemplate(restTemplate);
		controller.setMailSender(mailSender);
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(111) , loanApplication.getRepayment());
		
	}

}
