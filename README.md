# Report for Assignment 1

## Project

Description: An implementation of the Halli Galli card game featuring a player versus multiple CPU opponents. It includes game mechanics like card handling, bell-smacking logic, and participant elimination.

Programming language: Java

## Initial tests

### Tests

Located in src/test/java

- AreFiveFruitsPresent_TestA
- GrabAllTableCards_BasicTest
- ProcessCPUBellSmacking_BasicTest
- ProvidePlayerNameTest

![Test Report](images/testReport.jpg)

### Coverage of initial tests

We used jacoco and gradle to do our initial tests. We created a build.gradle file that ensured we're using jacoco and ran tests using the command line ./gradlew clean test jacocoTestReport. To know if the tests worked, we would navigate to the index.html file found in HaliGaliJava/build/reports/tests/test and to see our coverage so far we would navigate to the index.html file found in HaliGaliJava/build/reports/jacoco/test/html.

![Initial Coverage Report](images/initialCoverage.jpg)

## Coverage improvement

### Individual tests

Nada

Test 1: ResetGame_BasicTest

https://github.com/Cvndas/HaliGaliJava/commit/64296da77a90f8a5ca4e927a27d378601a0d4da6#diff-19c3fc0506bd3705a8cf376d79cc00f5ec27da80b00862df7c10edc1afca4076

![Detailed Initial Coverage Report](images/detailed_initial.png)

![Nada Coverage Report](images/nadaTestsCoverage.png)

This test improved coverage by 50% because it actually runs ResetGame() with all the participant lists filled. It checks if the method really clears everything and returns true. Before this, those lines weren’t being tested. Now, the code that clears the lists and checks if they’re empty is being used, which explains the coverage boost.

Test 2:  InitializeGame_BasicTest

https://github.com/Cvndas/HaliGaliJava/commit/a030034d65208985747dee845b9f71900e92d5d4#diff-2ddfc30ca3a82ccae3704265d860c49d75518cc5ddd911ebf6f17c4a546bb47f

![Detailed Initial Coverage Report](images/detailed_initial.png)

![Nada Coverage Report](images/nadaTestsCoverage.png)

This test helped increase the coverage of InitializeGame() by about 57% because it actually ran the method with real input. I used System.setIn() to simulate typing "3" for the number of players and "PlayerName" for the name. Then I set up the scanner and called the method. The test checks if the method returns 3, meaning the input was processed correctly. By doing this, the test activated parts of the code that read input, set up players, and returned the count, which wasn’t being tested before.


Thadeus

TODO: Group member name

TODO: Test 1

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

TODO: Provide a screenshot of the new coverage results

TODO: State the coverage improvement with a number and elaborate on why the coverage is improved

Repeat for other tests...

Amira

TODO: Group member name

TODO: Test 1

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

TODO: Provide a screenshot of the new coverage results

TODO: State the coverage improvement with a number and elaborate on why the coverage is improved

Repeat for other tests...

Sıla 

TODO: Group member name

TODO: Test 1

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

TODO: Provide a screenshot of the new coverage results

TODO: State the coverage improvement with a number and elaborate on why the coverage is improved

Repeat for other tests...



### Overall

![Initial Coverage Report](images/initialCoverage.jpg)

TODO: Provide a screenshot of the new coverage results by running the existing tool using all test modifications made by the group

## Statement of individual contributions

TODO: Write what each group member did. Use the following table for that and add additional text under it if you see fit.

Link to github repository: https://github.com/Cvndas/HaliGaliJava

| Member | Three functions (names with links to the code on the repository) created | Initial test (name) | Other tests (names) |
| --- | --- | --- | --- |
| Nada | GrabAllTableCards(), KickOutDeadParticipants(), ResetGame() https://github.com/Cvndas/HaliGaliJava/commit/fd985c55a7c56550b4878545e23b920d2f3f1be2#diff-328ff730f449c18acd4a1342163e35e947d20707e4710bf8abea62659b5bc807| GrabAllTableCards_BasicTest | ResetGame_BasicTest, InitializeGame_BasicTest |
| Thadeus | | | |
| Amira | | | |
| Sıla | | | |