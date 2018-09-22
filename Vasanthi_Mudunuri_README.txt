Dataset WIKI which was assigned is also been attached

To run the spark jobs written in scala:
1.copy Vasanthi_Mudunuri_Program.sbt to the present working directory
2.Create path in present working directory as src/main/scala/ and copy Vasanthi_Mudunuri_Program_1.scala,
Vasanthi_Mudunuri_Program_2.scala,Vasanthi_Mudunuri_Program_3.scala programs into it
3.From the present working directory type : sbt package
4.After succesfull completion this creates project and target folders under present working directory
5.The target folder contains scala-2.10 directory which contains the jar file vasanthi_mudunuri_program_2.10-1.0.jar 
6.Now run the Command 2 below as specified for each program to run the spark job.
7.A jar file specific to each program is been already attached instead of following the above steps copy the jars to the current working directory
and run the below Command 1 for each program.

Note: here both the input and output paths are in hdfs
InputPath : hdfs://hadoop1:9000/vmudunu/WIKI.csv

Program 1:

Command 1: spark-submit --class Vasanthi_Mudunuri_Program_1 vasanthi_mudunuri_program_1.jar /vmudunu/Program1

(or)

Command 2: spark-submit --class Vasanthi_Mudunuri_Program_1 target/scala-2.10/vasanthi_mudunuri_program_2.10-1.0.jar /vmudunu/Program1

Arguments: OutputPath

Program 2:

Command 1: spark-submit --class Vasanthi_Mudunuri_Program_2 vasanthi_mudunuri_program_2.jar /vmudunu/Program2

(or)

Command 2: spark-submit --class Vasanthi_Mudunuri_Program_2 target/scala-2.10/vasanthi_mudunuri_program_2.10-1.0.jar /vmudunu/Program2

Arguments: OutputPath

Program 3:

Command 1: spark-submit --class Vasanthi_Mudunuri_Program_3 vasanthi_mudunuri_program_3.jar /vmudunu/Program3

(or)

Command 2: spark-submit --class Vasanthi_Mudunuri_Program_3 target/scala-2.10/vasanthi_mudunuri_program_2.10-1.0.jar /vmudunu/Program3

Arguments: OutputPath

To run the R programs:

Load the R file to Rstudio by selecting File -> Open File.
Click on run.