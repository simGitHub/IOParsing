%% test code
clc;
a = zeros(1,5);
disp(a(1:5))

%% read memristance from blues and classical songs
clc;
nbrOfSongs = 100;
nbrOfMemristors = 60;
nbrOfTimeSteps = 1201;
transient = 0.9;
directory = "music/";

% m_blues = readMemristanceFromSongs("blues", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongs);
% m_classical = readMemristanceFromSongs("classical", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongs);
% [m_blues_trainingSet,m_blues_testSet] = splitData(m_blues, 0.7);
% [m_classical_trainingSet,m_classical_testSet] = splitData(m_classical, 0.7);
% theta_blues = trainReadOutVector("blues", m_blues_trainingSet, 1, 0);
% theta_classical = trainReadOutVector("classical", m_classical_trainingSet, 0, 0);
classificationError_blues = testPerformance("blues", m_blues_testSet, theta_blues, theta_classical, transient);
classificationError_classical = testPerformance("classical", m_classical_testSet, theta_blues, theta_classical, transient);


%% plot memristor states over time of a given song

temp1_nonNorm = m_blues(:,:,1);
temp2_nonNorm = m_blues(:,:,2);
nbrDataPoints = 1200;
for i = 1:60
    figure(1)
    plot(temp2_nonNorm(i,1:nbrDataPoints))
    hold on
end
hold off