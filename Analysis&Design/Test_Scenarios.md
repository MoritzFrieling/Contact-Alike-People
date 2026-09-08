
## Create account
<details>
    <summary>All Test Szenarios in here, because there are quite a lot.</summary>
    
| Name: | Insufficient password.  |
|----|----|
| Precondition: | The Actor indicated that he wants to create an account. |
| Scenario: |1. The System changes the view to a window for the account login information, where the email adress and the password has to be set.  |
|  | 2. The Actor fills in "abc@web.de" as email adress and "Abcd1234" as password, confirms it by typing "Abcd1234" again and continues.  |
|  | 3. The System indicates that the password does not fulfill all of the requirements and specifically points out that no special characters were used.  |
| Results: | Actor goes back to the account login information and has to start over. |

| Name: | Passwords not equal  |
|----|----|
| Precondition: | The Actor indicated that he wants to create an account. |
| Scenario: |1. The System changes the view to a window for the account login information, where the email adress and the password has to be set.  |
|  | 2. The Actor fills in "abc@web.de" as email adress and "Abcd1234!" as password, confirms it by typing "Abcd1234!!" again and continues.  |
|  | 3. The System indicates that the passwords do not match.  |
| Results: | Actor goes back to the account login information and has to start over. |

| Name: | Email existing in the system.  |
|----|----|
| Precondition: | The Actor indicated that he wants to create an account. |
| Scenario: |1. The System changes the view to a window for the account login information, where the email adress and the password has to be set.  |
|  | 2. The Actor fills in "abc@web.de" as email adress and "Abcd1234!" as password, confirms it by typing "Abcd1234!" again and continues.  |
|  | 3. The System indicates that the email is already taken.  |
| Results: | Actor goes back to the account login information and has to start over. |

| Name: | Actor denies request to use the camera.  |
|----|----|
| Precondition: | The Actor already inserted his login information in the process of creating the account. |
| Scenario: |1. The System asks for permission to use the camera.  |
|  | 2. The Actor indicates that the permission to use the camera and the gallery will not be granted. |
|  | 3. The System informs the Actor that without the permission, the app can't be used.  |
| Results: | Actor goes back to the profile overview page, where he has to approve the request. |

| Name: | Required account overview information not filled out.  |
|----|----|
| Precondition: | The Actor already inserted his login information in the process of creating the account. |
| Scenario: |1. The System shows the window for personal information (Name, Age, Gender, bio) and the option to take a picture. |
|  | 2. The Actor indicates he wants to move on. |
|  | 3. The System indicates that no fields have been filled out and no picture has been taken. |
| Results: | Actor is taken back to the profile overview page to write down information and take the photo. |

| Name: | Location details empty.  |
|----|----|
| Precondition: |All details on the account overview pager were filled out. |
| Scenario: |1. The System changes the view to a window for location information(Streetname, Nr, City, Country, Postal Code). |
|  | 2. The Actor doesn't fill in aynthing. |
|  | 3. The System indicates that all fields need to be filled out to continue with the process of creating the account. |
| Results: | System takes back the Actor back to the location details |

| Name: | Location details incorrect. |
|----|----|
| Precondition: |All details on the account overview pager were filled out. |
| Scenario: |1. The System changes the view to a window for location information(Streetname, Nr, City, Country, Postal Code).  |
|  | 2. The Actor fills out "Kölner Straße" as street number, "A" as number, "Cologne" as city, "Germany" as country and "20023" as postal code |
|  | 3. The System indicates that the field with the number needs to contain at least one number to continue with the process of creating the account. |
| Results: | System takes back the Actor back to the location details |

| Name: | No username has been written down for a checked contact option.  |
|----|----|
| Precondition: |All location details were already written down. |
| Scenario: |1. The System lists different contact options (twitter, whatsapp, discord, reddit, LoL, ...), which can be chosen and the user name can be filled in.  |
|  | 2. The Actor checks the box "Twitter" but doesn't write down the username |
|  | 3. The System indicates that for a certain contact option, the user name is missing.|
| Results: | The Actor goes back to the contact options and has to chose one to continue with the profile creation. |

| Name: | No contact options were checked.  |
|----|----|
| Precondition: |All location details were already written down. |
| Scenario: |1. The System lists different contact options (twitter, whatsapp, discord, reddit, LoL, ...), which can be chosen and the user name can be filled in.  |
|  | 2. The Actor doesn't check any boxes with contact details |
|  | 3. The System indicates that checking at least contact option is necessary to continue with the process of creating the account.|
| Results: | The Actor goes back to the contact options and has to chose one to continue with the profile creation. |

