import pandas as pd # data processing, CSV file I/O 
from sklearn.model_selection import train_test_split 
from sklearn.tree import DecisionTreeClassifier
import random as rn

data= pd.read_csv('1119492.csv')            #importing CSV file

data.drop(["d_id",],axis=1,inplace=True)            #dropping columns that are not required
data.drop(["time_stamp",],axis=1,inplace=True)
data.drop(["ldr"],axis=1,inplace=True)
data['hum']=data['hum'].astype('float32')       #converting datatype to type supported by Classifier
data['soil']=data['soil'].astype('float32')
data['temp']=data['temp'].astype('float32')
data['pump']=data['pump'].astype('float32')
#data.info()
y=data.pump.values     #assigning values in column 'pump' as labels
#print(y)
data.drop(["pump"],axis=1,inplace=True)         #dropping pump as it is a label
x_data=data                                     #assigning rest of data as input features
#print(x_data)
x_train,x_test,y_train,y_test=train_test_split(x_data,y,test_size=0.5,random_state=10) #splitting data into training and testing data also randomizing
#print(x_train)
#print(x_test)
dt= DecisionTreeClassifier()            #Initialzing Classifier
dt.fit(x_train,y_train)                 #Training the Classifier
print("Score : ",dt.score(x_test,y_test))    #Testing the Classifier
prediction=[None]*10              #Declaring list to hold predictions of 10 rows of randomly generated data
for i in range(len(prediction)):
    prediction[i]= dt.predict([[rn.randrange(50),rn.randrange(60),rn.randrange(100)]])
    print(prediction[i][0])

