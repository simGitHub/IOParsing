import numpy as np
import librosa as lb
import matplotlib.pyplot as plt

def plot_mfcc_data(mfcc_matrix, genre = 'genre'):
	plt.figure()
	plt.pcolor(mfcc_matrix)
	#nbrOfXPoint = np.size(mfcc_matrix,1)
	#plt.axis([0,nbrOfXPoint,0,10])
	plt.title(genre)
	plt.colorbar()
	plt.show()

def doSomeMaths(mfcc_matrix):
	print(np.shape(mfcc_matrix))
	nbr_mfcc_coeff = len(mfcc_matrix)
	for i in range(nbr_mfcc_coeff):
		print(np.mean(mfcc_matrix[i,:]))

def settOffsetToZero(mfcc_matrix):
	print('this method will set the offset of all coefficients to zero')

def normalizeData(mfcc_matrix):
	print('this method will normalize each coefficients to have maximum value of 1')

def attenuateSignal(mfcc_matrix, attenuateValue):
	return mfcc_matrix * attenuateValue

def saveFileToTxt(mfcc_matrix, saveFileName = 'outputTextFile.txt'):
	nbr_mfcc_coeff = np.size(mfcc_matrix,0)
	nbrDataPoints = np.size(mfcc_matrix,1)
	dt = 0.01
	dt_array = np.arange(nbrDataPoints) * dt
	mfcc_matrix_t = np.transpose(mfcc_matrix)
	finishedDataSignal = np.zeros((nbrDataPoints, nbr_mfcc_coeff + 1))
	finishedDataSignal[:,0] = dt_array
	finishedDataSignal[:,1:] = mfcc_matrix_t
	np.savetxt(saveFileName,finishedDataSignal,fmt='%.2f')

def readFileAndPerformMFCC(fileName):
	extractedData,samplingRate = lb.load(fileName)
	resolution = 300 # lesser value the better resolution
	melSpectroGram = lb.feature.melspectrogram(y = extractedData, sr = samplingRate, hop_length = resolution)
	numberOf_mfcc_coefficients = 8
	mfcc_matrix = lb.feature.mfcc(S= lb.power_to_db(melSpectroGram), n_mfcc = numberOf_mfcc_coefficients)
	return mfcc_matrix


fileName = 'music/blues.00000.au'
mfcc_matrix_blues = readFileAndPerformMFCC(fileName)
#plot_mfcc_data(mfcc_matrix_blues, 'blues')
#doSomeMaths(mfcc_matrix_blues)
saveFileToTxt(mfcc_matrix_blues)




