library(readr)
dataset <- read_csv(C:/Users/welcome/Desktop/WIKI.csv) #loading data
View(WIKI)
str(WIKI)
summary(WIKI)
install.packages("lattice")
library("lattice")
splom (~WIKI[c(1,2,4,6)], groups=NULL, data=WIKI,axis.line.tck = 0,axis.text.alpha = 0) #scatter plot matrix
Training= WIKI[1:547,] #training data
Testing= WIKI[548:913,] #testing data
MultipleLinearRegression = lm(WIKI$UNIVERSITY~WIKI$AGE+WIKI$GENDER+WIKI$PhD,data=Training)
confint(MultipleLinearRegression,level=0.95,interval="confidence")
Result=predict(MultipleLinearRegression,Testing[,c(1,2,4)])
head(Result) #predicted value
head(Testing[,6]) #actual value