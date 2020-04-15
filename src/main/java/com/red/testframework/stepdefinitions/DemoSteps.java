package com.red.testframework.stepdefinitions;

import com.red.testframework.pageobjects.LoginPage;
import com.red.testframework.utils.Log;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;





public class DemoSteps extends BaseSteps {
	private LoginPage loginPage;

	public DemoSteps() {
		super();
		logger = Log.getLog(DemoSteps.class);
		loginPage = new LoginPage(driver);
	}
    

    
//    When I complete action
//    And some other action
//    And yet another action
//    Then I validate the outcomes
//    And check more outcomes
    
	@Given("I want to write a step with precondition")
	public void iWantToWriteAStepWithPrecondition() {
		Log.info("Given I want to write a step with precondition");
	}
    
    @And ("^some other precondition$")
    public void someOtherPrecondition(){
        Log.info("And some other precondition");
    }
    
    @When ("^I complete action$")
    public void completeAction(){
        Log.info(" When I complete action");
        loginPage.login();
    }
    
    @Then ("^I validate the outcomes$")
    public void validateTheOutcome(){
        Log.info("Then I validate the outcomes");
    }
    
    @And ("^check more outcomes$")
    public void checkMoreOutcomes(){
        Log.info("And check more outcomes");
        
       loginPage.verificationExample();
       
    }
	
}
