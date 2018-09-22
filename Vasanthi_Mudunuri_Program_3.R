library(readr)
dataset <- read_csv("C:/Users/welcome/Desktop/WIKI.csv")  #loading file
View(WIKI)
install.packages("rpart.plot")
library("rpart")
library ("rpart.plot")
WIKI$GENDER[WIKI$GENDER==0] <- 'Male'
WIKI$GENDER[WIKI$GENDER==1] <- 'Female'
WIKI$DOMAIN[WIKI$DOMAIN==1] <- 'Arts & Humanities'
WIKI$DOMAIN[WIKI$DOMAIN==2] <- 'Sciences'
WIKI$DOMAIN[WIKI$DOMAIN==3] <- 'Health Sciences'
WIKI$DOMAIN[WIKI$DOMAIN==4] <- 'Engineering & Architecture'
WIKI$DOMAIN[WIKI$DOMAIN==5] <- 'Law & Politics'
WIKI$PhD[WIKI$PhD==0] <- 'No'
WIKI$PhD[WIKI$PhD==1] <- 'Yes'
WIKI$Use1[WIKI$Use1==1] <- 'Strongly disagree'
WIKI$Use1[WIKI$Use1==2] <- 'disagree'
WIKI$Use1[WIKI$Use1==3] <- 'Neutral'
WIKI$Use1[WIKI$Use1==4] <- 'Strongly agree'
WIKI$Use1[WIKI$Use1==4] <- 'agree'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==1] <- 'Professor'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==2] <- 'Associate'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==3] <- 'Assistant'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==4] <- 'Lecturer'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==5] <- 'Instructor'
WIKI$UOC_POSITION[WIKI$UOC_POSITION==6] <- 'Adjunct'
fit <- rpart (Use1 ~ AGE + GENDER + DOMAIN + PhD + UOC_POSITION ,method="class",data=WIKI,control=rpart.control(minsplit=100)) #classifying Use1
summary(fit)
rpart.plot (fit, type=4, extra=2, clip.right.labs=FALSE, varlen=0, faclen=0) #plotting decision tree
Testing= WIKI[548:913,]
predict(fit,Testing)