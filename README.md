# Simple-Banking-Web-Application
This is a Simple Banking web application written in Java.

Simple banking web application for a
bank. This application has the following endpoints:
1. GET \account_info\{accountNumber}
Action: query their account balance by account number and account
password. If the account does not exist return with Bad Request Error Code

Response body:
{
int responseCode
boolean success
String message
Object account {
String accountName,
String accountNumber,
Double balance
}
}

2. GET \account_statement\{accountNumber}
Response Body
[
{
Date transactionDate
String transactionType(Deposit or Withdrawal)
String narration
Double amount
Double accountBalance (after the transaction)
}
]

3. POST \deposit: User can deposit to any account number with any amount
below #1,000,000:00 and it will impact the account in real-time
If amount is more than #1,000,000:00 or less than #1:00. Reject the
transaction with the client error code of bad request. If the account does not exist return
with Bad Request Error Code
Request Body
{
String accountNumber
Double amount
}
Response Body
{
int responseCode
boolean success
String message
}

4. POST \withdrawal: User can withdraw provided he will have at least #500
after the withdrawn amount is deducted from the current balance. Otherwise,
respond with Bad request error code. If the amount is less than #1:00,

respond with Bad request error code. If the account not exists, return with a bad request code
Request body
{
String accountNumber
String accountPassword
Double withdrawnAmount
}
Response body
{
int responseCode
boolean successful
String message
}

5. POST \create_account: This will create an account number with the following
constraint

i. It will be unique in the system
ii. It will be of the exact length of 10
iii. It should be completely random and unpredictable
iv. If the account name already exists, it should fail with the descriptive message in response body
v. Initial deposit should be minimum #500:00
Request Body
{
String accountName
String accountPassword
Double initialDeposit
}
Response Body:
{
int responseCode
boolean success
String message
}

6. POST \login: this will perform basic authentication on accountNumber with a password and respond with accessToken for the user if successful. If
authentication fails, it should return a wrong credential error message with
Unauthorized error code

Request Body
{
String accountNumber,
String accountPassword
}
Response Body
{
boolean success
String accessToken
}

