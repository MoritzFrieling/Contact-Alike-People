# Use Cases

## List of Use Cases
| User Story | Use Case | extends/ implements |
|----|----|----|
| Template | [Template Use Case](#Template-Use-Case) | - |
| General| [Log in](#Log-in) | - |
| | [Log out](#Log-out) | - |
| US1: Account Creation | [Create account](#Create-account) | - |
| | [Take picture](#Take-picture) | extends "Create account" |
| | [Edit and crop picture](#Edit-and-crop-picture) | extends "Create account" |
| US2: See other people | [View user overview](#View-user-overview) | - |
| US3: Filter people around you| [Filter people around you](#Filter-people-around-you) | - |
| US5: Contact other people | [Send contact request](#Send-contact-request) | extends "View user profile" |
| | [Directly answer to contact request](#Directly-answer-to-contact-request) ||
| US7: View notifictations | [View notifications](#View-notifications) | - |
| | [Answer to contact request within notifications](#Answer-to-contact-request-within-notifications) | extends "View notifications" |
| US8: Edit account | [View own profile](#View-own-profile) | - |
| | [Edit account](#Edit-account) | extends "View own profile" |
| | [Set visibility](#Set-visibility) | extends "Edit account" |

### Create account 
| Name: | _1.	Create an account with personal information._ |
|----|----|
| Actor: | User | 
| Description: | The actor wants to register himself. |
| Pre-condition: | The Actor indicated that he wants to create an account. |
| Scenario: | 1. The System changes the view to a window for the account login information, where the email adress and the password has to be set. |
| | 2. The Actor fills in his email adress and enters a password, confirms it by typing it again and continues. | 
| | 3. The System changes the view to a window for personal information (Name, Age, Gender, bio) and the option to take a picture. |
| | 4. The Actor enters the information and indicates he wants to take a picture. | 
| | 5. The System asks for permission to use the camera. |
| | 6. The Actor agrees to the usage of the camera. |
| | 7. The System changes to the camera. |
| | 8. The Actor [takes a picture](#Take-picture). |
| | 9. The System enables the user to edit the picture via the camera tool. |
| | 10. The Actor [edits the picture](#Edit-and-crop-picture). |
| | 11. The System displays the photo and the personal information.
| | 12. The Actor  indicates he wants to move on.
| | 13. The System changes the view to a window for location information(Streetname, Nr, City, Country, Postal Code). |
| | 14. The Actor fills in the information and continues. |
| | 15. The System lists different contact options (twitter, whatsapp, discord, reddit, LoL, ...), which can be chosen and the user name can be filled in. |
| | 16. The Actor checks (at least one) contact option and writes down his user name |
| | 17. The System provides the Actor with a list of hobbies and a field for additional ones. |
| | 18. The Actor selects a hobby (hobbies) and can write in the field, if there are hobbies not on the list. |
| | 19. The System provides the Actor with an overview of all the information he filled in. | 
| | 20. The Actor indicates that the information are correct and he wants the account to be created |
| | 21. The System indicated that the account was created successfully and changes it's view to the log in page. | 
| Results: | The Actor has been successfully registered. |
| Exceptions: | 3.a. Password is insufficient. |
| | 3.a.1. The System indicates that the password does not fulfill all of the requirements ( at least length of 8, at least one special char, one lowercase, one uppercase, on number) and shows which ones are necessary. |
| | 3.a.2. Return to step 1. |
| | 3.b. Passwords are not equal. |
| | 3.b.1. The System indicates that the passwords are not the same. |
| | 3.b.2. Return to step 1. |
| | 3.c. Email is already existing. |
| | 3.c.1. The System indicates that the email adress is sued for an account already. |
| | 3.c.2. Return to step 1. |
| | 6.a The user denies the access request (camera, gallery). |
| | 6.a.1. The Actor indicates that the permission to use the camera and the gallery will not be granted. |
| | 6.a.2. The System informs the Actor that without the permission, the app can't be used. |
| | 6.a.3. Return to step 5. |
| | 12.a. Not all required fields have been filled correctly ( no picture taken, any empty fields)  . |
| | 12.a.1 The System indicates which fields need to be filled (differently) to continue with the process of creating the account. |
| | 12.a.2 Return to step 4. |
| | 15.a. Not all fields have been filled. |
| | 15.a.1 The System indicates which fields need to be filled to continue with the process of creating the account. |
| | 15.a.2 Return to step 13. |
| | 15.b. Fields have been filled incorrectly (Postal Code with letters or sth like this). |
| | 15.b.1 The System indicates which fields need to be filled differently to continue with the process of creating the account. |
| | 15.b.2 Return to step 13. |
| | 17.a. No contact option was checked. |
| | 17.a.1 The System indicates that checking at least contact option is necessary to continue with the process of creating the account. |
| | 17.a.2 Return to step 15. |
| | 17.b. No username present for a checked contact option. |
| | 17.b.1 The System indicates that for a certain contact option, the user name is missing. |
| | 17.b.2 Return to step 15. |
| | 19.a. No hobby was chosen from the list and the extra field was left empty. |
| | 19.a.1 The System indicates that chosing at least one hobby is necessary to continue with the process of creating the account. |
| | 19.a.2 Return to step 17. |

### Log in
| Name: | _2. Log in_ |
|----|----|
| Actor: | User |
| Description: | The Actor logs into the platform. |
| Pre-condition: | The Actor is already registered.  |
| Scenario: |1. The System displays a login page with fields for the email adress and password. |
|  | 2. The Actor enters a email adress and password. |
|  | 3. The System allows access to platform by switching the view to the map. |
| Results: | The Actor gets access to platform. |
| Exceptions: | 3.a. The System informs the Actor that the combination of email adress/password is invalid. |
|  | 3.a.1. Return to step 1.  |

### Log out
| Name: | _3. Log out_ |
|----|----|
| Actor: | User |
| Description: | The Actor logs out of the platform. |
| Pre-condition: | -  |
| Scenario: |1. The Actor indicates he wants to log out|
|  | 2. The System  asks for confirmation. |
|  | 3. The Actor confirms the log out request. |
| Results: | The Actor loses access to platform and gets refered to the log in page. |
| Exceptions: | 3.a. The Actor decides to not log out. |
|  | 3.a.1. The Actor denies the log out request.  |
|  | 3.a.2. Log out didn't go through and the Use Case ends here.  |

### Take picture 
| Name: | _4. Take picture_ |
|----|----|
| Actor: | User | 
| Description: | The Actor takes a picture of himself. |
| Pre-condition: | The Actor allowed the usage of the camera and the gallery. |
| Scenario: | 1. The System asks the Actor to shoot a photo. |
| | 2. The Actor makes a photo of himself. |
| | 3. The System asks the actor if this photo should be used. |
| | 4. The Actor indicates that this photo should be used. |
| | 5. The System returns to the account creation page. |
| Results: | The Actor successfully shot a photo of himself. |
| Extension: | 4.a. The Actor indicates that they want to shot a new photo. |
| | 4.a.1. Go to step 1. |

### Edit and crop picture
| Name: | _5. Edit and crop the pictures_ |
|----|----|
| Actor: | User | 
| Description: | The Actor wants to edit the photo to make it better. |
| Pre-condition: | A photo has been shot. |
| Scenario: | 1. The System enables the Actor to crop the picture. |
| | 2. The Actor crops the picture. |
| | 3. The System enables the Actor to choose a filter (and change some colour settings ...). |
| | 4. The Actor edits the picture further and saves the picture. |
| | 5. The System returns to the account creation page. |
| Results: | The picture has been successfully croped and edited. |

### Send contact request
| Name: | _6. Send contact request_ |
|----|----|
| Actor: | User |
| Description: |  Actor asks the other user to share contact information. |
| Pre-condition: | User profile of another user is open.  |
| Scenario: |1. Actor indicates that he wants to request contact information of the person. |
|  | 2. System notifies the actor that a request has been sent. |
| Results: | A contact request has been sent to the other user. |
| Exceptions: | 2.a. Multiple contact requests to the same person, last contact request less than 5 minutes ago. |
|  | 2.a.1. The System informs the user tht the contact request was not successful since there was a recent contact reest.  |
|  | 2.a.2. Use Case ends here.  |

### View user overview
| Name: | _7. View user overview_ |
|----|----|
| Actor: | User |
| Description: | Viewing amother user's information over the map. |
| Pre-condition: | An actor is logged in and is on the main page (with the map).  |
| Scenario: |1. Actor clicks on another user on the map. |
|  | 2. System opens the oveview of the person. |
| Results: | Window with information about the other user is open. |

### Filter people around you
| Name: | _8. Filter people around you_ |
|----|----|
| Actor: | User |
| Description: |  Filter other users on map based on different criteria. |
| Pre-condition: | User needs to be logged in.  |
| Scenario: |1. System shows the user the map (with predefined range filter, 100km?). |
|  | 2. User indicates he wants to alter the filter. |
|  | 3. System opens window to change filter. |
|  | 4. User changes range filter and/or interest filter (and/or social filter). |
|  | 5. System shows only certain users on the map, based on the filtering criteria. |
| Results: | Users on map got filtered. |

### View own profile
| Name: | _9. View own profile_ |
|----|----|
| Actor: | User |
| Description: |  User views his own profile. |
| Pre-condition: | User is logged in.  |
| Scenario: |1. User indicates he wants to view his own profile by clicking on the profile icon in the bottom bar. |
|  | 2. System changes view to the own profile. |
| Results: | User can successfully view own profile. |

### Edit profile
| Name: | _10. Edit profile_ |
|----|----|
| Actor: | User |
| Description: |  Edit own profile. |
| Pre-condition: | User is on the personal profile overview.  |
| Scenario: |1. User indicates he wants to edit his profile. |
|  | 2. System enables the user to change, add or delete information. |
|  | 3. User edits the profile and saves the changes. |
|  | 4. System indicates that all changes have been saved. |
| Results: | Profile was edited. |
| Exceptions: | 4.a. Profile information not correct (Costraints just like in profile creation). |
|  | 4.a.1. System informs the User that the changes have not been saved and indicates which information are incorrect.  |
|  | 4.a.2. Return to step 2. |

### Template Use Case
| Name: | _X. Template UC_ |
|----|----|
| Actor: | User |
| Description: |  aaaa. |
| Pre-condition: | aaaa.  |
| Scenario: |1. aaaa. |
|  | 2. aaaa. |
|  | 3. aaaa. |
| Results: | aaaa. |
| Extension: | X.a. aaaa. |
|  | X.a.1. aaaa.  |
| Exceptions: | X.a. aaaa. |
|  | X.a.1. aaaa.  |
