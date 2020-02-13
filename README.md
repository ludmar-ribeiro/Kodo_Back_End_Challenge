# Kodo_Back_End_Challenge

Kodo Back-End Challenge 

Although a good CV and an impressive LinkedIn profile is always nice, here at Kodo we like to see how our future engineers will work! This is why a small challenge has been set for you, to see how you work, how you document your work and your proficiency in the tools you use.

The following challenge has no time limit but it shouldn't take more than a few hours. We are NOT looking for a full proof, bulletproof solution (although it would be nice!), we are just looking to see how you complete a task.

This is a good opportunity to demonstrate your style and your capabilities. Create it in your own way and use the tools you are most comfortable with. Show us your skills.

Challenge Description

The Kodo Robot Daycare Centre

The year is 2113 and robots are now an integral part of life. The Kodo Corporation are the leading company in personalised human robot interaction, but some of their robots are getting a bit....old. We have become so used to technology working perfectly that these old robots need to retire so humanity can continue to live with no interruption to their way of life.
When this time comes, all Kodo robots must retire to the Kodo Robot DayCare Centre!

Taking care of a malfunction robot is a time-consuming task and in our busy society not every person has the time to do so. That’s why we are building the Kodo Robot's daycare centre.

The Kodo robot's daycare centres are subsidised by the government and in order for them to benefit from this program, they need to give government inspectors access to their database of guests.

The inspectors API

The inspectors come to our centre and check our "robot guests" one by one and update the database.

As the inspectors are robots as well, they don't use a web browser to communicate with our system, but a very simple API that we have to implement for them.

The API has 4 methods:

[Update] update a Kodo robot's attributes

The inspector sends the attributes for a specific Kodo robot.
Our system detects if the Kodo robot is already in our database or not. If not, we create it and if yes, we go directly to the next step.
We update the Kodo robot's attributes, only changing the ones included in the actual update request.
Example
The Kodo XX1 is already in our system and has the following attributes:

size: 150cm

weight: 12kg

status: good conditions

color: white

age: 107years

The inspector checks the Kodo robot and decides to update his attributes and sends the following information:

color: dirty cream white

age: 124years

number of eyes: 1

number of antenna: 3

The Kodo XX1 now has these attributes:

size: 150cm

weight: 12kg

status: good conditions

color: dirty cream white

age: 124years

number of eyes: 1

number of antennas: 3

Be aware that the attributes are not predefined and can be anything on both sides: key and value.

[Show] get a Kodo robot's actual attributes

The inspector wants to know about the actual attributes of a Kodo robot

Example

The inspector asks for the Kodo XX1 and the system responses with the following information:

size: 150cm

weight: 12kg

status: good conditions

color: dirty cream white

age: 124years

number of eyes: 1

number of antennas: 3

[Index] get a list of all the Kodo robot's in our database

The inspector wants to have an overview of all the Kodo robot's in our database.

Example
The inspector makes the call and the system responses with the following information:

1

name: XX1

last_update: 2113-12-01

2

name: XX2

last_update: 2113-12-03

3

name: XX3

last_update: 2113-12-12

[History] get a Kodo robot's attributes changes

The inspector is interested in knowing about the evolution of the Kodo robot, so he asks for the changes on the attributes that have been done for this specific robot.

Example:
The inspector asks for the history of the Kodo XX1, the system responses with the following information:

2112-11-28 10:23:24

type: create

changes:

size: [] -> [150cm]

weight: [] -> [12kg]

status: [] -> [good conditions]

color: [] -> [white]

age: [] -> [107years]

2113-12-02 16:30:11

type: update

changes:

color: [white] -> [dirty cream white]

age: [123years] -> [124years]

number of eyes: [] -> [1]

number of antennas: [] -> [3]

The API details

You can make your own decision on the details of your API. You can define the URLs, request methods, headers, response types, response formats, etc.

You don't even have to follow the information structures showed in the examples above. Use whatever structure you think is best, however just make sure that your proposition includes all the information in the examples.

Programming Requirements

Frameworks, gems, databases are all up to you, we want to see how you would solve this!

What we assess

Legible, understandable and maintainable code
Wisely chosen tools, techniques and/or frameworks

Submission Instructions

Fork this project

Complete the task and push on your own fork. (Nice, atomic and iterative commits are welcome)

Include instructions of how we can make it to work

Submit a pull request

Send an email to Alfie@learnkodo.com for us to review your solution