| Name: | No hobby was chosen and no additional hobby was written down.  |
|----|----|
| Precondition: |At least one contact option has been filled out. |
| Scenario: |1. The System provides the Actor with a list of hobbies and a field for additional ones.  |
|  | 2. The Actor doesn't select a hobby  and doesn't write down a hobby on his own. |
|  | 3. The System indicates that chosing at least one hobby is necessary to continue with the process of creating the account. |
| Results: | Process will be put on hold as long as no hobby has been chosen. |

| Name: | Sucessfull account creation  |
|----|----|
| Precondition: | The Actor indicated that he wants to create an account. |
| Scenario: | 1. The System changes the view to a window for the account login information, where the email adress and the password has to be set. |
| | 2. The Actor fills in "abcdef@web.de" as his email adress, "1234Abcd!" as password, and confirms it by typing "1234Abcd!" again and continues. | 
| | 3. The System changes the view to a window for personal information (Name, Age, Gender, bio) and the option to take a picture. |
| | 4. The Actor enters "Moritz" and "Frieling" as first and last name, "19" as age, "Male" as gender and "Hey, I like trees" as bio. Then, he clicks on the button to take a picture. | 
| | 5. The System asks for permission to use the camera. |
| | 6. The Actor agrees to the usage of the camera. |
| | 7. The System changes to the camera. |
| | 8. The Actor [takes a picture](#Take-picture). |
| | 9. The System enables the user to edit the picture via the camera tool. |
| | 10. The Actor [edits the picture](#Edit-and-crop-picture) by cutting the edges a bit. |
| | 11. The System displays the photo and the personal information.
| | 12. The Actor  indicates he wants to move on.
| | 13. The System changes the view to a window for location information(Streetname, Nr, City, Country, Postal Code). |
| | 14. The Actor fills in "Kölner Straße" as street name, "Cologne" as city, "Germany" as country, and "12345" as postal code and continues. |
| | 15. The System lists different contact options (twitter, whatsapp, discord, reddit, LoL, ...), which can be chosen and the user name or number can be filled in. |
| | 16. The Actor checks "whatsapp" as a contact option and writes down the phone number "0179 1111111". |
| | 17. The System provides the Actor with a list of hobbies and a field for additional ones. |
| | 18. The Actor selects "tennis" as hobby fills out the time he played the sport with "10 years" and writes down a number to measure his skill, which is "16.0". |
| | 19. The System provides the Actor with an overview of all the information he filled in. | 
| | 20. The Actor indicates that the information are correct and he wants the account to be created |
| | 21. The System indicated that the account was created successfully and changes it's view to the log in page. |
| Results: | The Actor has been successfully registered and the profile is finished. |

</details>


## Log in
| Name: | Actor logs in with invalid combination of email and password |
|----|----|
| Scenario: |1. System enables the Actor to enter email and password. |
|  | 2. Actor enters abc@gmail.com (wrong mail adress) as email and qwer12!A as password. |
|  | 3. The System informs the Actor that the combination of email adress/password is invalid. |
| Results: | Actor was not logged in and has no access to the platform . |


| Name: | Actor logs in with valid combination of email and password |
|----|----|
| Scenario: |1. System enables the Actor to enter email and password. |
|  | 2. Actor enters abcefg@gmail.com as email and qwer12!A  as password. |
|  | 3. The System allows access to platform by switching the view to the map. |
| Results: | System logs the Actor in. |

## Log out
| Name: | Actor logs out of the platform. |
|----|----|
| Scenario: |1. The Actor indicates he wants to log out. |
|  | 	2. The System asks for confirmation. |
|  | 	3. The Actor confirms the log out request. |
| Results: | The Actor loses access to the platform and gets refered to the log in page. |


| Name: | Actor accidentally pressed on log out and doesn't want to do so. |
|----|----|
| Scenario: |1. The Actor indicates he wants to log out. |
|  | 	2. The System asks for confirmation. |
|  | 	3. The Actor denies the log out request. |
| Results: | The actor stil has access to the platform. |

## Take picture
| Name: | First photo will be used. |
|----|----|
| Scenario: |	1. The System asks the Actor to shoot a photo. |
|  | 2. The Actor makes a second photo of himself. |
|  | 3. The System asks the actor if this photo should be used. |
|  | 4. The Actor indicates that this photo should be used. |
|  | 5. The System returns to the account creation page. |
| Results: | A photo has been taken. |


| Name: | Second photo will be used. |
|----|----|
| Scenario: |	1. The System asks the Actor to shoot a photo. |
|  | 2. The Actor makes a photo of himself. |
|  | 3. The System asks the actor if this photo should be used. |
|  | 4. The Actor indicates that they want to shot a new photo. |
|  | 5. The System asks the Actor to shoot a photo. |
|  | 6. The Actor makes a second photo of himself. |
|  | 7. The System asks the actor if this photo should be used. |
|  | 8. The Actor indicates that this photo should be used. |
|  | 9. The System returns to the account creation page. |
| Results: | The actor was able to take a second photo and will use this to create his profile picture. |

## Edit and crop picture |
| Name: | Actor edits the picture |
|----|----|
| Scenario: |1. System displays the photo the actor just took.|
|  | 2.Actor makes the picture larger by cutting away the edges.|
|  | 3.System enables the user to chose a filter and adjust other setting regarding the color and saturation.|
|  | 4.Actor adjust the brightness, choses a filter and saves the picture.|
|  | 5. The system returns to the profile creation page.|
| Results: | The profile picture has been altered based on the ideas of the actor. |

## Send contact request
| Name: | Actor sends first contact request. |
|----|----|
| Scenario: |1. Actor is on the profile(overview) of "Heinz-Karl Becker" and indicates he wants to request the contact information. |
|  | 2. The system indicates that a request has been delivered to Heinz-Karl. |
| Results: | A successfull contact request has been sent.


| Name: | Actor sends a second request after a short period of time. |
|----|----|
| Scenario: |1. Actor is on the profile(overview) of "Heinz-Karl Becker" and indicates he wants to request the contact information. |
|  | 2. The system indicates that noequest has been delivered to Heinz-Karl, since the time between the requests was too low.
| Results: | No contact request has been sent and the actor needs to wait a bit until he can sent out the next contact request to this person.

## View user overview
| Name: | Actor sucessfully opens the user overview of another user |
|----|----|
| Scenario: |1. Actor is logged in and on the map view. |
|  | 2. System shows the map. |
|  | 3. Actor selects the user overviewe for the user "Karl-Heinz Peter" by clicking on the profile bubble. |
|  | 4. System opens the user overview for Karl-Heinz. |
| Results: | Sucessfull opening of the user overview. |

## Filter people around you
| Name: |  Filtering other users on map based on different criteria shows users. |
|----|----|
| Scenario: |1. System shows the user the map (with predefined range filter, 100km?). |
|  | 2. User indicates he wants to alter the filter. |
|  | 3. System opens window to change filter. |
|  | 4. User changes range to 300km. |
|  | 5. System shows the 5 people within this range. |
| Results: | Users on map got filtered. |

| Name: |  Filtering other users on map based on different criteria does not show users (stil works). |
|----|----|
| Scenario: |1. System shows the user the map (with predefined range filter, 100km?). |
|  | 2. User indicates he wants to alter the filter. |
|  | 3. System opens window to change filter. |
|  | 4. User changes range filter to 50. |
|  | 5. System shows no users since there are no users that closeby. |
| Results: | Users on map got filtered, but no users are that close. |

## View own profile
| Name: | User views his own profile works |
|----|----|
| Scenario: |1. User indicates he wants to view his own profile by clicking on the profile icon in the bottom bar. |
|  | 2. System changes view to the own profile. |
| Results: | User can successfully view own profile. |

## Edit profile
| Name: | Edit profile does not work |
|----|----|
| Scenario: |1. User indicates he wants to edit his profile. |
|  | 2. System enables the user to change, add or delete information. |
|  | 3. User edits the profile by adding an additional interest, but doesn't indicate the level and saves the changes. 
|  | 4. System informs the User that the changes have not been saved and indicates that for one interest, the skill level is missing.  |
|  | 5. The system returns to the step where the user can change information. |

| Name: | Edit profile successfull |
|----|----|
| Scenario: |1. User indicates he wants to edit his profile. |
|  | 2. System enables the user to change, add or delete information. |
|  | 3. User edits the profile by changing the Description to "Hi! I am Moritz.". |
|  | 4. System indicates that all changes have been saved. |
| Results: | Profile was edited. |
