function theta = trainReadOutVector(genre, m, y, transient, plot_bool)
    nbrOfMemristors = size(m,1);
    nbrOfTimeSteps = size(m,2);
    nbrOfTrainingSongs = size(m,3);
    theta = ones(1,nbrOfMemristors);
    eta = 0.00001;
    transientValue = round(nbrOfTimeSteps * transient);
    gradient_plot = zeros(nbrOfMemristors, (1 - transient) * nbrOfTrainingSongs * nbrOfTimeSteps);
    theta_plot= zeros(nbrOfMemristors, (1 - transient) * nbrOfTrainingSongs * nbrOfTimeSteps);

    for iMem = 1:nbrOfMemristors
        counter = 0;
        for iSong = 1 : nbrOfTrainingSongs 
            for iTimeStep = (transientValue + 1):nbrOfTimeSteps
                counter = counter + 1;
                thetaGradient_i = ( (theta * m(:,iTimeStep,iSong) - y) ) * m(iMem,iTimeStep,iSong);
                theta(iMem) = theta(iMem) - eta * thetaGradient_i;
                gradient_plot(iMem,counter) = thetaGradient_i;
                theta_plot(iMem, counter) = theta(iMem);
            end
        end
    end
    if plot_bool
        %x = 1 : round((1 - transient) * nbrOfTrainingSongs * nbrOfTimeSteps);
        for iMem = 1:nbrOfMemristors
            plot(theta_plot(iMem,:));
            hold on
        end
    end
    disp("Calculation of readOutVector finished for genre type " + genre)
    fprintf("\n")
    %plot(gradient(1,:))
end