#!/usr/bin/env bash

# delete bin directory if it exist
# forsing to recompile the code
if [ -d "../bin" ]
then
    rm -rf ../bin
fi

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/**/**/**.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin org/ajay/Jarvis < input.txt > ACTUAL.TXT

# convert to UNIX format
# cp EXPECTED.TXT EXPECTED-UNIX.TXT
# dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# compare the output to the expected output
# -b, --ignore-space-change
#       ignore changes in the amount of white space
# -B, --ignore-blank-lines
#       ignore changes whose lines are all blank
diff -bB ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
