# <center>SEG 2105A - Walk in Clinics Services App</center>


<p align="center">
    <img src="assets/clinigo_logo_slogan.png" height="400" /> 
</p>

### Presented by: Team Camel

| Name | Student Number |
| --- | --- |
| Eric Haggar | 7674509 |
| Adel Araji | 7897476 |
| Mark Bastawros | 8123595 |
| Lev Guzman Aparicio   |  300038033 |
| Siraj Ghassel   |  8168653 |


### Professor: Andrew Forward

### Department of Electrical Engineering and Computer Science

## Table Of Contents
- [Introduction](#introduction)
- [UML Class Diagram](#uml-class-diagram)
- [Task Distribution](#task-distribution)
- [Screenshots](#screenshots)
- [Lessons Learned](#lessons-learned)


## Introduction

Our team developed an app called `Clinigo` to address the need for people to know wait times at nearby walk-in clinics without having to leave their home. It allows users to know the services offered by nearby walk-in clinics and allows them to check-in/book appointments at the clinic of their choice. The project was divided into 4 deliverables: Account Creation, Admin Functionality, Employee (Clinic) Functionality and Patient Functionality. 

The first deliverable consisted in developing account creation for employees and patients, while the admin login was harcoded based on the system requirements. Firebase Authentication as well as Firebase Realtime Database were used for authentication and storing new users. 

The second deliverable consisted in allowing the admin to manage users and services. The admin is allowed to delete employee and patient accounts, as well as create, update and delete services that clinics can offer.

The third deliverable consisted in allowing employees to create a profile for their clinic. The clinic therefore has access to add services from the existing services (created by the admin), update their working hours and edit their profile information.

The fourth and final deliverable consisted in allowing patients to search for walk-in clinics, book/check-in to the clinics of their choice and rate clinics from 1 to 5. The patient can search by using different filters: Address, City, Working Hours and Types of Services Provided. The patient can also choose to book a date as an appointment or check-in to the clinic immediately. Before doing so, the patient has access to the current day's average wait-times to make an informed decision. Finally, the patient can remove bookings or check-out from bookings as well as submit a rating and optional comment.

## UML Class Diagram

All important assumptions made throughout the life of this project are presented below:

- An employee account is associated to 1 clinic account (they are the same).
- Bookings are made for a day of the week in a yearly calendar rather than a time of day.
- A patient can only book or check-in to 1 clinic/day.
- A patient can rate a clinic however many times they want, and leaving comments is optional.

The final UML Class Diagram for the project is shown below and can be found on [Umple](https://cruise.eecs.uottawa.ca/umpleonline/umple.php?model=191019783621124).

<p align="center">
    <img src="screenshots/UML_Deliverable04.png"/> 
</p>

## Task Distribution

The table below shows the general task distribution that was used for each deliverable. However, the team worked closely for each deliverable and some features were implemented/revised by multiple team members. For more details, please see the [projects](https://github.com/professor-forward/project-camel/projects) on Github, where all issues were created, assigned and completed.

| Team Members |      Deliverable 1      |     Deliverable 2     |      Deliverable 3      |           Deliverable 4           |
|:------------:|:-----------------------:|:---------------------:|:-----------------------:|:---------------------------------:|
|     Siraj    |       UML Diagram       |   Admin Manage Users  | Updating Models and UML | Preparing Presentation and Report |
|     Eric     | Firebase Authentication | Circle CI Integration |  Manage Clinic Services |           Rating Clinics          |
|     Adel     |         Sign-up         |     Error Handling    |   Clinic Working Hours  |           Clinic Search           |
|      Lev     |         Sign-in         | Admin Manage Services |        Unit Tests       |      Updating Models and UML      |
|     Marc     |      Model Creation     |       Unit Tests      |    Clinic Information   |      Booking/Check-In Feature     |

## Screenshots

The screenshots for all deliverables are presented in this section.

1. Home Activity

<p align="center">
    <img src="screenshots/home.png" height="400" />
</p>

2. Sign Up Activities

<p align="center">
    <img src="screenshots/sign_up.png" height="400" />
</p>

3. Login Activities

<p align="center">
    <img src="screenshots/login.png" height="400" />
</p>

4. Admin Example

<p align="center">
    <img src="screenshots/admin_login.png" height="400" />
</p>

5. Admin Homepage

<p align="center">
    <img src="screenshots/admin_home_page.png" height="400" />
</p>

6. Manage Services Activity

<p align="center">
    <img src="screenshots/list_services.png" height="400" />
    <img src="screenshots/add_service.png" height="400" />
    <img src="screenshots/edit_service.png" height="400" />
</p>

7. Manage Users Activity

<p align="center">
    <img src="screenshots/list_users.png" height="400" />
    <img src="screenshots/delete_user.png" height="400" />
</p>

8. Create Clinic Profile

<p align="center">
    <img src="screenshots/create_profile.png" height="400" />
</p>

9. Employee Homepage

<p align="center">
    <img src="screenshots/employee_home.jpeg" height="400" />
</p>

10. Edit Profile Information

<p align="center">
    <img src="screenshots/edit_profile.png" height="400" />
</p>

11. Clinic Working Hours

<p align="center">
    <img src="screenshots/working_hours.jpeg" height="400" />
</p>

12. Clinic Services

<p align="center">
    <img src="screenshots/empty_services_page.png" height="400" />
    <img src="screenshots/clinic_services_list.png" height="400" />
</p>

13. Clinic Search

<p align="center">
    <img src="screenshots/clinic_search.png" height="400" />
</p>

14. Clinic Booking

<p align="center">
    <img src="screenshots/clinic_booking.png" height="400" />
    <img src="screenshots/my_booking.png" height="400" />
    <img src="screenshots/rate.png" height="400" />
</p>

## Lessons Learned
