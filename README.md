TDD means "Test drive the code"
Tests will drive writing code.

TDD process is
  RED -> GREEN -> REFACTOR
  1. A new test will initially fail.
  2. We work to get the test to pass.
  3. We finally optimize our code and tests.

Why we should always start with Failing Test?
This is to ensure that we are running the test which we created and we didn’t miss @Test annotation, which is likely to happen.
So to fail the test intentionally we will write 
fail() method as the first line in the test case.
(or)
sometimes if we don’t have assertions then failing tests also will pass, so to avoid this we need a failing test, then we will add minimum code to pass the test.

In TDD we will test only unit tests.
**Each test will cover a single scenario for a single piece of logic.


Rules of TDD
============
1. Test the expected outcome of an example.
Here we will say i need this outcome when running this test, we don't think about how it will be implemented.
When writing tests think about examples and outcomes not code and how it should work in detail.

2. Don’t prejudge design, let your tests drive it.
Decision of things like datatypes, class structure and architecture of whole system will change overtime as you write tests that determine what the requirements really are. 

3. write minimum code required to get your test to pass.
at this point we are not interested in building correct and full functionality, rather than minimum functionality to get this test pass.

4. Each test should validate ONE single piece of logic i.e., each test should have only one assert statement.
We can have multiple asserts in below 2 cases.
a) If we want to assert multiple variables for the same piece of logic.
   assertTrue(var1);
   assertTrue(var2);
b) when we test multiple values that are just different examples of the same thing.
   validateISBN(num1) - returns true
   validateISBN(num2) - returns true

Rules of Testing
————————————————
1. Test one item of functionality only 
   means we should have only assert in each test case.
   we can have multiple asserts when
   a) we are asserting multiple variables of the method that we are testing.
   b) when we are having same outcome of the method that we are testing to different input values.

2. Test business logic, not methods.
   this means we have to test method which have business logic, not the helper methods. for this method we can write suite of test cases to test different scenarios.
   If we have our test name and method name almost same then we are not testing the method  with different inputs.
   It also states that we don’t have one to one relationship with methods and test cases.

3. Tests must be repeatable and consistent.
   Even if we run tests at any time it should pass all test cases. 
   Inorder to achieve this  we need to mock methods which will interact with other systems.

4. Tests must be thorough.

What tests should I write to make tests thorough
————————————————————————————————————————————————
1. what should the logic be?
2. what is the opposite to that logic?
3. Are there any edge cases?
4. Are there any error conditions?

1. what should the logic be?
   we need to know what is the logic to determine a number is ISBN.
   tests with valid 10 digit ISBN
   tests with valid 13 digit ISBN.

2. what is the opposite to that logic?
   we need to know when the logic will fail. i.e., by changing check(last) digit isbn will become invalid.
   test with invalid 10 digit ISBN
   test with invalid 13 digit ISBN

3. Are there any edge cases?
   Ten Digit ISBN can have check(last) digit as X
   tests with valid 10 digit ISBN with last digit as X
   tests with Invalid 10 digit ISBN with last digit as X

4. Are there any error conditions?
   Test with invalid ISBN length.
   Tests with non numeric ISBN.

Stub:
Stub is a replacement for an object that the class we are testing that has dependency on external system.
we can inject this replacement into that class from our tests, it will override the dependency for the external system

Behaviour:
Calling an external system is known as behaviour.

In our example we are first checking in the database(which act as a cache) for the book
If it is available then book will returned from database.
If it is not available then book will call external web service and return the result.

@Test
public void databaseIsUsedIfDataIsPresent() {
    fail();
}
	
@Test
public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
    fail();
}

Using stub we cannot determine which is called or not called at runtime.

suppose 
1. if book is present in database then we can say 
database service is called and web service is not called.
2. if book is not present in database then we can say 
database service is called and web service is called.
3. If book is present in both database and web service
database service is called and web service is not called.

Here 2 case is tricky to know from which service book is returned
To check the behaviour(i.e., calling external service) using stubs we need to write lot of code which is not used in real world systems.
For this purpose we will use Mocks which will give handy methods to verify this behaviour.


MyClass myClass = mock(MyClass.class); //creating mock for MyClass

when(myClass.myMethod(params)).thenReturns(return-value); //setting dummy data

verify(cycles,times(?)).myMethod(params); //asserts whether myMethod(params) is called number of times specified in times(?) method.


times(?)
————————
times(0)
times(1)
atLeast(2)
atMost(4)
never()

If the times argument is not mentioned then the default value will be 1 

params
—————-
Here we can use like
anyString()
anyInteger()
andyObject()
For Custom Classes
any(Book.class)


Spy:
————
If we mock an object then all methods on the class will be mocked. so any method invocation will return null.
In some cases we need mock only specific methods then we need to use Spy.

How to create Spy.

@Spy
private LoanApplication loanApplication
loanApplication = spy(new LoanApplication());

doReturn(return_value).when(spy_object).method_call(params)
