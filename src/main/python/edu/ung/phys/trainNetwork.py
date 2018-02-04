
from sklearn.neural_network import MLPClassifier
import numpy
g = 0
X = [[0.,0.,0.,0.,0.]] # inputs as an array of lists
y = [0.] # target values

clf = MLPClassifier(solver='adam', alpha=1e-5, random_state=1)


training_data_file = open("data/data_train_5000.txt", 'r')
training_data_list = training_data_file.readlines()
training_data_file.close()

for record in training_data_list:
	all_values = record.split(',')
	#X.append((numpy.asfarray(all_values[1:])))
	#print(X)
	X.append(numpy.array(list(map(float, all_values[1:]))).tolist())
	y.append(float(all_values[0]))

print(clf.fit(X,y))

X = [[0.,0.,0.,0.,0.]]
y = []
training_data_file = open("data/data_test_10.txt", 'r')
training_data_list = training_data_file.readlines()
training_data_file.close()

for record in training_data_list:
	all_values = record.split(',')
	X.append(numpy.array(list(map(float, all_values[1:]))).tolist())
	y.append(float(all_values[0]))

for g in training_data_list:
        prediction = ','.join(map(str ,clf.predict(X)))
##        predictions = float(prediction.split(","))
        predictions = [int(e) if e.isdigit() else e for e in prediction.split(',')]
        print(prediction[g])

#print(clf.score(X,y))
prediction = ','.join(map(str ,clf.predict(X)))
print(prediction)

##print(clf.predict(X))
#print(y)
