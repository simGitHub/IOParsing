import numpy as np
import librosa as lb
import matplotlib.pyplot as plt

def ReadAudioFileAndPlotMFCC(fileName,figure_nbr, genre):
    
    y,sr = lb.load(fileName)
    S = lb.feature.melspectrogram(y=y, sr=sr, hop_length = 30000)
    y_mfcc = lb.feature.mfcc(S=lb.power_to_db(S),n_mfcc = 10)
    #y_mfcc = lb.feature.mfcc(y, n_mfcc=10)
    print np.shape(y_mfcc)
    nbrOfXPoint = np.size(y_mfcc,1)
    plt.figure(figure_nbr)
    plt.pcolor(y_mfcc)
    plt.axis([0,nbrOfXPoint,0,10])
    plt.title(genre)
    plt.colorbar()
    


fileName = 'genres/blues/blues.00001.au'
ReadAudioFileAndPlotMFCC(fileName, 1,'blues')
#fileName = 'genres/blues/blues.00002.au'
#ReadAudioFileAndPlotMFCC(fileName, 2,'blues')
#fileName = 'genres/blues/blues.00003.au'
#ReadAudioFileAndPlotMFCC(fileName, 3,'blues')
#
#fileName = 'genres/classical/classical.00001.au'
#ReadAudioFileAndPlotMFCC(fileName, 4,'classical')
#fileName = 'genres/classical/classical.00002.au'
#ReadAudioFileAndPlotMFCC(fileName, 5,'classical')
#fileName = 'genres/classical/classical.00003.au'
#ReadAudioFileAndPlotMFCC(fileName, 6,'classical')





