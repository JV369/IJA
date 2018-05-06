#!/bin/bash
rm -R -f images*;
rm -f junit-4.12.jar;
rm -f hamcrest-core-1.3.jar;
wget http://www.stud.fit.vutbr.cz/~xpostu03/junit-4.12.jar;
wget http://www.stud.fit.vutbr.cz/~xpostu03/hamcrest-core-1.3.jar;
wget http://www.stud.fit.vutbr.cz/~xpostu03/images.zip
unzip images.zip
