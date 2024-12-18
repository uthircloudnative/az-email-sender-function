This repostiry contains a use case code and steps to create Azure function app using spring cloud function.

# Use Case

  - It will have an implementation for sending email to given recipent.
  - This application is created as Azure funcation app using spring cloud function.
  - It can be invoked as HTTP endpoint as its defined to respond for any HTTP trigger event.

# Tech Stack

  - Java 17
  - Spring Boot 3.4.x
  - Spring Cloud Function
  - Maven
  - Azure Cloud Function

# Prerequisite

  - As this use case needs to send email for given receipent we are using MailTrap email delivery platform to send emails.
  - This require an account to be created in Mailtrap platform.
  - Steps mentioned in this article required only free account.

## Steps to Create mailtrap account

  - visit this https://mailtrap.io/ site to SignUp for free account to get SMTP server setup to send email.

  ![SignUp in mailtrap for free account](https://github.com/user-attachments/assets/a7e7bec4-3a9d-4c7c-901a-405dfac82610)

  - Clieck SignUp and choose any one of the option to create free account.

  ![Fille the form](https://github.com/user-attachments/assets/09630adc-3fe5-4237-bed9-9b2de0d22e39)

  - Select below mentioned option as we only need to send email to a valid email id to test the functionality.

  ![Choose Free AcctOptions](https://github.com/user-attachments/assets/a3eb6166-a077-4a43-993b-3122fa1aa6e2)

  - Upon submission of the form we can land in below page.

  ![Registration-Success](https://github.com/user-attachments/assets/f51627f3-1229-4f11-97c3-14caee0e11f6)

  - Select a demo domina from which we will send emails to a valid email address. This domain is required to send emails.

  ![Domain-Selection](https://github.com/user-attachments/assets/b4c0f788-55fc-450c-b4cd-dabc688c41df)

  - Now we need to specific integration type to send email. For this use case just choose Transactional Stream.

  ![Choose-txn-Integration](https://github.com/user-attachments/assets/4e788bba-8b90-4fd8-b8af-51a61d91a2a5)

  - Upon clicking Integration copy below listed SMTP server details. These server details will be used in the Azure Cloud Funtion code.

   ![Copy-Server-Details](https://github.com/user-attachments/assets/a236c2f9-57e1-446a-b084-05dd4916ca57)

  - Also make sure Demo domain is verified successfully after its get created. This will happen automatically after few seconds, no action needed from our end.

  ![Verify-1](https://github.com/user-attachments/assets/b9e45a25-187f-4b89-b2c2-54c6b128ab5f)

  ![Verify-2](https://github.com/user-attachments/assets/57674e5c-33c6-4416-8709-50c66a7eab0a)

  - Now we are all set to use this SMTP server to send emails. As this is free account it has some limitation. Please be aware of those limits before sending emails.

