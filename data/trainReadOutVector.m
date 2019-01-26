function theta = trainReadOutVector(genre, m, y, transient, eta, plot_theta_bool, plot_error_bool, useAverageTheta_bool)
    nbrOfMemristors = size(m,1);
    nbrOfTimeSteps = size(m,2);
    nbrOfTrainingSongs = size(m,3);
    theta = ones(1,nbrOfMemristors);
    transientValue = round(nbrOfTimeSteps * transient);
    loopLenght = 2;
    nbrSongsInLoop = nbrOfTrainingSongs * loopLenght;
    nbrTimeStepsInLoop = nbrOfTimeSteps * loopLenght;
    theta_plot= zeros(nbrOfMemristors, round((1 - transient) * nbrSongsInLoop * nbrTimeStepsInLoop));
    error_plot = zeros(1,nbrSongsInLoop);
    
    counter = 0;
    sumCounter = 1;
    for iSong = 1 : nbrSongsInLoop
        for iTimeStep = (transientValue + 1):nbrTimeStepsInLoop
            counter = counter + 1;
            for iMem = 1:nbrOfMemristors
                iT = round(rand()*(nbrOfTimeSteps - 1)) + 1;
                iS = round(rand()*(nbrOfTrainingSongs-1)) + 1;
                thetaGradient_i = ( (theta * m(:,iT,iS) - y) ) * m(iMem,iT,iS);
                theta(iMem) = theta(iMem) - eta * thetaGradient_i;
                theta_plot(iMem, counter) = theta(iMem);
            end
        end
        if plot_error_bool
            for iSum_Song = 1:nbrOfTrainingSongs
                for iSum_TimeStep = (transientValue + 1):nbrOfTimeSteps
                    error_plot(sumCounter) = error_plot(sumCounter) + sqrt((theta * m(:,iSum_TimeStep,iSum_Song) - y)^2 ) / y;
                end
            end
            sumCounter = sumCounter + 1;
        end
    end
    if useAverageTheta_bool
        for iMem = 1:nbrOfMemristors
            theta(iMem) = mean(theta_plot(iMem,counter - 2e4:counter));
        end
    end
    
    
    error_plot = error_plot / (nbrOfTrainingSongs * (1 - transient) * nbrOfTimeSteps);
    if plot_theta_bool
        for iMem = 1:nbrOfMemristors
            if genre == "blues"
                figure(1)
                plot(theta_plot(iMem,:));
                hold on
            else
                figure(2)
                plot(theta_plot(iMem,:));
                hold on
            end
        end
    end
    
    if plot_error_bool
        if genre == "blues"
            figure(1)
            plot(error_plot / 10)
        else
            figure(2)
            plot(error_plot / 10)
        end
    end
        
    disp("Calculation of readOutVector finished for genre type " + genre)
    fprintf("\n")
end