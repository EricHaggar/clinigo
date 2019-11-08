# Project-Camel

Our team developed an app to address the need for people to know wait times at nearby walk-in clinics without having to leave their home. It also allows users to know the services offered by nearby walk-in clinics and allow them to check-in/book appointments at the clinic of their choice. 

We present to you: `Clinigo`

<p align="center">
    <img src="assets/clinigo_logo.PNG" height="400" /> 
</p>

## Team Members

<center>

| Name | Student Number |
| --- | --- |
| Eric Haggar | 7674509 |
| Adel Araji | 7897476 |
| Mark Bastawros | 8123595 |
| Lev Guzman Aparicio   |  300038033 |
| Siraj Ghassel   |  8168653 |

</center>

## Table Of Contents

- [Project-Camel](#project-camel)
  - [Team Members](#team-members)
  - [Table Of Contents](#table-of-contents)
  - [Deliverable 1](#deliverable-1)
    - [UML Diagram](#uml-diagram)
    - [Activities Screenshots](#activities-screenshots)
  - [Deliverable 2](#deliverable-2)
    - [Admin Account](#admin-account)
    - [UML Diagram](#uml-diagram-1)
    - [Activities Screenshots](#activities-screenshots-1)

## Deliverable 1

The first deliverable concentrates on creating accounts and storing them into a database (Firebase).
Only one admin account exists but many patient and employee accounts can be created at will.
Each user has a first name, last name, email and password.

**Please Note:** Emails were used for login to work with Firebase Authentication.

### UML Diagram

The UML diagram for this deliverable is shown below:

<p align="center">
    <img src="screenshots/UML_Deliverable01.png"/> 
</p>

### Activities Screenshots

We took multiple screenshots to demonstrate the different features and their functionality.

1. Home Activity

<p align="center">
    <img src="screenshots/home.png" height="400" /> 
</p>

2. Sign Up Activities
   
<p align="center">
    <img src="screenshots/sign_up.png" height="400" /> 
    <img src="screenshots/sign_up_welcome.png" height="400" /> 
</p>

1. Login Activities

<p align="center">
    <img src="screenshots/login.png" height="400" />
    <img src="screenshots/login_welcome.png" height="400" /> 
</p>

4. Admin Example 

<p align="center">
    <img src="screenshots/admin_login.png" height="400" />
</p>

## Deliverable 2

The second deliverable concentrates on the admin features. The admin can create and edit services as well as delete accounts (walk-in clinic employees and patients). 

`CircleCI` was utilized to run unit tests and builds can be found [here](https://circleci.com/gh/professor-forward)

### Admin Account

There is only 1 admin account and the credentials are:

```
Email: admin@admin.com
Password: 5T5ptQ
```

### UML Diagram

The UML diagram for this deliverable is shown below:

<p align="center">
    <img src="screenshots/UML_Deliverable02.png"/> 
</p>

### Activities Screenshots

1. Admin Homepage 

<p align="center">
    <img src="screenshots/admin_home_page.png" height="400" /> 
</p>
    
2. Manage Services Activity

<p align="center">
    <img src="screenshots/list_service.png" height="400" /> 
    <img src="screenshots/add_services.png" height="400" />
    <img src="screenshots/delete_service.png" height="400" /> 
    </p>

3. Manage Users Activity

<p align="center">
    <img src="screenshots/list_accounts.png" height="400" />
    <img src="screenshots/delete_account.png" height="400" /> 
</p>
