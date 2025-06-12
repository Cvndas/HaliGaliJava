# Report for Assignment 1

## Project

Description: An implementation of the Halli Galli card game featuring a player versus multiple CPU opponents. It
includes game mechanics like card handling, bell-smacking logic, and participant elimination.

Programming language: Java

## Initial tests

### Tests

Located in src/test/java

- AreFiveFruitsPresent_TestA
- GrabAllTableCards_BasicTest
- ProcessCPUBellSmacking_BasicTest
- ProvidePlayerNameTest

![Initial Coverage Report](images/testReport.jpg)

### Coverage of initial tests

We used jacoco and gradle to do our initial tests. We created a build.gradle file that ensured we're using jacoco and
ran tests using the command line ./gradlew clean test jacocoTestReport. To know if the tests worked, we would navigate
to the index.html file found in HaliGaliJava/build/reports/tests/test and to see our coverage so far we would navigate
to the index.html file found in HaliGaliJava/build/reports/jacoco/test/html.

![Initial Coverage Report](images/initialCoverage.jpg)

## Coverage improvement

### Individual tests

Nada

Test 1: KickOutDeadParticipants_BasicTest

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

TODO: Provide a screenshot of the new coverage results

TODO: State the coverage improvement with a number and elaborate on why the coverage is improved

Test 2:  InitializeGame_BasicTest

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

TODO: Provide a screenshot of the new coverage results

TODO: State the coverage improvement with a number and elaborate on why the coverage is improved

### Thadeus

Test 1:

TODO: Show a patch (diff) or a link to a commit made in your repository that shows the new test

TODO: Provide a screenshot of the old coverage results (the same as you already showed above)

New coverage results
![Test 1 improvement](images/test_1_improvement.png)

I changed initial test to have a null card and 4 bananas. The coverage is improved because the "continue" path is being
hit by having a player  that does not have a card.

Test 2:
Link to commit: https://github.com/Cvndas/HaliGaliJava/commit/a3528202f793cfa1cba3fbdb76c0c2e6761b74a9

Old coverage results:
![Test 1 improvement](images/test_1_improvement.png)

New coverage results:
![Test 2 improvement](images/Thadeus_Test_2_improvement.png)

This test improves the coverage for the same function by 25% on missed instructions, and 31% on missed branches.
This is because it tests for various different fruits. 



TODO: Test 3

Link to commit: https://github.com/Cvndas/HaliGaliJava/commit/a3528202f793cfa1cba3fbdb76c0c2e6761b74a9

Old coverage results
![Test 2 improvement](images/Thadeus_Test_2_improvement.png)

New coverage results:
![Test 3 improvement](images/Thadeus_Test_3_improvement.png)

The new test improves missed instructions on ProgressTurnIndex() by 100%, and missed branches by 87%.
This test was created by analyzing how the function works, and observing that the following must hold
for coverage to be maximum:
		- currentPlayerTurn input must be equal to allCpuParticipants.size()
		- The player must be dead
		- The next CPU must be dead
		- The CPU After the dead cpu is not dead.
The inputs within the test are crafted to match these properties.

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

TODO: Provide a screenshot of the new coverage results by running the existing tool using all test modifications made by
the group

## Statement of individual contributions

TODO: Write what each group member did. Use the following table for that and add additional text under it if you see
fit.

| Member  | Three functions (names with links to the code on the repository) created | Initial test (name) | Other tests (names) |
|---------|--------------------------------------------------------------------------|---------------------|---------------------|
| Nada    |                                                                          |                     |                     |
| Thadeus |                                                                          |                     |                     |
| Amira   |                                                                          |                     |                     |
| Sıla    |                                                                          |                     |                     |