# Overview
This sample application demonstrates the use of Spring Security for
authenticating users of a web application based on Spring MVC and
using Spring Security extensions for SAML in addition to regular
authentication methods such as form based login.

Download it and run it as `mvn clean tomcat7:run`.  Then access it using
a web browser at [http://localhost](http://localhost).  This will present
a sign-in form.

When the application is launched for the first time, it will not recognize
any users because none have been registered yet.  Use the sign-up link at
the bottom of the form to open the registration form.  Register a user with
a valid email address, name and password.  This process needs to be repeated
every time the application is stopped and started again because the user
information is kept strictly in-memory and is not saved anywhere in between
application restarts.

Once registered, you can sign in with the registered email address and
password.  You can register as many users as you want while the application is
running, provided the users have unique email addresses.

Successful sign-in will display the home page which will list all the
users registered with the application.

# License
This sample application and its associated source code in its entirety is being made
available under the following licensing terms.

    Copyright (C) 2016

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in the
    Software without restriction, including without limitation the rights to use, copy,
    modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
    and to permit persons to whom the Software is furnished to do so, subject to the
    following conditions:

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
    PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
    CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
    OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
