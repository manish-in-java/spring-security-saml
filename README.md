# Overview
This sample application demonstrates the use of Spring Security for
authenticating users of a web application based on Spring MVC and
using Spring Security extensions for SAML in addition to regular
authentication methods such as form based login.

# Single Sign-on (SSO) basics
**Single sign-on (SSO)** refers to an implicit trust between multiple
software applications where users authenticated with one of the applications
are trusted by the other applications as their own users.

As an example, consider an organisation that uses Microsoft Active Directory
to manage and authenticate all its users centrally.  Users authenticate with
the Active Directory using one of the supported mechanisms such as a
username-password pair, a fingerprint, a facial picture, etc.  Since
the organisation has full control over the Active Directory, the users
fully trust the Active Directory for authentication with the implicit
knowledge that their authentication credentials (passwords, fingerprints,
etc.) are in the secure custody of their employing organisation.

If the organisation requires its users to access other internal or third-party
applications that also require the users to authenticate, it is natural
for the users to prefer that they can use their existing Active Directory
credentials to authenticate with these additional applications.  In this
scenario, if the users can actually use their Active Directory authentication
credentials to authenticate with these additional applications, the
organisation would said to be enabled for single sign-on (SSO).  SSO is
provided by Active Directory in this case, called the provider and all the
applications that depend on Active Directory for authentication will be called
as SSO consumers.

# Security Assertion Markup Language (SAML) basics
A successful SSO implementation has two challenges:

1. Making sure that the consumers can trust the provider, ensuring that a
malicious party cannot impersonate as the provider and force the consumers
to accept rogue users.
1. Ensuring that consumers and providers using different technologies and
having different representations for authentication data can exchange
authentication information seamlessly.

**Security Assertion Markup Language (SAML)** is a standard that uses XML
for exchanging SSO information reliably and securely.  In the SAML universe,
the provider is called an Identity Provider (IDP) and the consumer is called
a Service Provider (SP).

The IDP is responsible for the following:

* User registration
* User authentication
* Optional user rights management

The SP is responsible for providing meaningful business functionality to
the users.

Using SAML, authentication can be started by either the IDP or the SP.  In the
former case, it is known as IDP-initiated authentication whereas in the later
it is known as SP-initiated authentication.

In the case of IDP-initiated authentication, the user is already authenticated
with the IDP and requests a resource from the SP.  The IP forwards
authentication information to the SP, ensuring that the SP is able to
authenticate the user transparently.

In the case of SP-initiated authentication, the user requests a resource from
the SP.  The SP redirects the user to the IDP.  If the user is already
authenticated with the IDP, the IDP re-redirects them back to the SP, attaching
the required authentication information, allowing the SP to again authenticate
the user transparently.  If the user is not authenticated with the IDP, the
IDP forces them to authenticate and then proceeds with the re-redirect process.

# Form authentication
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
password.  You can register as many users as you want while the application
is running, provided the users have unique email addresses.

Successful sign-in will display the home page which will list all the
users registered with the application.

# SAML authentication
This sample application only supports IDP-initiated SSO; SP-initiated
SSO is not supported.

Once the application has started, you can test IDP-initiated SSO using the
[SSOCircle](http://www.ssocircle.com) public IDP.

1. Hit the application's [SAML metadata display page](http://localhost/saml/metadata).
This will either display the metadata as XML in the web browser or download an
XML file locally.  If later, open the file in a text editor.
1. Follow the detailed instructions on the
[SSOCircle website](http://www.ssocircle.com/en/ssocircle-how-to/) to 
register the sample application as an SP.
    1. Provide `localhost` as the provider name (FQDN field).
    1. Choose to receive all the three fields in the authentication response when registering the SP.
    1. Paste the metadata downloaded from the application in the field marked for metadata.
    1. Save the provider definition.
1. Initiate an [IDP-initiated SSO request](https://idp.ssocircle.com/sso/hos/AdPage.jsp?returnUrl=/sso/idpssoinit&metaAlias=%2Fssocircle&spEntityID=http%3A%2F%2Flocalhost%3A80%2Fsaml%2Fmetadata).
1. Authenticate with SSOCircle using your registered credentials.
1. You will be redirected to the application home page on successful authentication.  You will notice that the application automatically registers you on first successful authentication.

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
