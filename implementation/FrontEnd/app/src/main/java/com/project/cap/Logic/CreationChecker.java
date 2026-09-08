package com.project.cap.Logic;

public class CreationChecker {

    /**
     * Check if the email is already registered to a UserReference. True if its available
     * @param mail
     * @return
     */
    public boolean checkMailAvailable(String mail){
        try{
            return LogicController.getInstance().getFutureGateway().isEmailAvailableAsync(mail).get();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if the password fulfills the requirements.
     * @param pw
     * @return
     */
    public boolean passwordCriteria(String pw){
        return pw.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[+#?!@$%^&*-]).{8,}$");
    }

    /**
     * Makes sure that the email format is correct.
     * @param email
     * @return
     */
    public boolean emailCriteria(String email){
        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    /**
     * Checks if the two strings are the same./ Contain the same character sequence.
     * @param pw
     * @param pw2
     * @return
     */
    public boolean passwordMatchesPassword(String pw, String pw2){
        return pw.contentEquals(pw2);
    }

}
