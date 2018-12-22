import numpy as np
import librosa as lb
import matplotlib.pyplot as plt
import sklearn

class CalculateMFCC:
	def __init__(self):
		self.nbrOfSongsPerGenre = 100
		self.readFileDirectory = "../../music_data/"
		self.saveFileDirectory = "music/n_mfcc/2/normalized/" 
		self.n_mfcc = 2
		self.dt = 0.01
		self.normalizeData_bool = True
		self.normalizeCoefficients_bool = False
		self.attenuateValue = 0.01

	def plot_mfcc(self, mfcc, genre = 'genre'):
		plt.figure()
		plt.pcolor(mfcc)
		plt.title(genre)
		plt.colorbar()
		plt.show()

	def normalizeCoefficientsToAValue(self, mfcc, normalizeValue):
		for i in range(self.n_mfcc):
			mfcc[i,:] = normalizeValue * mfcc[i,:] / np.max(np.abs(mfcc[i,:]))
		return mfcc

	def normalizeData(self,mfcc):
		mfcc = sklearn.preprocessing.scale(mfcc, axis = 1)
		return mfcc

	def saveFileToTxt(self,mfcc, saveFileName = 'outputTextFile.txt'):
		nbrDataPoints = np.size(mfcc,1)
		dt_array = np.arange(nbrDataPoints) * self.dt
		mfcc_t = np.transpose(mfcc)
		finishedDataSignal = np.zeros((nbrDataPoints, self.n_mfcc + 1))
		finishedDataSignal[:,0] = dt_array
		finishedDataSignal[:,1:] = mfcc_t
		np.savetxt(saveFileName,finishedDataSignal,fmt='%.2f')

	def readFileAndPerformMFCC(self,fileName):
		x,sr = lb.load(fileName)
		mfcc = lb.feature.mfcc(x, sr = sr, n_mfcc = self.n_mfcc, hop_length = 300)
		return mfcc


	def attentuateSignal(self,mfcc, value):
		mfcc = value * mfcc
		return mfcc

	def doHeadOperationsOfSong(self, fileName = None):
		mfcc = self.readFileAndPerformMFCC(self.readFileDirectory + fileName + ".au")
		if self.normalizeData_bool:
			mfcc = self.normalizeData(mfcc)
		elif self.normalizeCoefficients_bool:
			mfcc = self.normalizeCoefficientsToAValue(mfcc, 2.5)
		else:
			mfcc = self.attentuateSignal(mfcc, self.attenuateValue)
		self.saveFileToTxt(mfcc, saveFileName = self.saveFileDirectory + fileName + ".txt")

	def calculateBluesSongsMFCC(self):
		for i in range(self.nbrOfSongsPerGenre):
			if i % 10 == 0:
				print('blues', i)
			if i < 10:
				fileName = 'blues.0000' + str(i)
			else:
				fileName = 'blues.000' + str(i)
			self.doHeadOperationsOfSong(fileName)


	def calculateClassicalSongsMFCC(self):
		for i in range(self.nbrOfSongsPerGenre):
			if i % 10 == 0:
				print('classical', i)
			if i < 10:
				fileName = 'classical.0000' + str(i)
			else:
				fileName = 'classical.000' + str(i)
			self.doHeadOperationsOfSong(fileName)

	def run(self):
		self.calculateBluesSongsMFCC()
		self.calculateClassicalSongsMFCC()


if __name__ == '__main__':
	#fileName = "../../music_data/" + 'blues.00000.au'
	#x,sr = lb.load(fileName)
	#mfcc = lb.feature.mfcc(x, sr = sr, n_mfcc = 8)
	#print(np.size(mfcc,0))

	c = CalculateMFCC()
	c.run()




