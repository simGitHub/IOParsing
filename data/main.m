%% test code
clc;
a = zeros(1,5);
disp(a(1:5))

%% read memristance from blues and classical songs
clc;
nbrOfSongsPerGenre = 100;
grid_depth = 4;
grid_length = 2;
directory = "results/" + grid_depth + "x" + grid_length + "/1";
nbrOfMemristors = grid_length * grid_depth + (grid_depth - 1) * grid_length;
nbrOfTimeSteps = 2e3;
splitDataConstant = 0.6;
y_blues = 1;
y_classical = -1;
plotThetha_blues = false;
plotError_blues = false;
plotTetha_classical = false; 
plotError_classical = false;
plotScalarProduct_bool = false;
useThetaAverage_bool = false;
transient_test = 0;
transient_train = 0;
eta = 5 * 1e-3;
eta_momentum = 1; % 0.998 approximately gets to 0.1 of when 1e3 updates is used
nbrOfUpdates = 1 * 1e3;

% m_blues = readMemristanceFromSongs("blues", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
% m_classical = readMemristanceFromSongs("classical", directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongsPerGenre);
% [m_blues_trainingSet,m_blues_testSet] = splitData(m_blues, splitDataConstant);
% [m_classical_trainingSet,m_classical_testSet] = splitData(m_classical, splitDataConstant);
eta_vec = [9 * 1e-1, 8 * 1e-1, 7 * 1e-1, 6 * 1e-1, 5 * 1e-1, 4 * 1e-1, 3 * 1e-1, 2 * 1e-1, 1 * 1e-1 ...
    9 * 1e-2, 8 * 1e-2, 7 * 1e-2, 6 * 1e-2, 5 * 1e-2, 4 * 1e-2, 3 * 1e-2, 2 * 1e-2, 1 * 1e-2,...
    9 * 1e-3, 8 * 1e-3, 7 * 1e-3, 6 * 1e-3, 5 * 1e-3, 4 * 1e-3, 3 * 1e-3, 2 * 1e-3, 1 * 1e-3 ...
    9 * 1e-4, 8 * 1e-4, 7 * 1e-4, 6 * 1e-4, 5 * 1e-4, 4 * 1e-4, 3 * 1e-4, 2 * 1e-4, 1 * 1e-4 ...
    ];
nbrAverage = 2;
loss = zeros(nbrAverage,length(eta_vec));
eta_counter = 0;
for eta_i = eta_vec
    eta_counter = eta_counter + 1;
    disp("running eta: " + eta_i)
    for iAverage = 1:nbrAverage
        theta_blues = trainReadOutVector_v2("blues", m_blues_trainingSet, y_blues, transient_train, eta, plotThetha_blues, plotError_blues, useThetaAverage_bool, eta_momentum, nbrOfUpdates);
        theta_classical = trainReadOutVector_v2("classical", m_classical_trainingSet, y_classical, transient_train, eta, plotTetha_classical, plotError_classical, useThetaAverage_bool, eta_momentum, nbrOfUpdates);
        p_blues = testPerformance("blues", m_blues_testSet, theta_blues, theta_classical, transient_test, y_blues, y_classical);
        p_classical = testPerformance("classical", m_classical_testSet, theta_blues, theta_classical, transient_test,  y_blues, y_classical);
        loss(iAverage, eta_counter) = (p_blues + p_classical) / 2;
    end
end
loss_average = mean(loss,1);
semilogx(eta_vec, loss_average);

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