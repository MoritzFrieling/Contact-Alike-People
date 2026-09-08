package com.project.cap.Logic;

/**
 * TODO adjust tests to async
 *
 */
public class LogicControllerTest {

    /*LogicController controller;
    UserReference invalidUser;

    public LogicControllerTest() {
        controller = LogicController.getInstance();
        invalidUser = new UserReference();
    }

    @Before
    public void setUp() {
        //controller = LogicController.getInstance();
        invalidUser.setEmail("philipKuentge@aol.com");
        //invalidUser.setUsername("Legende");
        invalidUser.setPassword("gsjsnagDDD1");
    }

    @Test
    public void registerUserInvalidPasswordTest() {

        Boolean result = null;
        try {
            result = LogicController.getInstance().getUserManagement().registerUserAsync(invalidUser).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }

    @Test
    public void registerUserInvalidEmailTest() {
        invalidUser.setPassword("Sich3r3s!Passwort");
        invalidUser.setEmail("Aber kack email");
        Boolean result = null;
        try {
            result = LogicController.getInstance().getUserManagement().registerUserAsync(invalidUser).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(result);
    }*/

    //Activate as soon as login-service exists. Validation itself works!
    /*
    @Test
    public void registerUserValidInputTest() {
        UserReference validUser = new UserReference();
        validUser.setEmail("legend@fontys.de");
        validUser.setPassword("Sup3rS1cher!!!");
        assertTrue(LogicController.registerUser(validUser));
    }
    */
}