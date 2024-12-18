This repostiry contains a use case code and steps to create Azure function app using spring cloud function.
When this function is invoked it will send an email to given recipient.

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


## Azure Function code implementation.

  - Azure function is a serverless offering from Azure. Where developers don't need to worry about provisioning or maintaining of compute platform in which code is running.
    
  - We should only develop business logic which needs to be executed underlying platform to run this business logic will be provided and managed by Azure.
    
  - This business logic will be developed in the form of light weight function's and it can be executed by the azure platform.
    
  - These light weight functions will be developed using simple core Java itself depending on the nature of its use case. In our case we will be leveraging Spring framework to create this function. With Spring we can get the benefit of other framework features.

  - To create a function Spring will provide a library Spring Cloud Funtion which will have a custom integration for Azure Cloud Function. In a nutshell we will create a small spring boot application without other heavy weight framework features like MVC or WebFlxu but still use its powerful IoC features to implement our function business logic.

 - In spring initializr create a simple spring boot application with Spring Cloud Function dependency and include below given dependencies.

  ```
  <dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-function-context</artifactId>
		</dependency>

			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-function-adapter-azure</artifactId>
			</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

  ```

  - From this **spring-boot-starter-mail** will provide wrapper library to send email.
    
  - **spring-cloud-function-context** will provide a lightweight library to create functions which can be executed on different platforms.

  - **spring-cloud-function-adapter-azure** will provide intergation of Spring Cloud Function to Azure platform. When code is run on Azure this adapter will intercept the requests and bind the input to function objects then execute the function and send the response back to caller. It will provide hadnler implememtion to handle incoming request.

  - Once function code is implemented to deploy and run this function on Azure function app we need to configure following Azure plugin which will bind the code into the azure platform and run. In the configuration section should have exact value of Azure function app.

  ```
     
				<groupId>com.microsoft.azure</groupId>
				<artifactId>azure-functions-maven-plugin</artifactId>
				<version>${azure.functions.maven.plugin.version}</version>

				<configuration>
					<appName>${functionAppName}</appName>
					<resourceGroup>${functionResourceGroup}</resourceGroup>
					<region>${functionAppRegion}</region>
					<appServicePlanName>${functionAppServicePlanName}</appServicePlanName>
					<pricingTier>${functionPricingTier}</pricingTier>

					<hostJson>${project.basedir}/src/main/resources/host.json</hostJson>
				<!--	<localSettingsJson>${project.basedir}/src/main/resources/local.settings.json</localSettingsJson> -->

					<runtime>
						<os>linux</os>
						<javaVersion>17</javaVersion>
					</runtime>

					<funcPort>7072</funcPort>

					<appSettings>
						<property>
							<name>FUNCTIONS_EXTENSION_VERSION</name>
							<value>~4</value>
						</property>
					<!--	<property>
							<name>MAIN_CLASS</name>
							<value>com.azlearning.functionapp.AzEmailSenderFunctionApplication</value>
						</property> -->
					</appSettings>
				</configuration>
				<executions>
					<execution>
						<id>package-functions</id>
						<goals>
							<goal>package</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

  ``` 

  - Define a Azure function Halder which handles incoming request. To do that define a Component like below.

  ```
@Component
public class EmailSenderFunctionHandler {

    private final EmailSender emailSender;

    public EmailSenderFunctionHandler(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @FunctionName("emailSender")
    public String execute(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<EmailDTO>> request,
            ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        EmailDTO emailDTO = request.getBody().orElseThrow(() -> new IllegalArgumentException("EmailDTO is required"));

        return emailSender.apply(emailDTO);

    }
}
  ```
  - **EmailSenderFunctionHandler** is a entry point to function it is defined as simple spring bean using @Component.

  - **@FunctionName("emailSender")** annotation will indicate the name of the function on the azure.
 
  - **@HttpTrigger** will hanle HTTP specific request and it's supported methods which this function can handle. In our case this function will support POST method.

  - **HttpRequestMessage<Optional<EmailDTO>> request** will wrap the Request body which will be parsed by azure function adpter library.

  - **ExecutionContext context** will provide azure runtime platform access. Specifically to get platform logging context which is used to capture application logs and forward it to azure other services.

  - Once request is intercepted by this adapter then it will invoke remainder of the application logic as like any other spring java application.

  - In our use case we need to send email to do that we need to configure SMTP server details we created and captured in earlier steps above.

  - Below code will have mailtrap SMTP server details configured in our application code. These configuration will be read by the function during runtime and it will connect to SMTP server to send email.

   ```
     spring.application.name=az-email-sender-function
    
    spring.mail.host=live.smtp.mailtrap.io
    spring.mail.port=587
    spring.mail.username=smtp@mailtrap.io
    spring.mail.password=<Password>
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

  ```

   - **EmailSender** will implement **Function** interface and have logic to call the SMTP server with provided email details.

   - Under **resources** folder we need to include **host.json** with provided content as defined by azure to make the integration work.

### Run function locally and test

 - To run and test the azure functions locally we need to install Azure Core Tools which let us test our function locally without need to deploy in cloud.

 - Execute below commands to install this tool in macOS

  ```
    brew tap azure/functions
    brew install azure-functions-core-tools@4

  ```

   - To run and deploy azure function from IntelliJ IDE install **Azure ToolKit for IntelliJ**

   ![Install-Azure-ToolKit](https://github.com/user-attachments/assets/77cbe69f-2a26-4ef4-9d48-ce5fc7c5de66)

   - After this intsallation and restart we need to authenticate IntelliJ to connect our Azure account.

   - Once its authenticated we can able to see Azure connectivity window and different Azure service listing in IntelliJ IDE.

   ![Azure-IntelliJ](https://github.com/user-attachments/assets/28a5dc63-6bb0-4a16-bd78-249491372088)

   

### Create Azure funtion app in azure portal

   - Now our function code is ready we need to create a function app in Azure which will run this code.

   - 
