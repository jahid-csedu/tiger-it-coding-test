# tiger-it-coding-test
The project has 4 pages in total(login, home, add contacts and view contacts)
pages are protected with proper access control
there are 2 types of user roles (ADMIN, USER)
for USER use username=user and password=user
for ADMIN user username=admin and password=admin
admin can view contact list as well as add contact
user can only view the contact list
I have used in memory authentication for user authentication
there are 2 rest apis. one for add new contact another for get all contacts
rest apis are secured with spring basic http authentication i.e. username and password should be provided in the request header
the project has basic error handling mechanism for rest apis
