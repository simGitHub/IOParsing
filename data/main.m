%% test code
clc;
a = zeros(1,5);
disp(a(1:5))

%% read memristance from blues and classical songs
clc;
nbrOfSongs = 100;
nbrOfMemristors = 60;
nbrOfTimeSteps = 2e3;
transient_train = 0;
transient_test = 0;
splitDataConstant = 0.7;
directory = "music/";
y_blues = 1;
y_classical = 0; 
plotTheta = true;

% m_blues = readMemristanceFromSongs("blues", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongs);
% m_classical = readMemristanceFromSongs("classical", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongs);
[m_blues_trainingSet,m_blues_testSet] = splitData(m_blues, splitDataConstant);
[m_classical_trainingSet,m_classical_testSet] = splitData(m_classical, splitDataConstant);
theta_blues = trainReadOutVector("blues", m_blues_trainingSet, y_blues, transient_train, plotTheta);
theta_classical = trainReadOutVector("classical", m_classical_trainingSet, y_classical, transient_train, plotTheta);
classificationError_blues = testPerformance("blues", m_blues_testSet, theta_blues, theta_classical, transient_test, y_blues, y_classical);
classificationError_classical = testPerformance("classical", m_classical_testSet, theta_blues, theta_classical, transient_test,  y_blues, y_classical);




%% plot memristor states over time of a given song

temp1_nonNorm = m_blues(:,:,1);
temp2_nonNorm = m_blues(:,:,2);
nbrDataPoints = 2e3;
for i = 1:60
    figure(1)
    plot(temp2_nonNorm(i,1:nbrDataPoints))
    hold on
end
hold off