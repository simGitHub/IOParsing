%% test code
clc;
a = zeros(1,5);
disp(a(1:5))

%% read memristance from blues and classical songs
clc;
nbrOfSongsPerGenre = 100;
depth = 4;
length = 2;
directory = "results/" + depth + "x" + length + "/1";
nbrOfMemristors = length * depth + (depth - 1) * length;
nbrOfTimeSteps = 2e3;
splitDataConstant = 0.6;
y_blues = 1;
y_classical = -1;
plotThetha_blues = true;
plotError_blues = false;
plotTetha_classical = false; 
plotError_classical = false;
plotScalarProduct_bool = false;
useThetaAverage_bool = false;
transient_test = 0;
transient_train = 0;
eta = 1 * 1e-3;
eta_momentum = 0.9999;
nbrOfUpdates = 8 * 1e3;

% m_blues = readMemristanceFromSongs("blues", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
% m_classical = readMemristanceFromSongs("classical", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
% [m_blues_trainingSet,m_blues_testSet] = splitData(m_blues, splitDataConstant);
% [m_classical_trainingSet,m_classical_testSet] = splitData(m_classical, splitDataConstant);

theta_blues = trainReadOutVector_v2("blues", m_blues_trainingSet, y_blues, transient_train, eta, plotThetha_blues, plotError_blues, useThetaAverage_bool, eta_momentum, nbrOfUpdates);
theta_classical = trainReadOutVector_v2("classical", m_classical_trainingSet, y_classical, transient_train, eta, plotTetha_classical, plotError_classical, useThetaAverage_bool, eta_momentum, nbrOfUpdates);
testPerformance("blues", m_blues_testSet, theta_blues, theta_classical, transient_test, y_blues, y_classical);
testPerformance("classical", m_classical_testSet, theta_blues, theta_classical, transient_test,  y_blues, y_classical);

hold off
if plotScalarProduct_bool
    nbrOfPlots = 2;
    figure(2)
    plotDotProductOfRandomSong(m_blues_testSet, theta_blues,theta_classical, nbrOfPlots, y_blues, y_classical,"blues");
    figure(3)
    plotDotProductOfRandomSong(m_classical_testSet, theta_blues,theta_classical, nbrOfPlots, y_blues, y_classical,"classical");
end

disp("Finished")

%% hyperparameter used:
eta = 1 * 1e-2;
eta_momentum = 0.99;
nbrOfUpdates = 1 * 1e3;
nbrErrorSums = 100;

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