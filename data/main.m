%% test code
clc;
a = zeros(1,5);
disp(a(1:5))

%% read memristance from blues and classical songs
clc;clf;
nbrOfSongsPerGenre = 100;
nbrOfMemristors = 14;
nbrOfTimeSteps = 2e3;
transient_test = 0;
splitDataConstant = 0.7;
directory = "music/n_mfcc/4/";
y_blues = 1;
y_classical = 0; 
plotThetha_blues = false;
plotError_blues = true;
plotTetha_classical = false; 
plotError_classical = true;
transient_train = 0;
eta = 1e-3;f

% m_blues = readMemristanceFromSongs("blues", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
% m_classical = readMemristanceFromSongs("classical", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
[m_blues_trainingSet,m_blues_testSet] = splitData(m_blues, splitDataConstant);
[m_classical_trainingSet,m_classical_testSet] = splitData(m_classical, splitDataConstant);

theta_blues = trainReadOutVector("blues", m_blues_trainingSet, y_blues, transient_train, eta, plotThetha_blues, plotError_blues);
theta_classical = trainReadOutVector("classical", m_classical_trainingSet, y_classical, transient_train, eta, plotTetha_classical, plotError_classical);
testPerformance("blues", m_blues_testSet, theta_blues, theta_classical, transient_test, y_blues, y_classical);
testPerformance("classical", m_classical_testSet, theta_blues, theta_classical, transient_test,  y_blues, y_classical);




%% temporaraly words


accuracy of classification for all timesteps: 0.74974
accuracy of classification per song: 0.87143
genre type tested: blues

accuracy of classification for all timesteps: 0.74105
accuracy of classification per song: 0.81429
genre type tested: classical


%% do some math
value = 0;
for iTime = 1:nbrOfTimeSteps
    for iSong = 1:nbrOfSongsPerGenre - 30
        value = theta_classical * m_classical_trainingSet(:,iTime,iSong) + value;
    end
end
value_average = value / (nbrOfTimeSteps * (nbrOfSongsPerGenre - 30));
disp(value_average);



%% plot memristor states over time of a given song

temp1 = m_blues(:,:,1);
temp2 = m_blues(:,:,2); 
nbrDataPoints = 2e3;
for i = 1:4
    figure(1)
    j = 4;
    plot(m_blues(i,:,2)-m_classical(i,:,2));
    hold on
end

hold off