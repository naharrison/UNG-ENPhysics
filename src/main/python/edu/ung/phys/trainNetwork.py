import sys
import numpy
from sklearn.neural_network import MLPClassifier

"""
Read in the training and test files from the command line
or use the default values
"""
trainingFileName = "test-data/pid_sim_200.txt"
testFileName = "test-data/pid_sim_10.txt"

if(len(sys.argv) != 1 and len(sys.argv) != 3):
    print("wrong number of args... exiting")
    sys.exit()
elif(len(sys.argv) == 3): 
    trainingFileName = sys.argv[1] # (sys.argv[0] is the script filename)
    testFileName = sys.argv[2]


"""
Read training data into a big matrix (an array of arrays).
Ok for relatively small files but may need a smarter system for large files.
Also read target values into an array.
"""
training_data_matrix = []
training_target_values = [] # i.e. true values

training_data_file = open(trainingFileName, 'r')
training_data_list = training_data_file.readlines()
training_data_file.close()

for record in training_data_list:
    all_values = record.split(',')
    training_data_matrix.append(list(map(float, all_values[1:])))
    training_target_values.append(float(all_values[0])) # will int work here??


"""
Create and train the classifier.
MLP = Multi-Layer Perceptron.
"""
classifier = MLPClassifier(solver='adam', alpha=1e-5, random_state=1)
classifier_parameters = classifier.fit(training_data_matrix, training_target_values)
print("\nThe classifier parameters are as follows:\n")
print(classifier_parameters, "\n")


"""
Read in some test data.
"""
test_data_matrix = []
test_target_values = [] # i.e. true values

test_data_file = open(testFileName, 'r')
test_data_list = test_data_file.readlines()
test_data_file.close()

for record in test_data_list:
    all_values = record.split(',')
    test_data_matrix.append(list(map(float, all_values[1:])))
    test_target_values.append(float(all_values[0])) # will int work here??


"""
Now test the classifier on the test data and print results
"""
predictions = classifier.predict(test_data_matrix)
# score = classifier.score(test_data_matrix, test_target_values)

number_correct = 0

for k in range(0, len(predictions)):
    if(predictions[k] == test_target_values[k]):
        print("Test event {}: actual={}, prediction={}  :)".format(k+1, test_target_values[k], predictions[k]))
        number_correct += 1
    else:
        print("Test event {}: actual={}, prediction={}  :(".format(k+1, test_target_values[k], predictions[k]))

print("\n{} out of {} correct".format(number_correct, len(predictions)))
